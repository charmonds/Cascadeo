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

public class ViewWeightChart extends Activity {
	private GraphicalView targetView;
	String id;
	LinearLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_weight_chart);
		initializeVariables();
		layout.removeAllViews();
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("ID");
		long l = Long.parseLong(id);
		DatabaseManager database = new DatabaseManager(ViewWeightChart.this);
		database.open();
		Cursor c = database.getDataAndReturnedAsQuery(id);
		String[] patientWeights = new String[c.getCount()];
		String[] patientDate = new String[c.getCount()];
		double[] weightInfo = new double[c.getCount()];
		if(c.moveToFirst()) {
			for(int i=0;i<c.getCount();i++) {
				patientWeights[i] = c.getString(0);
				patientDate[i] = c.getString(1);
				c.moveToNext();
			}
		}
		
		
		for(int i=0;i<c.getCount();i++) {
			weightInfo[i] = Double.parseDouble(patientWeights[i]);
		}
		
		String initWeight = database.getDataInitialWeight(l);
		double initialweight = Double.parseDouble(initWeight);
		
		c.close();
		
		String tarWeight = database.getTargetWeight(id);
		String tarDate = database.getTargetDate(id);
		double targetWeight = Double.parseDouble(tarWeight);
		database.close();
		CreateLineGraphWeightChart linegraph = new CreateLineGraphWeightChart();
		linegraph.createLine(weightInfo, patientDate, initialweight,targetWeight,tarDate);
		targetView = ChartFactory.getLineChartView(this, linegraph.weightDataSet, linegraph.weightMultipleSeriesRenderer);
		layout.addView(targetView,new LayoutParams(LayoutParams.MATCH_PARENT,
		          LayoutParams.MATCH_PARENT));
		
	}
	
	public void initializeVariables() {
		layout = (LinearLayout) findViewById(R.id.weightChartHolder);
	}
	
	
	public void updateChart(View view) {
		Intent intent = new Intent(getApplicationContext(), UpdateWeightChart.class);
		intent.putExtra("ID", id);
		startActivity(intent);
	}
	
	public void setTargetWeight(View view) {
		Intent intent = new Intent(getApplicationContext(), AddTarget.class);
		intent.putExtra("ID", id);
		startActivity(intent);
	}
 

}
