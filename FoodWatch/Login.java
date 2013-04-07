package com.cs192.foodwatch;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends Activity

{	
	//TextView t;
	EditText username,password;
	String id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initializeVariables();
		
	}
	
	private void initializeVariables() {
		// TODO Auto-generated method stub
		username = (EditText) findViewById(R.id.etLogInUsername);
		password = (EditText) findViewById(R.id.etLogInPassword);
	}

	public void login(View view)
	{
		
		
		String loginName = username.getText().toString();
		String pass = password.getText().toString();
		
		DatabaseManager database = new DatabaseManager(Login.this);
		database.open();
		id = database.getID(loginName);
		database.close();
		Intent intent =new Intent(getApplicationContext(),Mainmenu.class);
		intent.putExtra("ID", id);
		startActivity(intent);

		
	}
	
	public void register(View view)
	{
		Intent intent = new Intent(this,Register.class);
	    startActivity(intent);
	}
	
	public void forgot(View view)
	{
		Intent intent = new Intent(this,ForgotPassword.class);
	    startActivity(intent);
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

}
