package com.cs192.foodwatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTarget extends Activity {
	
	String id;
	Button clear;
	EditText etWeight, etDate;
	String tarWeight, tarDate;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_target);
		initializeVariables();
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("ID");
		
		clear.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				etWeight.setText("");
				etDate.setText("");
				etWeight.setHint("amount in kg");
				etDate.setHint("mm/dd/yy");
			}
		});
		
		
	}
	
	private void initializeVariables() {
		// TODO Auto-generated method stub
		clear =(Button) findViewById(R.id.addTargetCLEAR);
		etWeight = (EditText) findViewById(R.id.etaddTargetWeight);
		etDate = (EditText) findViewById(R.id.etaddTargetDate);
	}

	public void updateTarget(View view) {
		
		
		DatabaseManager database = new DatabaseManager(this);
		database.open();
		tarWeight = etWeight.getText().toString();
		tarDate = etDate.getText().toString();
		String one = "1";
		database.updateTarget(tarWeight, tarDate, one,id);
		database.close();
		Intent intent = new Intent(getApplicationContext(), ViewWeightChart.class);
		intent.putExtra("ID", id);
		startActivity(intent);
	}

	
	
}
