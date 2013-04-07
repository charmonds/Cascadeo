package com.cs192.foodwatch;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.cs192.foodwatch.R.color;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;


public class LineGraph extends Activity{

	XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); // Holds a collection of XYSeriesRenderer and customizes the graph
	
	
	public Intent getIntent(Context context) {
		
		// Our first data
		int[] x = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }; // x values!
		int[] y =  { 30, 34, 45, 57, 55, 55, 55, 55, 55, 55 }; // y values!
		TimeSeries series = new TimeSeries("Line1"); 
		for( int i = 0; i < x.length; i++)
		{
			series.add(x[i], y[i]);
		}
		
		
		dataset.addSeries(series);
		
		XYSeriesRenderer renderer = new XYSeriesRenderer(); // This will be used to customize line 1
		mRenderer.addSeriesRenderer(renderer);
		
		// Customization time for line 1!
		renderer.setColor(color.Aquamarine);
		renderer.setPointStyle(PointStyle.SQUARE);
		renderer.setFillPoints(true);
		mRenderer.setMargins(new int[]{0,0,0,0});
		mRenderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));
		mRenderer.setApplyBackgroundColor(true);
		//mRenderer.setMarginsColor(Color.TRANSPARENT);
		mRenderer.setBackgroundColor(Color.TRANSPARENT);
		// Customization time for line 2!
		
		Intent intent = ChartFactory.getLineChartIntent(context, dataset, mRenderer, "Line Graph Title");
		return intent;
		
	}

	
	
}
