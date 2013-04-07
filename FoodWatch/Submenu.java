package com.cs192.foodwatch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class Submenu extends Activity 
{

	public void bmiTable(View view)
	{
		Intent intent =new Intent(this,BmiTable.class);
	    startActivity(intent);
	}
	
	public void calorieChart(View view)
	{
		Intent intent =new Intent(this,BmiTable.class);
	    startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submenu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.activity_submenu, menu);
		return true;
	}

}
