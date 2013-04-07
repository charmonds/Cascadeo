package com.cs192.foodwatch;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class ViewDatabaseInner extends Activity {
	
	TableLayout t1, t2, t3;
	ScrollView s1;
	LinearLayout l1;
	RelativeLayout R1;
	String idPassed;
	Spinner nutFactChoices;
	
	
	double[] universal;
	double[] cholesterol;
	double[] carbohydrates;
	double[] totalFats;
	double[] sugar;
	double[] calories;
	String[] dateOfNutritionalFacts;
 	
	
	
	int nutritionFactLength;
	TextView fname,mname,lname,email,pass, initWeight,initHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_database_inner);
		initializeVariables();
		Bundle bundle = getIntent().getExtras();
		idPassed = bundle.getString("SPECIFICID");
		
		
		
		TabHost th = (TabHost) findViewById(R.id.tabhostForViewingDatabaseInner);
		th.setup();
		TabSpec specs = th.newTabSpec("tag1");
		specs.setContent(R.id.dbViewPersonalInformation);
		specs.setIndicator("Personal Information");
		th.addTab(specs);
		
		specs = th.newTabSpec("tag2");
		specs.setContent(R.id.dbViewWeightInformation);
		specs.setIndicator("Weight Information");
		th.addTab(specs);
		
		specs = th.newTabSpec("tag3");
		specs.setContent(R.id.dbViewNutFactInformation);
		specs.setIndicator("Nutritional Fact Information");
		th.addTab(specs);
		
		DatabaseManager database = new DatabaseManager(this);
		String first,middle,last,emailadd,password,initialweight,initialheight;
		database.open();
		
		/*For getting the personal information of the patient*/
		Cursor c = database.getDataOfSpecificPatientAndReturnAsQuery(idPassed);
		if (c.moveToFirst()) {
				first = c.getString(1);
				middle = c.getString(2);
				last = c.getString(3);
				emailadd = c.getString(4);
				password = c.getString(5);
				initialweight = c.getString(6);
				initialheight = c.getString(7);
				fname.setText(first);
				mname.setText(middle);
				lname.setText(last);
				email.setText(emailadd);
				pass.setText(password);
				initWeight.setText(initialweight);
				initHeight.setText(initialheight);
				c.moveToNext();
		}
		c.close();
		/*For getting the weight information of the patient*/
		Cursor d = database.getDataAndReturnedAsQuery(idPassed);
		String[] patientWeightRecords = new String[d.getCount()];
		String[] patientDateRecords = new String[d.getCount()];
		if(d.moveToFirst()) {
			for(int i=0;i<d.getCount();i++) {
				patientWeightRecords[i] = d.getString(0);
				patientDateRecords[i] = d.getString(1);
				d.moveToNext();
			}
		}
		int length = d.getCount();
		d.close();
		t2.removeAllViews();
		 for (int current = 0; current < length; current++)
	        {
			// Inflate your row "template" and fill out the fields.
			    TableRow row = (TableRow)LayoutInflater.from(ViewDatabaseInner.this).inflate(R.layout.attribute_row, null);
			    ((TextView)row.findViewById(R.id.attrib_name)).setText(patientWeightRecords[current]+" kg");
			    ((TextView)row.findViewById(R.id.attrib_value)).setText(patientDateRecords[current]);
			    t2.addView(row);
	            
	        }
		
		
		 /*for getting the nutrition fact of the patient*/
		 Cursor e = database.getDataOfNutFactandReturnAsQuerry(idPassed);
			String[] cholData = new String[e.getCount()];
			String[] carbData = new String[e.getCount()];
			String[] fatData = new String[e.getCount()];
			String[] sugData = new String[e.getCount()];
			String[] calData = new String[e.getCount()];
			String[] amtData = new String[e.getCount()];
			String[] dateData = new String[e.getCount()];
			double[] chol = new double[e.getCount()];
			double[] carb = new double[e.getCount()];
			double[] fat = new double[e.getCount()];
			double[] sug =new double[e.getCount()];
			double[] cal =new double[e.getCount()];
			nutritionFactLength = e.getCount();
			
			if(e.moveToFirst()) {
				for(int i=0;i<e.getCount();i++) {
					cholData[i] = e.getString(1);
					carbData[i] = e.getString(2);
					fatData[i] = e.getString(3);
					sugData[i] = e.getString(4);
					calData[i] = e.getString(5);
					amtData[i] = e.getString(6);
					dateData[i] = e.getString(7);
					chol[i] = Double.parseDouble(cholData[i]) * Double.parseDouble(amtData[i]);
					carb[i] = Double.parseDouble(carbData[i])  * Double.parseDouble(amtData[i]);
					fat[i] = Double.parseDouble(fatData[i])  * Double.parseDouble(amtData[i]); 
					sug[i] = Double.parseDouble(sugData[i])  * Double.parseDouble(amtData[i]);
					cal[i] = Double.parseDouble(calData[i])  * Double.parseDouble(amtData[i]);
					e.moveToNext();
				}
			}	
			
			
			cholesterol = chol;
			carbohydrates = carb;
			totalFats = fat;
			sugar = sug;
			calories = cal;
			dateOfNutritionalFacts = dateData;
			
			e.close();
			String[] nutFacts = {"Cholesterol","Carbohydrates", "Fats", "Sugar", "Calories"};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewDatabaseInner.this, android.R.layout.simple_spinner_item, nutFacts);
			nutFactChoices.setAdapter(adapter);
			
			nutFactChoices.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parentView, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					String selected = parentView.getItemAtPosition(position).toString();
					if (selected == "Cholesterol") {
						universal = cholesterol;
					} else if (selected == "Carbohydrates") {
						universal = carbohydrates;
					} else if (selected == "Fats") {
						universal = totalFats;
					}  else if (selected == "Sugar") {
						universal = sugar;
					}  else {
						universal = calories;
					}
					t3.removeAllViews();
					 for (int current = 0; current < nutritionFactLength; current++)
				        {
						// Inflate your row "template" and fill out the fields.
						    TableRow row = (TableRow)LayoutInflater.from(ViewDatabaseInner.this).inflate(R.layout.attribute_row, null);
						    ((TextView)row.findViewById(R.id.attrib_name)).setText(""+universal[current]);
						    ((TextView)row.findViewById(R.id.attrib_value)).setText(dateOfNutritionalFacts[current]);
						    t3.addView(row);
				            
				        }
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
				
			});				
				
				
		database.close();
	}

	private void initializeVariables() {
		// TODO Auto-generated method stub
		s1 = (ScrollView) findViewById(R.id.dbViewPersonalInformation);
		t1 = (TableLayout) findViewById(R.id.tableLayoutForPersonalInformation);
		t2 = (TableLayout) findViewById(R.id.tableLayoutForWeightInformation);
		t3 = (TableLayout) findViewById(R.id.tableLayoutForNutritionInformation);
		t2.removeAllViews();
		fname = (TextView) findViewById(R.id.tvViewDatabaseInnerFirstName);
		mname = (TextView) findViewById(R.id.tvViewDatabaseInnerMiddleName);
		lname = (TextView) findViewById(R.id.tvViewDatabaseInnerLastName);
		email = (TextView) findViewById(R.id.tvViewDatabaseInnerEmail);
		pass = (TextView) findViewById(R.id.tvViewDatabaseInnerPassword);
		initWeight = (TextView) findViewById(R.id.tvViewDatabaseInnerInitialWeight);
		initHeight = (TextView) findViewById(R.id.tvViewDatabaseInnerInitialHeight);
		nutFactChoices = (Spinner) findViewById(R.id.spinnerDatabaseInnerNutFacts);
	}
	

}
