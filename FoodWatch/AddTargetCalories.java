package com.cs192.foodwatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTargetCalories extends Activity {

	
	String id;
	Button clear;
	EditText etCalories, etDate;
	String tarCalories, tarDate;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_calorie_target);
		initializeVariables();
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("ID");
		
		clear.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				etCalories.setText("");
				etDate.setText("");
				etCalories.setHint("amount in g");
				etDate.setHint("mm/dd/yy");
			}
		});
		
		
	}
	
	private void initializeVariables() {
		// TODO Auto-generated method stub
		clear =(Button) findViewById(R.id.addTargetCalorieCLEAR);
		etCalories = (EditText) findViewById(R.id.etaddTargetCalorie);
		etDate = (EditText) findViewById(R.id.etaddTargetCalorieDate);
	}

	public void updateTargetCaloriesMethod(View view) {
		
		
		DatabaseManager database = new DatabaseManager(this);
		database.open();
		tarCalories = etCalories.getText().toString();
		tarDate = etDate.getText().toString();
		String one = "1";
		database.updateTargetCalories(tarCalories, tarDate, one,id);
		database.close();
		Intent intent = new Intent(getApplicationContext(), ViewCalorieChart.class);
		intent.putExtra("ID", id);
		startActivity(intent);
	}

	
}
