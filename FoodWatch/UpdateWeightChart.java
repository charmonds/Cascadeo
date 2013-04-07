package com.cs192.foodwatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateWeightChart extends Activity {
	
	Button clear,update,view,target;
	EditText weight,date;
	String id;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_weight_chart);
		initializeVariables();
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("ID");
		clear.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				weight.setText("");
				date.setText("");
				
			}
		});
		
		
		
	}

	public void initializeVariables() {
		clear = (Button) findViewById(R.id.updateChartCLEAR);
		update = (Button) findViewById(R.id.updateButton2);
		weight = (EditText) findViewById(R.id.etUpdateWeightChartWeight);
		date = (EditText) findViewById(R.id.etUpdateWeightChartDate);
	}
	
	public void updateWeightDatabase(View view) {
		//String tar;
		try {
			String w = weight.getText().toString();
			String d= date.getText().toString();
			

			DatabaseManager database = new DatabaseManager(UpdateWeightChart.this);
			database.open();
			long l = Long.parseLong(id);
			database.addToWeightDatabaseOfPatient(w, d, id);
			database.close();
		} catch (Exception e) {
			Log.d("ErrorTag", e.toString());
		} finally {
			//tar = target.getText().toString();
			Intent intent = new Intent(getApplicationContext(), ViewWeightChart.class);
			intent.putExtra("ID", id);
			startActivity(intent);
		}
	}
	
	public void setTarget(View view) {
			Intent intent = new Intent(getApplicationContext(),AddTarget.class);
			intent.putExtra("ID", id);
			startActivity(intent);
	}
	
}
