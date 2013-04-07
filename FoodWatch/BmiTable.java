package com.cs192.foodwatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class BmiTable extends Activity {
	float weight, height;
	EditText getInput1FromProfile, getInput2FromProfile;
	TextView displayBMIFromProfile;
	String id;
	

	public void bmiCalculator(View view)
	{
		Intent intent =new Intent(this,BmiCalculator.class);
		intent.putExtra("ID", id);
	    startActivity(intent);
	}
	
	public void mainMenu(View view)
	{
		Intent intent =new Intent(this,Mainmenu.class);
		intent.putExtra("ID", id);
	    startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bmi_table);
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("ID");
		getInput1FromProfile = (EditText) findViewById(R.id.editHeight);
		getInput2FromProfile = (EditText) findViewById(R.id.editWeight);
		displayBMIFromProfile = (TextView) findViewById(R.id.textDispBMI);
	}
	
	
	
	
}
