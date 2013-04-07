package com.cs192.foodwatch;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Add_ailment extends Activity {

	EditText name, date;
	String ailment, acquired, id;
	public void add(View view)
	{
		//add a "successfully added ailment" here.
		Intent intent =new Intent(this,Profile_health.class);
		boolean didItWork = true;
		name = (EditText) findViewById(R.id.editText1);
		date = (EditText) findViewById(R.id.EditText01);
		ailment = name.getText().toString();
		acquired = date.getText().toString();
		
		
		try {
			
			
			DatabaseManager info = new DatabaseManager(Add_ailment.this);
			info.open();
			Bundle bundle = getIntent().getExtras();
			id = bundle.getString("ID");
			info.insertAilment(id, ailment, acquired);
			info.close();
		} catch (Exception e) {
			Dialog d= new Dialog(this);
			String error = e.toString();
			TextView tv = new TextView(this);
			d.setTitle("ERROR!");
			tv.setText(error);
			d.setContentView(tv);
			d.show();
			startActivity(intent);
			
			didItWork = false;
		} finally {
			if (didItWork) {
				Dialog d= new Dialog(this);
				TextView tv = new TextView(this);
				d.setTitle("Notice");
				tv.setText("Your Profile has been updated!");
				d.setContentView(tv);
				d.show();
			}
			
		}
	
		intent.putExtra("ID", id);
		startActivity(intent);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_ailment);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_ailment, menu);
		return true;
	}

}
