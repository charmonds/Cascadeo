package com.cs192.foodwatch;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class Profile_personal extends Activity {
		TextView t;
		String id, age, occupation, iweight, iheight;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_personal);
		DatabaseManager database = new DatabaseManager(Profile_personal.this);
		database.open();
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("ID");
		age = database.getPersonal(id, "age");
		occupation = database.getPersonal(id, "occupation");
		iweight = database.getInfo(id, "Iweight");
		iheight = database.getInfo(id, "Fweight");
		
		database.close();
		t = new TextView(this);
		
	    t=(TextView)findViewById(R.id.textView5); 
	    t.setText(age);
	    t=(TextView)findViewById(R.id.textView6); 
	    t.setText(occupation);
	    t=(TextView)findViewById(R.id.textView7); 
	    t.setText(iweight);
	    t=(TextView)findViewById(R.id.textView8); 
	    t.setText(iheight);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_profile_personal, menu);
		return true;
	}

}
