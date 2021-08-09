package com.project.penyewaanalatpesta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Activity extends AppCompatActivity {
	FirebaseAuth mAuth;
	EditText et_email,et_password;

	Button loginBtn;
	TextView createAccount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mAuth=FirebaseAuth.getInstance();

		et_email = findViewById(R.id.email_login);
		et_password = findViewById(R.id.password_login);

		loginBtn=findViewById(R.id.btn_login);
		createAccount=findViewById(R.id.buataccount);

		createAccount.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),Register_Activity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(i);
			}
		});

		loginBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String txt_email = et_email.getText().toString();
				String txt_password = et_password.getText().toString();

				final ProgressDialog progressDialog=new ProgressDialog(Login_Activity.this);

				progressDialog.show();

				progressDialog.setContentView(R.layout.dialog_progres);

				progressDialog.getWindow().setBackgroundDrawableResource(
						android.R.color.transparent
				);

				if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
					Toast.makeText(Login_Activity.this, "Harus di isi semuanya!", Toast.LENGTH_SHORT).show();
				} else {
					mAuth.signInWithEmailAndPassword(txt_email, txt_password)
							.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
								@Override
								public void onComplete(@NonNull Task<AuthResult> task) {
									if (task.isSuccessful()) {
										progressDialog.dismiss();
										Intent intent = new Intent(Login_Activity.this, Home_Activity.class);
										intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
										startActivity(intent);
										finish();
									} else {
										progressDialog.dismiss();
										Toast.makeText(Login_Activity.this, "Login Gagal!", Toast.LENGTH_SHORT).show();
									}
								}
							});
				}
			}
		});



	}
}