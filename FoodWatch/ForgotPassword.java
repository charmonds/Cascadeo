package com.cs192.foodwatch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ForgotPassword extends Activity {
	
	Button forgot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);
		forgot = (Button) findViewById(R.id.bsubmitforgotten);
		
		forgot.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassword.this);
				AlertDialog alertDialog = builder.create();
				
				alertDialog.setTitle("Notice");
				alertDialog.setMessage("Request received! Please check your e-mail address for confirmation.");
				alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
	                    new DialogInterface.OnClickListener() {
	                        public void onClick(DialogInterface dialog, int whichButton) {
	                            Intent intent1 = new Intent(getApplicationContext(), Login.class);
	                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
	                            startActivity(intent1);
	                            finish();
	                        }

	                    });
				
				alertDialog.show();
			}
		});
	}

}
