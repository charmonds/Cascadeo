package com.cs192.foodwatch;

import java.io.File;
import java.io.FileOutputStream;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Environment;
import android.widget.LinearLayout;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

	public class Test extends Activity {
		
	  private GraphicalView mChartView;
	 
	  
	  @Override
	 public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.activity_test);
		  LineGraph line = new LineGraph();
		  Intent lineIntent = line.getIntent(this);
	      //startActivity(lineIntent);
	      LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
	      mChartView = ChartFactory.getLineChartView(this,line.dataset,line.mRenderer);
	      layout.addView(mChartView, new LayoutParams(200,200));
	      
	  }
	  
	


}



