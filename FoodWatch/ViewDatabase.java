package com.cs192.foodwatch;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class ViewDatabase extends ListActivity {

	String[] patientNames;
	String[] patientIDs;
	String id;
	
	String passedIdToGetDataAboutPatient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("ID");
		DatabaseManager database = new DatabaseManager(ViewDatabase.this);
		database.open();
		Cursor c = database.getDataOfPatientAndReturnAsQuery();
		String[] fullNameOfPatient = new String[c.getCount()];
		String[] tempIDs = new String[c.getCount()];
		if(c.moveToFirst()) {
			for(int i =0;i<c.getCount();i++) {
				fullNameOfPatient[i] = c.getString(1) + " " + c.getString(2) + " " + c.getString(3);
				tempIDs[i] = database.getID(c.getString(4));
				c.moveToNext();
			}
			
		}
		c.close();
		database.close();
		patientNames = fullNameOfPatient;
		patientIDs = tempIDs;
		setListAdapter(new ArrayAdapter<String>(ViewDatabase.this, android.R.layout.simple_list_item_1, patientNames));
		
	}
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Log.d(null,"hello!");

		try
		{
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(getApplicationContext(), ViewDatabaseInner.class);
		intent.putExtra("SPECIFICID", patientIDs[position]);
		startActivity(intent);
		}catch (Exception e){Log.d(null,"error!");}
	}


}
