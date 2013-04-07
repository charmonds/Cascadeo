package com.cs192.foodwatch;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class RecordSubmenu extends Activity {
	
	String id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_records_submenu);
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("ID");
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_records_submenu, menu);
		return true;
	}
	
	
	
	public void records(View view)
	{
		Intent intent =new Intent(getApplicationContext(),Record.class);
		intent.putExtra("ID", id);
	    startActivity(intent);
	}
	
	public void viewRecords(View view)
	{
		Intent intent =new Intent(getApplicationContext(),ViewRecords.class);
		intent.putExtra("ID", id);
	    startActivity(intent);
	}

	
	public void viewCharts(View view) 
	{
		Intent intent = new Intent(getApplicationContext(),ViewWeightChart.class);
		intent.putExtra("ID", id);
		startActivity(intent);
	}
	
	public void viewCalorieChart(View view) {
		Intent intent = new Intent(getApplicationContext(), ViewCalorieChart.class);
		intent.putExtra("ID", id);
		startActivity(intent);
	}
	
}
