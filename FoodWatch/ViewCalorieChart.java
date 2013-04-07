package com.cs192.foodwatch;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class ViewCalorieChart extends Activity {
	private GraphicalView targetView;
	String id;
	LinearLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_calorie_chart);
		initializeVariables();
		layout.removeAllViews();
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("ID");
		DatabaseManager database = new DatabaseManager(this);
		database.open();
		Cursor c = database.getDataOfNutFactandReturnAsQuerry(id);
		String[] patientCalories = new String[c.getCount()];
		String[] patientDate = new String[c.getCount()];
		String[] patientAmount = new String[c.getCount()];
		double[] caloriesInfo = new double[c.getCount()];
		double[] amount = new double[c.getCount()];
		if(c.moveToFirst()) {
			for(int i=0;i<c.getCount();i++) {
				patientCalories[i] = c.getString(5);
				patientAmount[i] = c.getString(6);
				patientDate[i] = c.getString(7);
				amount[i] = Double.parseDouble(patientAmount[i]);
				c.moveToNext();
			}
		}
		
		
		for(int i=0;i<c.getCount();i++) {
			caloriesInfo[i] = Double.parseDouble(patientCalories[i])*amount[i];
		}
		
		
		c.close();
		
		String targetCalories = database.getTargetCalories(id);
		String tarDateOfCalories = database.getTargetDateOfCalories(id);
		double tarCalories = Double.parseDouble(targetCalories);
		database.close();
		CreateLineGraphCalorieChart linegraph = new CreateLineGraphCalorieChart();
		linegraph.createLine(caloriesInfo, patientDate, tarCalories, tarDateOfCalories);
		targetView = ChartFactory.getLineChartView(this, linegraph.caloriesDataSet, linegraph.caloriesMultipleSeriesRenderer);
		layout.addView(targetView,new LayoutParams(LayoutParams.MATCH_PARENT,
		          LayoutParams.MATCH_PARENT));
		
	}
	
	public void initializeVariables() {
		layout = (LinearLayout) findViewById(R.id.calorieChartHolder);
	}
	
	
	
	public void setTargetCalories(View view) {
		Intent intent = new Intent(getApplicationContext(), AddTargetCalories.class);
		intent.putExtra("ID", id);
		startActivity(intent);
	}
	
	public void updateChart(View view) {
		Intent intent = new Intent(getApplicationContext(), UpdateWeightChart.class);
		intent.putExtra("ID", id);
		startActivity(intent);
	}
 

}
