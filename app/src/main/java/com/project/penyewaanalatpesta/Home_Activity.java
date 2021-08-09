package com.project.penyewaanalatpesta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.penyewaanalatpesta.fragments.HistoryFragment;
import com.project.penyewaanalatpesta.fragments.HomeFragment;
import com.project.penyewaanalatpesta.fragments.KeranjangFragment;
import com.project.penyewaanalatpesta.fragments.PesananFragment;
import com.project.penyewaanalatpesta.model.User_model;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home_Activity extends AppCompatActivity {

	private Fragment fragment;
	private FragmentManager fragmentManager;

	DatabaseReference ref;

	FirebaseUser firebaseUser;

	TextView nama;
	CircleImageView profilsy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		BottomNavigationView topNav=findViewById(R.id.topnav);
		fragmentManager = getSupportFragmentManager();

		firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

		nama=findViewById(R.id.namasaya);
		profilsy=findViewById(R.id.profilsy);

		profilsy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i =new Intent(getApplicationContext(),Profil_User.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
			}
		});


		ref= FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid().toString());
		ref.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				User_model user=snapshot.getValue(User_model.class);
				String nama1=user.getFullname();
				nama.setText(nama1);
				if (user.getPhotoprofil().equals("default")){
					profilsy.setImageResource(R.drawable.settings_ic);
				}else{
					Glide.with(getApplicationContext())
							.load(user.getPhotoprofil()).fitCenter()
							.into(profilsy);
				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});

		//setFragment(dashboard);
		fragmentManager.beginTransaction().replace(R.id.containerPost, new HomeFragment()).commit();

		topNav.setItemIconTintList(null);

		topNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
				switch (menuItem.getItemId()){
					case R.id.home_id:
						fragment = new HomeFragment();
						break;
					case R.id.keranjang:
						fragment = new KeranjangFragment();
						break;
					case R.id.pesanan:
						fragment = new PesananFragment();
						break;
					case R.id.history:
						fragment = new HistoryFragment();
						break;
				}
				final FragmentTransaction transaction = fragmentManager.beginTransaction();
				transaction.replace(R.id.containerPost, fragment).commit();
				return true;
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.navmenu, menu);
		return true;
	}

	public void keluar(View view) {
		Yakin();
	}

	public void Yakin(){
		final Dialog dialog=new Dialog(this);

		dialog.setContentView(R.layout.allert_logout);

		dialog.setCanceledOnTouchOutside(false);
		dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT);

		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

		dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;

		Button btnOk = dialog.findViewById(R.id.ok);
		final Button btnCenc = dialog.findViewById(R.id.cencel);
		btnCenc.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		btnOk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Keluarlah();
			}
		});

		dialog.show();
	}

	public void Keluarlah(){
		FirebaseAuth.getInstance().signOut();
		Intent i =new Intent(getApplicationContext(),Login_Activity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(i);
		finish();

	}
}