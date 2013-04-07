package com.cs192.foodwatch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Profile_view extends Activity 
{
	TextView t;
	String id, result, name, middle, last, email;
	//change to MySQL queries
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_view);
		DatabaseManager database = new DatabaseManager(Profile_view.this);
		database.open();
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("ID");
		name = database.getInfo(id, "firstname");
		middle = database.getInfo(id, "middlename");
		last = database.getInfo(id, "lastname");
		email = database.getInfo(id, "email");
		
		database.close();
		t = new TextView(this);
		
	    t=(TextView)findViewById(R.id.textView5); 
	    t.setText(name);
	    t=(TextView)findViewById(R.id.textView6); 
	    t.setText(middle);
	    t=(TextView)findViewById(R.id.textView7); 
	    t.setText(last);
	    t=(TextView)findViewById(R.id.textView8); 
	    t.setText(email);
	}

	public void dietInfo(View view)
	{
		Intent intent =new Intent(this,Profile_dietitian.class);
		intent.putExtra("ID", id);
	    startActivity(intent);
	}
	
	public void personalInfo(View view)
	{
		Intent intent =new Intent(this,Profile_personal.class);
		intent.putExtra("ID", id);
	    startActivity(intent);
	}

	public void healthInfo(View view)
	{
		Intent intent =new Intent(this,Profile_health.class);
		intent.putExtra("ID", id);
	    startActivity(intent);
	}

	public void edit(View view)
	{
		Intent intent =new Intent(this,Profile_edit.class);
		intent.putExtra("ID", id);
	    startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_profile_view, menu);
		return true;
	}

}
