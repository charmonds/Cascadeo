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

public class Register extends Activity 
{
	TextView t;
	Button clear;
	EditText fname,mname,lname,email,pass,repass,weight,height;
	public static final String initialTarget = "0";
	public static final String initialDateOfTarget = "0";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initializeVariables();
		
		clear.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				fname.setText("");
				mname.setText("");
				lname.setText("");
				email.setText("");
				pass.setText("");
				repass.setText("");
				weight.setText("");
				height.setText("");
				
			}
		});
	}
	
	public void done(View view)
	{
		Intent intent =new Intent(getApplicationContext(),RegSuccess.class);
		boolean didItWork = true;
		String first = fname.getText().toString();
		String middle = mname.getText().toString();
		String last = lname.getText().toString();
		String mail = email.getText().toString();
		String p = pass.getText().toString();
		String rp = repass.getText().toString();
		String w = weight.getText().toString();
		String h = height.getText().toString();
		
		if(!p.equals(rp)){
			t = new TextView(this);
			t=(TextView)findViewById(R.id.textView1); 
			t.setText("**Passwords need to be identical");
		}else if(!mail.contains("@")){
			t = new TextView(this);
			t=(TextView)findViewById(R.id.textView1); 
			t.setText("**Invalid email address");
		}else{
			
		
			try {
				
				
				DatabaseManager entry = new DatabaseManager(Register.this);
				entry.open();
				entry.createEntry(first,middle,last,mail,p,w,h);
				String id = entry.getID(mail);
				entry.createDietitianDatabase(id);
				entry.createHealthDatabase(id);
				entry.createPersonalDatabase(id);
				entry.createWeightDatabase(id);
				entry.createTargetDatabase(id);
				entry.createNutritionalFactDatabase(id);
				entry.createCalorieTargetDatabase(id);
				entry.createTarget(initialDateOfTarget, initialTarget,id);
				entry.createCalorieTarget(initialDateOfTarget, initialTarget,id);
				entry.close();
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
		
		
			startActivity(intent);
		}
	}
	
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_register, menu);
		return true;
	}
	
	public void initializeVariables() {
		clear = (Button) findViewById(R.id.registerButtonClear);
		fname = (EditText) findViewById(R.id.etRegisterFname);
		mname = (EditText) findViewById(R.id.etRegisterMname);
		lname = (EditText) findViewById(R.id.etRegisterLname);
		email = (EditText) findViewById(R.id.etRegisterEmail);
		pass = (EditText) findViewById(R.id.etRegisterPassword);
		repass = (EditText) findViewById(R.id.etRegisterReEnterPassword);
		weight = (EditText) findViewById(R.id.etRegisterWeight);
		height = (EditText) findViewById(R.id.etRegisterHeight);
		
	}

}
