package com.cs192.foodwatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LogoutSuccess extends Activity {
	
	public void mainMenu(View view)
	{
		Intent intent =new Intent(this,Mainmenu.class);
	    startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logout_sucess);
	}

}
