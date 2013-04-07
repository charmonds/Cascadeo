package com.cs192.foodwatch;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Profile_edit extends Activity 
{
	TextView t;
	String id;
	EditText fname,mname,lname,email;
	
	public void done(View view)
	{	    
		//make queries (get data in field), and exit, return to profile.
		Intent intent =new Intent(this,Profile_view.class);
		initializeVariables();
		boolean didItWork = true;
		String first = fname.getText().toString();
		String middle = mname.getText().toString();
		String last = lname.getText().toString();
		String mail = email.getText().toString();
		
		if(!mail.contains("@")){
			t = new TextView(this);
			t=(TextView)findViewById(R.id.textView5); 
			t.setText("**Invalid email address");
		}else{
			
		
			try {
				
				
				DatabaseManager info = new DatabaseManager(Profile_edit.this);
				info.open();
				Bundle bundle = getIntent().getExtras();
				id = bundle.getString("ID");
				info.updateEntry(id, first, middle, last, mail);
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
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_edit);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_profile_edit, menu);
		return true;
	}

}
