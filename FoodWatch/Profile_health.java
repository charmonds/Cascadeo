package com.cs192.foodwatch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Profile_health extends Activity {

	TextView t;
	String information, id;
	public void addAilment(View view)
	{
		Intent intent =new Intent(this,Add_ailment.class);
		intent.putExtra("ID", id);
	    startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_health);
		DatabaseManager database = new DatabaseManager(Profile_health.this);
		database.open();
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("ID");
		information = database.getHealth(id);
		database.close();
		t = new TextView(this);
		
	    t=(TextView)findViewById(R.id.textView2); 
	    t.setText(information);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_profile_health, menu);
		return true;
	}

}
