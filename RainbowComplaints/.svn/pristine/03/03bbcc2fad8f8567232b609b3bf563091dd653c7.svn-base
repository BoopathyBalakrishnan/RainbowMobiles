package com.rainbowmobiles.app.complaints;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class SuccessActivity extends Activity {

	ImageButton close;
	RelativeLayout successLayout;
	Animation animation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.success);
		initWidgets();
		animation = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slide_in_from_top);
		successLayout.startAnimation(animation);

	}

	/**
	 * 
	 */
	private void initWidgets() {
		close = (ImageButton) findViewById(R.id.close);
		successLayout=(RelativeLayout) findViewById(R.id.success_screen);
		close.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				finish();
				System.exit(0);
			}
		});
	}
}
