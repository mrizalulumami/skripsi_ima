package com.project.penyewaanalatpesta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
	private static int SPLASH_TIME_OUT=1000;

	FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (firebaseUser != null) {
					Intent intent = new Intent(MainActivity.this, Home_Activity.class);
					startActivity(intent);
					finish();
				}else {
					Intent inten = new Intent(MainActivity.this, Login_Activity.class);
					startActivity(inten);
					finish();
				}
			}
		},SPLASH_TIME_OUT);
	}
}