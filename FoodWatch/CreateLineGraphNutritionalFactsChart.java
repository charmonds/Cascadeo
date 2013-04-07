package com.cs192.foodwatch;

import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.cs192.foodwatch.R.color;

import android.graphics.Color;

public class CreateLineGraphNutritionalFactsChart {
	
	
	public XYMultipleSeriesRenderer nutFactsMultipleSeriesRenderer = new XYMultipleSeriesRenderer(); // Holds a collection of XYSeriesRenderer and customizes the graph
	public XYMultipleSeriesDataset nutFactDataSet = new XYMultipleSeriesDataset();
	public XYSeriesRenderer cholesterolRenderer = new XYSeriesRenderer();
	public XYSeriesRenderer carbohydratesRenderer = new XYSeriesRenderer();
	public XYSeriesRenderer fatsRenderer = new XYSeriesRenderer();
	public XYSeriesRenderer sugarRenderer = new XYSeriesRenderer();
	public XYSeriesRenderer caloriesRenderer = new XYSeriesRenderer();

	public void createLineGraph(double[] chol, double[] carb, double[] fat,
			double[] sug, double[] cal,  int length, String[] dateData, String xTitle, String yTitle, String theTitle) {
		// TODO Auto-generated method stub
		
		
		TimeSeries cholesterolLine = new TimeSeries("Cholesterol");
		TimeSeries carbohydratesLine = new TimeSeries("Carbohydrates");
		TimeSeries fatLine = new TimeSeries("Fats");
		TimeSeries sugarLine = new TimeSeries("Sugar");
		TimeSeries caloriesLine = new TimeSeries("Calories");
		for (int i =0;i<length;i++) {
			cholesterolLine.add(i+1,chol[i]);
			carbohydratesLine.add(i+1, carb[i]);
			fatLine.add(i+1, fat[i]);
			sugarLine.add(i+1, sug[i]);
			caloriesLine.add(i+1, cal[i]);
		}
		
		for (int i = 0; i <length; i++) {
	        nutFactsMultipleSeriesRenderer.addXTextLabel(i + 1,dateData[i]);
	    }
		
		
		
		nutFactDataSet.addSeries(cholesterolLine);
		nutFactDataSet.addSeries(carbohydratesLine);
		nutFactDataSet.addSeries(fatLine);
		nutFactDataSet.addSeries(sugarLine);
		nutFactDataSet.addSeries(caloriesLine);
		nutFactsMultipleSeriesRenderer.addSeriesRenderer(cholesterolRenderer);
		nutFactsMultipleSeriesRenderer.addSeriesRenderer(carbohydratesRenderer);
		nutFactsMultipleSeriesRenderer.addSeriesRenderer(fatsRenderer);
		nutFactsMultipleSeriesRenderer.addSeriesRenderer(sugarRenderer);
		nutFactsMultipleSeriesRenderer.addSeriesRenderer(caloriesRenderer);
		
		
		
		//Customization for chart!
		nutFactsMultipleSeriesRenderer.setChartTitle(theTitle);
		nutFactsMultipleSeriesRenderer.setShowAxes(true);
		nutFactsMultipleSeriesRenderer.setMargins(new int[] { 20, 30, 15, 0 });
		nutFactsMultipleSeriesRenderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));
		nutFactsMultipleSeriesRenderer.setApplyBackgroundColor(true);
		nutFactsMultipleSeriesRenderer.setXLabels(0);
		nutFactsMultipleSeriesRenderer.setLabelsColor(Color.BLACK);
		//nutFactsMultipleSeriesRenderer.setYLabels(70);
		nutFactsMultipleSeriesRenderer.setBackgroundColor(Color.TRANSPARENT);
		nutFactsMultipleSeriesRenderer.setAxesColor(Color.BLACK);
		nutFactsMultipleSeriesRenderer.setXAxisMin(0,0);
		nutFactsMultipleSeriesRenderer.setYAxisMin(0,0);
		nutFactsMultipleSeriesRenderer.setXAxisMax(length+10,0);
		//nutFactsMultipleSeriesRenderer.setYAxisMax(max+10,0);
		nutFactsMultipleSeriesRenderer.setPointSize(4);
		nutFactsMultipleSeriesRenderer.setAxisTitleTextSize(16);
		nutFactsMultipleSeriesRenderer.setChartTitleTextSize(20);
		nutFactsMultipleSeriesRenderer.setLabelsTextSize(8);
		nutFactsMultipleSeriesRenderer.setLabelsColor(Color.BLACK);
		nutFactsMultipleSeriesRenderer.setLegendTextSize(15);
		nutFactsMultipleSeriesRenderer.setAxesColor(Color.LTGRAY);
		nutFactsMultipleSeriesRenderer.setXTitle(xTitle);
		nutFactsMultipleSeriesRenderer.setYTitle(yTitle);
		nutFactsMultipleSeriesRenderer.setGridColor(Color.BLACK);
		
		//Customization time for cholesterol line!
		cholesterolRenderer.setColor(Color.RED);
		cholesterolRenderer.setPointStyle(PointStyle.CIRCLE);
		cholesterolRenderer.setFillPoints(true);
		
		//Customization time for carbohydrate line!
		carbohydratesRenderer.setColor(Color.BLUE);
		carbohydratesRenderer.setPointStyle(PointStyle.DIAMOND);
		carbohydratesRenderer.setFillPoints(true);
		
		// Customization time for fats line!
		fatsRenderer.setColor(Color.GREEN);
		fatsRenderer.setPointStyle(PointStyle.SQUARE);
		fatsRenderer.setFillPoints(true);
		
		//Customization time for sugar line!
		sugarRenderer.setColor(color.Brown);
		sugarRenderer.setPointStyle(PointStyle.POINT);
		sugarRenderer.setFillPoints(true);
		
		//Customization time for calories line!
		caloriesRenderer.setColor(color.Pink);
		caloriesRenderer.setPointStyle(PointStyle.TRIANGLE);
		caloriesRenderer.setFillPoints(true);
			
	}

}
