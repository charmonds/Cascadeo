package com.cs192.foodwatch;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Mainmenu extends Activity 
{
	Button loggedout,records,profile,extras,help,viewdata;
	String id;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainmenu);
		initializeVariables();
		Bundle bundle = getIntent().getExtras();
		id =bundle.getString("ID");
		long x;
		x = Long.parseLong(id);
		if(x == 1) {
			records.setVisibility(View.GONE);
			profile.setVisibility(View.GONE);
			extras.setVisibility(View.GONE);
			help.setVisibility(View.GONE);
			viewdata.setVisibility(View.VISIBLE);
			loggedout.setVisibility(View.VISIBLE);
			
		} else {
			records.setVisibility(View.VISIBLE);
			profile.setVisibility(View.VISIBLE);
			extras.setVisibility(View.VISIBLE);
			help.setVisibility(View.VISIBLE);
			viewdata.setVisibility(View.GONE);
			loggedout.setVisibility(View.VISIBLE);
		}
		
		
		loggedout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(Mainmenu.this);
				AlertDialog alertDialog = builder.create();
				
				alertDialog.setTitle("Notice");
				alertDialog.setMessage("Do you want to logout?");
				alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes",
	                    new DialogInterface.OnClickListener() {
	                        public void onClick(DialogInterface dialog, int whichButton) {
	                            Intent intent1 = new Intent(getApplicationContext(), Login.class);
	                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
	                            startActivity(intent1);
	                            finish();
	                        }

	                    });
				
				 alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No",
		                    new DialogInterface.OnClickListener() {
		                        public void onClick(DialogInterface dialog,
		                                int whichButton) {
		                        }
		                    });
				 
				 alertDialog.show();
			}
		});
	
	
	}

	

	private void initializeVariables() {
		// TODO Auto-generated method stub
		loggedout = (Button) findViewById(R.id.bLogOut);
		records = (Button) findViewById(R.id.bRecords);
		profile = (Button) findViewById(R.id.bProfile);
		extras = (Button) findViewById(R.id.bExtras);
		help = (Button) findViewById(R.id.bHelp);
		viewdata = (Button) findViewById(R.id.viewdatabase);
		
	}



	public void recordSubmenu(View view)
	{
		Intent intent =new Intent(getApplicationContext(),RecordSubmenu.class);
		intent.putExtra("ID", id);
	    startActivity(intent);
	}

	public void profile(View view)
	{
		Intent intent =new Intent(getApplicationContext(),Profile_view.class);
		intent.putExtra("ID", id);
	    startActivity(intent);
	}

	public void extras(View view)
	{
		Intent intent =new Intent(this,BmiTable.class);
		intent.putExtra("ID", id);
	    startActivity(intent);
	}

	public void help(View view)
	{
		Intent intent =new Intent(this, Help.class);
		intent.putExtra("ID", id);
	    startActivity(intent);
	}
	
	public void viewDatabase(View view) {
		Intent intent = new Intent(getApplicationContext(), ViewDatabase.class);
		intent.putExtra("ID", id);
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_mainmenu, menu);
		return true;
	}
}
