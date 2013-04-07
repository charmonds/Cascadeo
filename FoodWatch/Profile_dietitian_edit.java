package com.cs192.foodwatch;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Profile_dietitian_edit extends Activity {
	
	TextView t;
	String id;
	EditText fname,mname,lname,email, schedule;
	
	public void done(View view)
	{
		Intent intent =new Intent(this,Profile_dietitian.class);
		initializeVariables();
		boolean didItWork = true;
		String first = fname.getText().toString();
		String middle = mname.getText().toString();
		String last = lname.getText().toString();
		String mail = email.getText().toString();
		String sched = schedule.getText().toString();
		
		if(!mail.contains("@")){
			t = new TextView(this);
			t=(TextView)findViewById(R.id.textView5); 
			t.setText("**Invalid email address");
		}else{
			
		
			try {
				
				
				DatabaseManager info = new DatabaseManager(Profile_dietitian_edit.this);
				info.open();
				Bundle bundle = getIntent().getExtras();
				id = bundle.getString("ID");
				info.updateDietitian(id, first, middle, last, mail, sched);
				info.close();
			} catch (Exception e) {
				Dialog d= new Dialog(this);
				String error = e.toString();
				TextView tv = new TextView(this);
				d.setTitle("ERROR!");
				tv.setText(error);
				d.setContentView(tv);
				d.show();
				startActivity(intent);
				
				didItWork = false;
			} finally {
				if (didItWork) {
					Dialog d= new Dialog(this);
					TextView tv = new TextView(this);
					d.setTitle("Notice");
					tv.setText("Your Profile has been updated!");
					d.setContentView(tv);
					d.show();
				}
				
			}
		
			intent.putExtra("ID", id);
			startActivity(intent);
		}
	}
	
	public void initializeVariables() {
		fname = (EditText) findViewById(R.id.editText1);
		mname = (EditText) findViewById(R.id.editText2);
		lname = (EditText) findViewById(R.id.editText3);
		email = (EditText) findViewById(R.id.editText4);
		schedule = (EditText) findViewById(R.id.editText5);
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_dietitian_edit);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_profile_dietitian_edit, menu);
		return true;
	}

}
