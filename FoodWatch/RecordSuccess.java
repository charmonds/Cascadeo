package com.cs192.foodwatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RecordSuccess extends Activity {
	String d;
	String t;
	String cho;
	String ca;
	String fa;
	String su;
	String id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_success);
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("ID");
	}
	
	public void records(View view)
	{
		Intent intent =new Intent(this,Record.class);
		intent.putExtra("ID", id);
	    startActivity(intent);
	}
	
	public void mainMenu(View view)
	{
		Intent intent =new Intent(this,Mainmenu.class);
		intent.putExtra("ID", id);
	    startActivity(intent);
	}
	
	public void viewRecords(View view) {
		
		Intent intent = new Intent(getApplicationContext(),ViewRecords.class);
		intent.putExtra("ID", id);
		startActivity(intent);
	}



	
}
