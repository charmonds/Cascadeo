package com.cs192.foodwatch;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateChart extends Activity {
	Button clear;
	EditText weight,etDate,time,targetWeight,targetDays;
	
	int day,month,year;
	int minute,hour;
	public int target;
	
	public double theTargetWeight;
	public double theTargetDays;
	
    String displayMinute;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activitiy_create_chart);
		weight = (EditText) findViewById(R.id.etCreateWeightChartWeight);
		etDate = (EditText) findViewById(R.id.etCreateWeightChartDate);
		time = (EditText) findViewById(R.id.etCreateWeightChartTime);
		targetWeight = (EditText) findViewById(R.id.etCreateTargetWeight);
		targetDays = (EditText) findViewById(R.id.etCreateWeightChartTargetDay);
		GregorianCalendar date = new GregorianCalendar();
		 
	    day = date.get(Calendar.DAY_OF_MONTH);
	    month = date.get(Calendar.MONTH);
	    year = date.get(Calendar.YEAR);

	    minute = date.get(Calendar.MINUTE);
	    hour = date.get(Calendar.HOUR);
	    minute = date.get(Calendar.MINUTE);

	    int checker=0;
	    switch(minute) {
	    case 1:
	    	displayMinute ="01";
	    	checker++;
	    	break;
	    case 2:
	    	displayMinute ="";
	    	checker++;
	    	break;
	    case 3:
	    	displayMinute ="";
	    	checker++;
	    	break;
	    case 4:
	    	displayMinute ="";
	    	checker++;
	    	break;
	    case 5:
	    	displayMinute ="";
	    	checker++;
	    	break;
	    case 6:
	    	displayMinute ="";
	    	checker++;
	    	break;
	    case 7:
	    	displayMinute ="";
	    	checker++;
	    	break;
	    case 8:
	    	displayMinute ="";
	    	checker++;
	    	break;
	    case 9:
	    	displayMinute ="";
	    	checker++;
	    	break;
	    case 0:
	    	displayMinute ="";
	    	checker++;
	    	break;
	    default:
	    	break;
	    }
	    
	    
	    etDate.setText((month+1)+"/"+day+"/"+year);
	    if(checker>0) {
	    	time.setText(hour+":"+displayMinute);
	    } else {
	    	time.setText(hour+":"+minute);
	    }
	    
		
		clear = (Button) findViewById(R.id.createWeightChartClearButton);
		clear.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				weight.setText("");
				etDate.setText("");
				time.setText("");
				targetDays.setText("");
				targetWeight.setText("");
			}
		});
		
	}
	
	public void createWeightChart(View view) 
	{
		
		String tw= targetWeight.getText().toString();
		String td = targetDays.getText().toString();
		String iw = weight.getText().toString();
		theTargetWeight = Double.parseDouble(tw);
		theTargetDays = Double.parseDouble(td);
		//CreateChartDataPasser datapass = new CreateChartDataPasser();
		//datapass.createChartDataPasser(theTargetWeight, theTargetDays, target);
		Intent intent = new Intent(getApplicationContext(),ViewWeightChart.class);
		intent.putExtra("inputWeight", iw);
		intent.putExtra("targetWeight", tw);
		intent.putExtra("targetDays", td);
		startActivity(intent);
	}
	
	public void createCalorieChart(View view) 
	{
		
	}

}
