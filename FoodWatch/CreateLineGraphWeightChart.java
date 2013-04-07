package com.cs192.foodwatch;

import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.graphics.Color;


public class CreateLineGraphWeightChart {
	
	public XYMultipleSeriesRenderer weightMultipleSeriesRenderer = new XYMultipleSeriesRenderer(); // Holds a collection of XYSeriesRenderer and customizes the graph
	public XYMultipleSeriesDataset weightDataSet = new XYMultipleSeriesDataset();
	public XYSeriesRenderer targetRenderer = new XYSeriesRenderer();
	public XYSeriesRenderer weightLineRenderer = new XYSeriesRenderer();
	public XYSeriesRenderer initialRenderer = new XYSeriesRenderer();
	
	
	private int length;
	private static final double initialDate = 0;

	
	public void createLine(double[] weight, String[] dates, double initialWeight, double targetWeight, String tarDate) {
		length = weight.length;
		
		
		TimeSeries weightLine = new TimeSeries("WeightLine");
		for (int i =0;i<length;i++) {
			weightLine.add(i+1,weight[i]);
		}
		
		for (int i = 0; i <length; i++) {
	        weightMultipleSeriesRenderer.addXTextLabel(i + 1,dates[i]);
	    }
		
		TimeSeries initialLine = new TimeSeries("Initial Weight");
		initialLine.add(initialDate, initialWeight);
		
		TimeSeries theTarget = new TimeSeries("Target");
		theTarget.add(length, targetWeight);
		
		
		
		weightDataSet.addSeries(initialLine);
		weightDataSet.addSeries(weightLine);
		weightDataSet.addSeries(theTarget);
		weightMultipleSeriesRenderer.addSeriesRenderer(initialRenderer);
		weightMultipleSeriesRenderer.addSeriesRenderer(weightLineRenderer);
		weightMultipleSeriesRenderer.addSeriesRenderer(targetRenderer);
		
		//Customization for chart!
		weightMultipleSeriesRenderer.setChartTitle("Weight Graph");
		weightMultipleSeriesRenderer.setShowAxes(true);
		weightMultipleSeriesRenderer.setMargins(new int[] { 20, 30, 15, 0 });
		weightMultipleSeriesRenderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));
		weightMultipleSeriesRenderer.setApplyBackgroundColor(true);
		weightMultipleSeriesRenderer.setXLabels(0);
		weightMultipleSeriesRenderer.setLabelsColor(Color.BLACK);
		//weightMultipleSeriesRenderer.setYLabels(70);
		weightMultipleSeriesRenderer.setBackgroundColor(Color.TRANSPARENT);
		weightMultipleSeriesRenderer.setAxesColor(Color.BLACK);
		weightMultipleSeriesRenderer.setXAxisMin(0,0);
		weightMultipleSeriesRenderer.setYAxisMin(0,0);
		weightMultipleSeriesRenderer.setXAxisMax(length+10,0);
		//weightMultipleSeriesRenderer.setYAxisMax(max+10,0);
		weightMultipleSeriesRenderer.setPointSize(4);
		weightMultipleSeriesRenderer.setAxisTitleTextSize(16);
		weightMultipleSeriesRenderer.setChartTitleTextSize(20);
		weightMultipleSeriesRenderer.setLabelsTextSize(8);
		weightMultipleSeriesRenderer.setLabelsColor(Color.BLACK);
		weightMultipleSeriesRenderer.setLegendTextSize(15);
		weightMultipleSeriesRenderer.setAxesColor(Color.LTGRAY);
		weightMultipleSeriesRenderer.setXTitle("DATE");
		weightMultipleSeriesRenderer.setYTitle("WEIGHT");
		weightMultipleSeriesRenderer.setGridColor(Color.BLACK);
		
		// Customization time for initial line!
		initialRenderer.setColor(Color.GREEN);
		initialRenderer.setPointStyle(PointStyle.SQUARE);
		initialRenderer.setFillPoints(true);
		
		//Customization time for weight line!
		weightLineRenderer.setColor(Color.BLUE);
		weightLineRenderer.setPointStyle(PointStyle.DIAMOND);
		weightLineRenderer.setFillPoints(true);
		
		//Customization time for target line!
		targetRenderer.setColor(Color.RED);
		targetRenderer.setPointStyle(PointStyle.CIRCLE);
		targetRenderer.setFillPoints(true);
		
	}

	
}
