package com.cs192.foodwatch;

import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.graphics.Color;

public class CreateLineGraphCalorieChart {
	
	public XYMultipleSeriesRenderer caloriesMultipleSeriesRenderer = new XYMultipleSeriesRenderer(); // Holds a collection of XYSeriesRenderer and customizes the graph
	public XYMultipleSeriesDataset caloriesDataSet = new XYMultipleSeriesDataset();
	public XYSeriesRenderer targetCaloriesRenderer = new XYSeriesRenderer();
	public XYSeriesRenderer caloriesweightLineRenderer = new XYSeriesRenderer();
	
	
	private int length;

	
	public void createLine(double[] weight, String[] dates, double targetWeight, String tarDate) {
		length = weight.length;
		
		
		TimeSeries weightLine = new TimeSeries("WeightLine");
		for (int i =0;i<length;i++) {
			weightLine.add(i+1,weight[i]);
		}
		
		for (int i = 0; i <length; i++) {
	        caloriesMultipleSeriesRenderer.addXTextLabel(i + 1,dates[i]);
	    }
		
		TimeSeries theTarget = new TimeSeries("Target");
		theTarget.add(length, targetWeight);
		
		
		
		caloriesDataSet.addSeries(weightLine);
		caloriesDataSet.addSeries(theTarget);
		caloriesMultipleSeriesRenderer.addSeriesRenderer(caloriesweightLineRenderer);
		caloriesMultipleSeriesRenderer.addSeriesRenderer(targetCaloriesRenderer);
		
		//Customization for chart!
		caloriesMultipleSeriesRenderer.setChartTitle("Calories Graph");
		caloriesMultipleSeriesRenderer.setShowAxes(true);
		caloriesMultipleSeriesRenderer.setMargins(new int[] { 20, 30, 15, 0 });
		caloriesMultipleSeriesRenderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));
		caloriesMultipleSeriesRenderer.setApplyBackgroundColor(true);
		caloriesMultipleSeriesRenderer.setXLabels(0);
		caloriesMultipleSeriesRenderer.setLabelsColor(Color.BLACK);
		//caloriesMultipleSeriesRenderer.setYLabels(70);
		caloriesMultipleSeriesRenderer.setBackgroundColor(Color.TRANSPARENT);
		caloriesMultipleSeriesRenderer.setAxesColor(Color.BLACK);
		caloriesMultipleSeriesRenderer.setXAxisMin(0,0);
		caloriesMultipleSeriesRenderer.setYAxisMin(0,0);
		caloriesMultipleSeriesRenderer.setXAxisMax(length+10,0);
		//caloriesMultipleSeriesRenderer.setYAxisMax(max+10,0);
		caloriesMultipleSeriesRenderer.setPointSize(4);
		caloriesMultipleSeriesRenderer.setAxisTitleTextSize(16);
		caloriesMultipleSeriesRenderer.setChartTitleTextSize(20);
		caloriesMultipleSeriesRenderer.setLabelsTextSize(8);
		caloriesMultipleSeriesRenderer.setLabelsColor(Color.BLACK);
		caloriesMultipleSeriesRenderer.setLegendTextSize(15);
		caloriesMultipleSeriesRenderer.setAxesColor(Color.LTGRAY);
		caloriesMultipleSeriesRenderer.setXTitle("DATE");
		caloriesMultipleSeriesRenderer.setYTitle("CALORIES");
		caloriesMultipleSeriesRenderer.setGridColor(Color.BLACK);
		
		
		//Customization time for weight line!
		caloriesweightLineRenderer.setColor(Color.BLUE);
		caloriesweightLineRenderer.setPointStyle(PointStyle.DIAMOND);
		caloriesweightLineRenderer.setFillPoints(true);
		
		//Customization time for target line!
		targetCaloriesRenderer.setColor(Color.RED);
		targetCaloriesRenderer.setPointStyle(PointStyle.CIRCLE);
		targetCaloriesRenderer.setFillPoints(true);
		
	}

}
