package com.cs192.foodwatch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Profile_dietitian extends Activity {
	
	TextView t;
	String id, name, middle, last, email, schedule;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_dietitian);
		DatabaseManager database = new DatabaseManager(Profile_dietitian.this);
		database.open();
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("ID");
		name = database.getDietitian(id, "fname");
		middle = database.getDietitian(id, "mname");
		last = database.getDietitian(id, "lname");
		email = database.getDietitian(id, "email");
		schedule = database.getDietitian(id, "schedule");
		
		database.close();
		t = new TextView(this);
		
	    t=(TextView)findViewById(R.id.textView9); 
	    t.setText(name);
	    t=(TextView)findViewById(R.id.textView8); 
	    t.setText(middle);
	    t=(TextView)findViewById(R.id.textView7); 
	    t.setText(last);
	    t=(TextView)findViewById(R.id.textView5); 
	    t.setText(email);
	    t=(TextView)findViewById(R.id.textView20); 
	    t.setText(schedule);
		
	}
	
	public void edit(View view) {
		Intent intent =new Intent(this,Profile_dietitian_edit.class);
		intent.putExtra("ID", id);
	    startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_profile_dietitian, menu);
		return true;
	}

}
