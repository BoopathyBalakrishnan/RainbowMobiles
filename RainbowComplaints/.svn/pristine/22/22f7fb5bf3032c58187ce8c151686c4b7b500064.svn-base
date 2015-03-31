package com.rainbowmobiles.app.complaints;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		 Thread background = new Thread() {
	            public void run() {
	                 
	                try {
	                    // Thread will sleep for 5 seconds
	                    sleep(5*1000);
	              // int sleep=ApplicationConstants.SPLASH_TIME;
	                    Intent invokeToMainActivity = new Intent(
	    						getApplicationContext(), MainActivity.class);

	    				startActivity(invokeToMainActivity);
	                     
	                    //Remove activity
	                    finish();
	                     
	                } catch (Exception e) {
	                 
	                }
	            }
	        };
	         
	        // start thread
	        background.start();
		
		/*new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				
				Intent invokeToMainActivity = new Intent(
						getApplicationContext(), MainActivity.class);

				startActivity(invokeToMainActivity);
			}
		}, ApplicationConstants.SPLASH_TIME);*/
	}
}
