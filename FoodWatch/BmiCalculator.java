package com.cs192.foodwatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BmiCalculator extends Activity {
	float weight, height, BMI;
	float bodyMassIndex;
	
	String id;
	
	Button calculate;
	EditText input1, input2;
	TextView displayBMI;

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
		setContentView(R.layout.activity_bmi_calculator);
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("ID");
		BMI = 0;
		height = 0;
		calculate = (Button) findViewById(R.id.calculateBMI);
		input1 = (EditText) findViewById(R.id.editHeight);
		input2 = (EditText) findViewById(R.id.editWeight);
		displayBMI = (TextView) findViewById(R.id.textDispBMI);
		
		calculate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				input1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
				String h= input1.getText().toString();
				String w = input2.getText().toString();
				height = Float.parseFloat(h);
				weight = Float.parseFloat(w);
				height = height / 100;
				BMI = weight / (height*height);
				displayBMI.setText("Your BMI is " + BMI);
			}
		});
	}
}
