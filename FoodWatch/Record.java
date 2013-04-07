package com.cs192.foodwatch;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Record extends Activity {
	
	String id;
	
	Spinner spinner1, spinner2;
	Button clear;
	EditText cholesterol, totalCarbs, totalFats, sugar,calories,amountofFood,etDate,etTime,foodname;
	
	int day,month,year;
	int minute,hour;
	int checker=0;
	String displayMinute;
	
	String[] timeOfMealChoices = {"Breakfast","Lunch","Dinner","Others(snack)"};
	String[] mealChoices = {"Home-made", "Lunch-out"};
	
	/*declaration of variables needed to take input*/
	String date;
	String time;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);
		initializeVariables();
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("ID");
		GregorianCalendar date = new GregorianCalendar();
		 
	    day = date.get(Calendar.DAY_OF_MONTH);
	    month = date.get(Calendar.MONTH);
	    year = date.get(Calendar.YEAR);

	    minute = date.get(Calendar.MINUTE);
	    switch(minute) {
	    case 1:
	    	displayMinute ="01";
	    	checker++;
	    	break;
	    case 2:
	    	displayMinute ="02";
	    	checker++;
	    	break;
	    case 3:
	    	displayMinute ="03";
	    	checker++;
	    	break;
	    case 4:
	    	displayMinute ="04";
	    	checker++;
	    	break;
	    case 5:
	    	displayMinute ="05";
	    	checker++;
	    	break;
	    case 6:
	    	displayMinute ="06";
	    	checker++;
	    	break;
	    case 7:
	    	displayMinute ="07";
	    	checker++;
	    	break;
	    case 8:
	    	displayMinute ="08";
	    	checker++;
	    	break;
	    case 9:
	    	displayMinute ="09";
	    	checker++;
	    	break;
	    case 0:
	    	displayMinute ="00";
	    	checker++;
	    	break;
	    default:
	    	break;
	    }
	    hour = date.get(Calendar.HOUR);
	    
	    
	    etDate.setText((month+1)+"/"+day+"/"+year);
	    
	    if(checker>0) {
	    	etTime.setText(hour+":"+displayMinute);
	    } else {
	    	etTime.setText(hour+":"+minute);
	    }

		
		
		clear.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				foodname.setText("");
				cholesterol.setText("");
				totalCarbs.setText("");
				totalFats.setText("");
				sugar.setText("");
				calories.setText("");
				amountofFood.setText("");
				
			}
		});
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Notice");
		alert.setMessage("Please provide as much information as you can. Thank You!");
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			  //String value = input.getText();
			  // Do something with value!
			  }
			});
		alert.show();
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(Record.this, android.R.layout.simple_spinner_item, timeOfMealChoices);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Record.this, android.R.layout.simple_spinner_item, mealChoices);
	
		
		spinner1.setAdapter(adapter);
		
		spinner2.setAdapter(adapter2);
		
	}
	
	private void initializeVariables() {
		// TODO Auto-generated method stub
		foodname = (EditText) findViewById(R.id.etRecordFoodName);
		etDate = (EditText) findViewById(R.id.etRecordDate);
		etTime = (EditText) findViewById(R.id.etRecordTime);
		cholesterol = (EditText) findViewById(R.id.etCholesterol);
		totalCarbs = (EditText) findViewById(R.id.etCarbohydrates);
		totalFats = (EditText) findViewById(R.id.etTotalFats);
		sugar = (EditText) findViewById(R.id.etSugar);
		calories = (EditText) findViewById(R.id.etCalories);
		amountofFood = (EditText) findViewById(R.id.etAmountOfFood);
		clear = (Button) findViewById(R.id.buttonRecordClear);
		spinner1 = (Spinner) findViewById(R.id.sTMeal);
		spinner2 = (Spinner) findViewById(R.id.sMeal);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_record, menu);
		return true;
	}
	
	public void recordSuccess(View view)
	{
		
		try {
			date = etDate.getText().toString();
			time = etTime.getText().toString();
			String foodName = foodname.getText().toString();
			String cho = cholesterol.getText().toString();
			String ca = totalCarbs.getText().toString();
			String fa = totalFats.getText().toString();
			String su = sugar.getText().toString();
			String cal = calories.getText().toString();
			String quantity = amountofFood.getText().toString();
			String timeOfMeal = spinner1.getSelectedItem().toString();
			String meal = spinner2.getSelectedItem().toString();

			DatabaseManager database = new DatabaseManager(this);
			database.open();
			database.addToNutritionalFactsDatabase(foodName,cho,ca,fa,su,cal,quantity,date,time,timeOfMeal, meal,id);
			database.close();
		} catch (Exception e) {
			Log.d("ErrorTag", e.toString());
		} finally {
			//tar = target.getText().toString();
			Intent intent =new Intent(getApplicationContext(),ViewRecords.class);
			intent.putExtra("ID", id);
			startActivity(intent);
		}
	}
}
