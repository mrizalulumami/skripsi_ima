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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Register_Activity extends AppCompatActivity {
	EditText nama_lengkap, username, alamat, email, password;
	Button register;

	FirebaseAuth firebaseAuth;
	DatabaseReference refrence;

	ProgressDialog progressDialog;

	TextView login_kembai;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		firebaseAuth = FirebaseAuth.getInstance();


		nama_lengkap=findViewById(R.id.e_namLengkap);
		username=findViewById(R.id.e_username);
		alamat=findViewById(R.id.e_alamat);
		email=findViewById(R.id.e_emailReg);
		password=findViewById(R.id.e_passwordReg);


		register=findViewById(R.id.btn_reg);

		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String txt_username=username.getText().toString();
				String txt_address=alamat.getText().toString();
				String txt_fullname=nama_lengkap.getText().toString();
				String txt_email=email.getText().toString();
				String txt_password=password.getText().toString();

				progressDialog=new ProgressDialog(Register_Activity.this);
				progressDialog.show();

				progressDialog.getWindow().setBackgroundDrawableResource(
						android.R.color.transparent
				);


				if(TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_address) || TextUtils.isEmpty(txt_fullname) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password) ){
					Toast.makeText(Register_Activity.this, "Harus di isi!", Toast.LENGTH_SHORT).show();
					progressDialog.dismiss();
				}else if(txt_password.length()<6){
					Toast.makeText(Register_Activity.this, "password kurang dari 6 digit!", Toast.LENGTH_SHORT).show();
					progressDialog.dismiss();
				}else{
					registeration(txt_fullname,txt_address,txt_username,txt_email,txt_password);
				}
			}
		});
	}

	public void registeration(String namaLengkap, String alamat, String username, String email, String password){
		firebaseAuth.createUserWithEmailAndPassword(email,password)
				.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
						if (task.isSuccessful()){
							FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
							String userid=firebaseUser.getUid();

							refrence= FirebaseDatabase.getInstance().getReference("Users").child(userid);

							HashMap<String,String> hashMap=new HashMap<>();

							hashMap.put("id",userid);
							hashMap.put("fullname",namaLengkap);
							hashMap.put("address",alamat);
							hashMap.put("username",username);
							hashMap.put("photoprofil","default");
							hashMap.put("email",email);
							hashMap.put("password",password);

							refrence.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
								@Override
								public void onComplete(@NonNull Task<Void> task) {
									if(task.isSuccessful()){
										progressDialog.dismiss();
										Intent intent=new Intent(Register_Activity.this,Home_Activity.class);
										intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
										startActivity(intent);
										finish();
									}
								}
							});
						}
					}
				});
	}
}