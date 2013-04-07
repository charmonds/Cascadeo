package com.cs192.foodwatch;


import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class ViewRecords extends Activity {
	
	private GraphicalView targetView,targetView2,targetView3;
	
	
	String date;
	String time;
	String id;
	String monthID;
	String dailyTitle = "Nutrition Line Graph: Daily";
	String weeklyTitle = "Nutrition Line Graph: Weekly";
	String monthlyTitle = "Nutrition Line Graph: Monthly";
	String xDateLabel = "Date";
	String yLabel = "Nutritional Facts";

	Spinner spinner,monthSpinner;
	String[] nutFacts = {"Cholesterol","Sodium","Total Fats","Saturated","Unsaturated","Trans","Total Carbohydrates","Sugar","Dietary Fiber"};
	String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	LinearLayout nutChartLinearLayout,nutChartLinearLayout2,nutChartLinearLayout3;
	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_record);
		initializeVariables();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewRecords.this, android.R.layout.simple_spinner_item, monthNames);
	
		
		monthSpinner.setAdapter(adapter);
		Bundle extras = getIntent().getExtras();
		id = extras.getString("ID");
		
		TabHost th = (TabHost) findViewById(R.id.tabhost);
		th.setup();
		TabSpec specs = th.newTabSpec("tag1");
		specs.setContent(R.id.day);
		specs.setIndicator("Day");
		th.addTab(specs);
		
		specs = th.newTabSpec("tag2");
		specs.setContent(R.id.week);
		specs.setIndicator("Week");
		th.addTab(specs);
		
		specs = th.newTabSpec("tag3");
		specs.setContent(R.id.month);
		specs.setIndicator("Month");
		th.addTab(specs);
		
		
		nutChartLinearLayout.removeAllViews();
		nutChartLinearLayout2.removeAllViews();
		nutChartLinearLayout3.removeAllViews();
		
		
		DatabaseManager database = new DatabaseManager(this);
		database.open();
		int length;
		Cursor c = database.getDataOfNutFactandReturnAsQuerry(id);
		String[] cholData = new String[c.getCount()];
		String[] carbData = new String[c.getCount()];
		String[] fatData = new String[c.getCount()];
		String[] sugData = new String[c.getCount()];
		String[] calData = new String[c.getCount()];
		String[] amtData = new String[c.getCount()];
		String[] dateData = new String[c.getCount()];
		length = c.getCount();
		
		
		double[] cholDataWeekly;
		double[] carbDataWeekly;
		double[] fatDataWeekly;
		double[] sugarDataWeekly;
		double[] calDataWeekly;
		String[] weeklyDate;
		int weeklyLength;
		int quotientForWeek;
		
		
		
		
		
		double[] chol = new double[c.getCount()];
		double[] carb = new double[c.getCount()];
		double[] fat = new double[c.getCount()];
		double[] sug = new double[c.getCount()];
		double[] cal = new double[c.getCount()];
		double[] amt = new double[c.getCount()];
		if(c.moveToFirst()) {
			for(int i=0;i<c.getCount();i++) {
				cholData[i] = c.getString(1);
				carbData[i] = c.getString(2);
				fatData[i] = c.getString(3);
				sugData[i] = c.getString(4);
				calData[i] = c.getString(5);
				amtData[i] = c.getString(6);
				dateData[i] = c.getString(7);
				chol[i] = Double.parseDouble(cholData[i]);
				carb[i] = Double.parseDouble(carbData[i]);
				fat[i] = Double.parseDouble(fatData[i]);
				sug[i] = Double.parseDouble(sugData[i]);
				cal[i] = Double.parseDouble(calData[i]);
				amt[i] = Double.parseDouble(amtData[i]);
				c.moveToNext();
			}
		}
		
		for(int i=0;i<c.getCount();i++) {
			chol[i] = chol[i] * amt[i];
			carb[i] = carb[i] * amt[i];
			fat[i] = fat[i]  * amt[i];
			sug[i] = sug[i]  * amt[i];
			cal[i] = cal[i]  * amt[i];
		}
		
		c.close();
		database.close();
		
		/*This part is for computing the weekly nutritional fact record*/
		quotientForWeek = length/7;
		
		int moduloQuotientForWeek = length%7;
		if (moduloQuotientForWeek == 0) {
			double[] cholWeek = new double[quotientForWeek];
			double[] carbWeek = new double[quotientForWeek];
			double[] fatWeek = new double[quotientForWeek];
			double[] sugarWeek = new double[quotientForWeek];
			double[] caloriesWeek = new double[quotientForWeek];
			String[] dateWeek = new String[quotientForWeek];
			
			for(int i =0;i<quotientForWeek;i++) {
				cholWeek[i]= chol[(((i+1)*7)-1)]+chol[(i*7)-2]+chol[(i*7)-3]+chol[(i*7)-4]+chol[(i*7)-5]+chol[(i*7)-6]+chol[(i*7)-7];
				carbWeek[i] = carb[(((i+1)*7)-1)]+carb[(i*7)-2]+carb[(i*7)-3]+carb[(i*7)-4]+carb[(i*7)-5]+carb[(i*7)-6]+carb[(i*7)-7];
				fatWeek[i] = fat[(((i+1)*7)-1)]+fat[(i*7)-2]+fat[(i*7)-3]+fat[(i*7)-4]+fat[(i*7)-5]+fat[(i*7)-6]+fat[(i*7)-7];
				sugarWeek[i] = sug[(((i+1)*7)-1)]+sug[(i*7)-2]+sug[(i*7)-3]+sug[(i*7)-4]+sug[(i*7)-5]+sug[(i*7)-6]+sug[(i*7)-7];
				caloriesWeek[i] = cal[(((i+1)*7)-1)]+cal[(i*7)-2]+cal[(i*7)-3]+cal[(i*7)-4]+cal[(i*7)-5]+cal[(i*7)-6]+cal[(i*7)-7];
				dateWeek[i] = dateData[(((i+1)*7)-1)]+"-"+dateData[(i*7)-7];
			}
			
			cholDataWeekly = cholWeek;
			carbDataWeekly = carbWeek;
			fatDataWeekly = fatWeek;
			sugarDataWeekly = sugarWeek;
			calDataWeekly = caloriesWeek;
			weeklyLength = quotientForWeek;
			weeklyDate = dateWeek;
			
		} else {
			double[] cholWeek = new double[quotientForWeek+1];
			double[] carbWeek = new double[quotientForWeek+1];
			double[] fatWeek = new double[quotientForWeek+1];
			double[] sugarWeek = new double[quotientForWeek+1];
			double[] caloriesWeek = new double[quotientForWeek+1];
			String[] dateWeek = new String[quotientForWeek+1];
			
			cholWeek[quotientForWeek]=0;
			carbWeek[quotientForWeek]=0;
			fatWeek[quotientForWeek]=0;
			sugarWeek[quotientForWeek]=0;
			caloriesWeek[quotientForWeek]=0;
			
			for(int i =0;i<quotientForWeek;i++) {
				cholWeek[i]= chol[(((i+1)*7)-1)]+chol[(i*7)-2]+chol[(i*7)-3]+chol[(i*7)-4]+chol[(i*7)-5]+chol[(i*7)-6]+chol[(i*7)-7];
				carbWeek[i] = carb[(((i+1)*7)-1)]+carb[(i*7)-2]+carb[(i*7)-3]+carb[(i*7)-4]+carb[(i*7)-5]+carb[(i*7)-6]+carb[(i*7)-7];
				fatWeek[i] = fat[(((i+1)*7)-1)]+fat[(i*7)-2]+fat[(i*7)-3]+fat[(i*7)-4]+fat[(i*7)-5]+fat[(i*7)-6]+fat[(i*7)-7];
				sugarWeek[i] = sug[(((i+1)*7)-1)]+sug[(i*7)-2]+sug[(i*7)-3]+sug[(i*7)-4]+sug[(i*7)-5]+sug[(i*7)-6]+sug[(i*7)-7];
				caloriesWeek[i] = cal[(((i+1)*7)-1)]+cal[(i*7)-2]+cal[(i*7)-3]+cal[(i*7)-4]+cal[(i*7)-5]+cal[(i*7)-6]+cal[(i*7)-7];
				dateWeek[i] = dateData[(((i+1)*7)-1)]+"-"+dateData[(i*7)-7];
			}
			
			for(int i=0;i<moduloQuotientForWeek;i++) {
				cholWeek[quotientForWeek] = chol[quotientForWeek] + chol[(quotientForWeek*7)+i];
				carbWeek[quotientForWeek] = carb[quotientForWeek] + carb[(quotientForWeek*7)+i];
				fatWeek[quotientForWeek] = fat[quotientForWeek] + fat[(quotientForWeek*7)+i];
				sugarWeek[quotientForWeek] = sug[quotientForWeek] + sug[(quotientForWeek*7)+i];
				caloriesWeek[quotientForWeek] = cal[quotientForWeek] + cal[(quotientForWeek*7)+i];
			}
			dateWeek[quotientForWeek] = dateData[(quotientForWeek*7)] + dateData[(quotientForWeek*7)+(moduloQuotientForWeek-1)];
			
			
			
			cholDataWeekly = cholWeek;
			carbDataWeekly = carbWeek;
			fatDataWeekly = fatWeek;
			sugarDataWeekly = sugarWeek;
			calDataWeekly = caloriesWeek;
			weeklyLength = quotientForWeek+1;
			weeklyDate = dateWeek;
		}
		
		
		
		/*This part is for computing the monthly nutritional fact record*/
		monthSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			
			@Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long idOfItemSelected) {
				String selected = parentView.getItemAtPosition(position).toString();
				if (selected == "January") {
					nutChartLinearLayout3.removeAllViews();
					monthID = "1/";
				} else if (selected == "February") {
					nutChartLinearLayout3.removeAllViews();
					monthID = "2/";
				} else if (selected == "March") {
					nutChartLinearLayout3.removeAllViews();
					monthID = "3/";
				} else if (selected == "April") {
					nutChartLinearLayout3.removeAllViews();
					monthID = "4/";
				} else if (selected == "May") {
					nutChartLinearLayout3.removeAllViews();
					monthID = "5/";
				} else if (selected == "June") {
					nutChartLinearLayout3.removeAllViews();
					monthID = "6/";
				} else if (selected == "July") {
					nutChartLinearLayout3.removeAllViews();
					monthID = "7/";
				} else if (selected == "August") {
					nutChartLinearLayout3.removeAllViews();
					monthID = "8/";
				} else if (selected == "September") {
					nutChartLinearLayout3.removeAllViews();
					monthID = "9/";
				} else if (selected == "October") {
					nutChartLinearLayout3.removeAllViews();
					monthID = "10/";
				} else if (selected == "November") {
					nutChartLinearLayout3.removeAllViews();
					monthID = "11/";
				} else {
					nutChartLinearLayout3.removeAllViews();
					monthID = "12/";
				}
				DatabaseManager database = new DatabaseManager(ViewRecords.this);
				database.open();
				Cursor c = database.getDataOfCorrespondingMonth(monthID,id);
				String[] monthDataRawCholesterol = new String[c.getCount()];
				String[] monthDataRawCarbohydrates = new String[c.getCount()];
				String[] monthDataRawFats = new String[c.getCount()];
				String[] monthDataRawSugar = new String[c.getCount()];
				String[] monthDataRawCalories = new String[c.getCount()];
				String[] monthDataRawAmount = new String[c.getCount()];
				String[] monthDataRawDate = new String[c.getCount()];
				
				double[] monthDataCholesterol = new double[c.getCount()];
				double[] monthDataCarbohydrates = new double[c.getCount()];
				double[] monthDataFats = new double[c.getCount()];
				double[] monthDataSugar = new double[c.getCount()];
				double[] monthDataCalories = new double[c.getCount()];
				
				int monthDataLength = c.getCount();
				
				if(c.moveToFirst()) {
					for(int i=0;i<c.getCount();i++) {
						monthDataRawCholesterol[i] = c.getString(1);
						monthDataRawCarbohydrates[i] = c.getString(2);
						monthDataRawFats[i] = c.getString(3);
						monthDataRawSugar[i] = c.getString(4);
						monthDataRawCalories[i] = c.getString(5);
						monthDataRawAmount[i] = c.getString(6);
						monthDataRawDate[i] = c.getString(7);
						
						monthDataCholesterol[i] = Double.parseDouble(monthDataRawCholesterol[i]) * Double.parseDouble(monthDataRawCholesterol[i]);
						monthDataCarbohydrates[i] = Double.parseDouble(monthDataRawCarbohydrates[i]) * Double.parseDouble(monthDataRawCholesterol[i]);
						monthDataFats[i] = Double.parseDouble(monthDataRawFats[i]) * Double.parseDouble(monthDataRawCholesterol[i]);
						monthDataSugar[i] = Double.parseDouble(monthDataRawSugar[i]) * Double.parseDouble(monthDataRawCholesterol[i]);
						monthDataCalories[i] = Double.parseDouble(monthDataRawCalories[i]) * Double.parseDouble(monthDataRawCholesterol[i]);
						c.moveToNext();
					}
				}
				
				c.close();
				database.close();
				
				CreateLineGraphNutritionalFactsChart linegraph3 = new CreateLineGraphNutritionalFactsChart();
				linegraph3.createLineGraph(monthDataCholesterol, monthDataCarbohydrates, monthDataFats, monthDataSugar, monthDataCalories, monthDataLength, monthDataRawDate,selected,yLabel,monthlyTitle);
				targetView3 = ChartFactory.getLineChartView(ViewRecords.this, linegraph3.nutFactDataSet, linegraph3.nutFactsMultipleSeriesRenderer);
				nutChartLinearLayout3.addView(targetView3,new LayoutParams(LayoutParams.MATCH_PARENT,
				          LayoutParams.MATCH_PARENT));
				
			}

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
			
			
			
		});
		/*This part is for generating the graph with the given information*/
		
		CreateLineGraphNutritionalFactsChart linegraph = new CreateLineGraphNutritionalFactsChart();
		linegraph.createLineGraph(chol,carb,fat,sug,cal,length,dateData,xDateLabel,yLabel,dailyTitle);
		CreateLineGraphNutritionalFactsChart linegraph2 = new CreateLineGraphNutritionalFactsChart();
		linegraph2.createLineGraph(cholDataWeekly, carbDataWeekly, fatDataWeekly, sugarDataWeekly, calDataWeekly, weeklyLength, weeklyDate,xDateLabel,yLabel,weeklyTitle);
		targetView = ChartFactory.getLineChartView(this, linegraph.nutFactDataSet, linegraph.nutFactsMultipleSeriesRenderer);
		nutChartLinearLayout.addView(targetView,new LayoutParams(LayoutParams.MATCH_PARENT,
		          LayoutParams.MATCH_PARENT));
		targetView2 = ChartFactory.getLineChartView(this, linegraph2.nutFactDataSet, linegraph2.nutFactsMultipleSeriesRenderer);
		nutChartLinearLayout2.addView(targetView2,new LayoutParams(LayoutParams.MATCH_PARENT,
		          LayoutParams.MATCH_PARENT));
	}
	
	public void initializeVariables() {
		nutChartLinearLayout = (LinearLayout) findViewById(R.id.dailyChartHolderLinearLayout);
		nutChartLinearLayout2 = (LinearLayout) findViewById(R.id.dailyChartHolderLinearLayout2);
		nutChartLinearLayout3 = (LinearLayout) findViewById(R.id.dailyChartHolderLinearLayout3);
		monthSpinner = (Spinner) findViewById(R.id.spinnerMonth);
		
	}
	

}
