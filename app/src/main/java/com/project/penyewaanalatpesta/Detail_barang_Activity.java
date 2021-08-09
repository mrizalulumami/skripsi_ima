package com.project.penyewaanalatpesta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.penyewaanalatpesta.model.User_model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class Detail_barang_Activity extends AppCompatActivity {

	String keynya,namabarangnya,hargabarangnya,stokbrangnya,gambarbarangnya,jenisbarangnya,idPenjual;

	ImageView gambarproduk;
	TextView nama_barangView, harga_barangView, stokView,viewJumlhHarga,jumlahbelinya,namapembeliview,alamatpembeliview,rentangSewa,tanggal_sewa;
	EditText nomorpembeli;
	Button keranjang, pesan;
	Button btnTambah, btnKurang;
	Integer harga1,totalharga;
	Integer jumlahBunga=1;
	Integer stok;
	int tgglsewa,tgglrentang;

	Calendar rentgglsewa,tanggalsewa;
	DatePickerDialog.OnDateSetListener datesewa,daterentang;


	DatabaseReference reference,ref;
	FirebaseUser firebaseUser;
	String namapembeli,alamatpembeli;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_barang);
		//imageview
		gambarproduk = findViewById(R.id.fotoBeliProduk);
		//textview
		nama_barangView = findViewById(R.id.namaBeliProduk);
		harga_barangView = findViewById(R.id.hargaBeliProduk);
		stokView = findViewById(R.id.stokproduk);
		rentangSewa =findViewById(R.id.rentang_sewa);
		tanggal_sewa =findViewById(R.id.tanggal_sewa);
		namapembeliview = findViewById(R.id.namaPembeli);
		alamatpembeliview = findViewById(R.id.alamatPembeli);
		//edittex
		nomorpembeli = findViewById(R.id.noHpPembeli);

		viewJumlhHarga = findViewById(R.id.totalharga);
		jumlahbelinya = findViewById(R.id.jmlhBeli);
		//tombol
		keranjang = findViewById(R.id.btnkeranjang);
		pesan = findViewById(R.id.btnBeli);
		//tombol
		btnTambah = findViewById(R.id.btnplus);
		btnKurang = findViewById(R.id.btnmmines);

		firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

		reference= FirebaseDatabase.getInstance().getReference("Penyewaan").push();


		keynya=getIntent().getStringExtra("KEY");
		namabarangnya=getIntent().getStringExtra("NAMA_BARANG");
		hargabarangnya=getIntent().getStringExtra("HARGA_BARANG");
		stokbrangnya=getIntent().getStringExtra("STOK_BARANG");
		gambarbarangnya=getIntent().getStringExtra("GAMBAR_BARANG");
		jenisbarangnya=getIntent().getStringExtra("JENIS_BARANG");
		idPenjual=getIntent().getStringExtra("ID_PENJUAL");




		ref= FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid().toString());
		ref.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				User_model user=snapshot.getValue(User_model.class);
				namapembeli=user.getFullname();
				alamatpembeli=user.getAddress();
				namapembeliview.setText(namapembeli);
				alamatpembeliview.setText(alamatpembeli);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});


		if (gambarbarangnya.equals("default")){
			gambarproduk.setImageResource(R.drawable.settings_ic);
		}else{
			Glide.with(getApplicationContext())
					.load(gambarbarangnya).fitCenter()
					.into(gambarproduk);
		}

		nama_barangView.setText(namabarangnya);
		harga_barangView.setText(hargabarangnya);
		stokView.setText(stokbrangnya);

		stok=Integer.parseInt(stokbrangnya);



		harga1 = Integer.parseInt(hargabarangnya);

		jumlahbelinya.setText(jumlahBunga.toString());
		viewJumlhHarga.setText(String.valueOf(jumlahBunga*harga1));




		btnTambah.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				jumlahBunga +=1;
				jumlahbelinya.setText(jumlahBunga.toString());
				viewJumlhHarga.setText(String.valueOf(harga1*jumlahBunga));

				totalharga=harga1*jumlahBunga;
				if (jumlahBunga>=stok){
					btnTambah.setEnabled(false);
				}else {
					btnKurang.setEnabled(true);
				}
			}
		});
		btnKurang.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				jumlahBunga -=1;
				jumlahbelinya.setText(jumlahBunga.toString());
				viewJumlhHarga.setText(String.valueOf(harga1*jumlahBunga));
				totalharga=harga1*jumlahBunga;

				if (jumlahBunga<=1){
					btnKurang.setEnabled(false);
				}else {
					btnTambah.setEnabled(true);
				}
			}
		});

		keranjang.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				keranjang();
			}
		});
		pesan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pesanan();
			}
		});

		rentgglsewa = Calendar.getInstance();
		tanggalsewa = Calendar.getInstance();
		datesewa = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
								  int dayOfMonth) {
				tanggalsewa.set(Calendar.YEAR, year);
				tanggalsewa.set(Calendar.MONTH, monthOfYear);
				tanggalsewa.set(Calendar.DAY_OF_MONTH, dayOfMonth);

				TextView tanggalnya = findViewById(R.id.tanggal_sewa);
				String myFormat = "dd-MMMM-yyyy";
				SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
				tanggalnya.setText(sdf.format(tanggalsewa.getTime()));
			}
		};
		daterentang = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
								  int dayOfMonth) {
				rentgglsewa.set(Calendar.YEAR, year);
				rentgglsewa.set(Calendar.MONTH, monthOfYear);
				rentgglsewa.set(Calendar.DAY_OF_MONTH, dayOfMonth);

				TextView tanggalrentang = findViewById(R.id.rentang_sewa);
				String myFormat = "dd-MMMM-yyyy";
				SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
				tanggalrentang.setText(sdf.format(rentgglsewa.getTime()));
			}
		};

		rentangSewa.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new DatePickerDialog(Detail_barang_Activity.this, daterentang,
						rentgglsewa.get(Calendar.YEAR),
						rentgglsewa.get(Calendar.MONTH),
						rentgglsewa.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		tanggal_sewa.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new DatePickerDialog(Detail_barang_Activity.this, datesewa,
						tanggalsewa.get(Calendar.YEAR),
						tanggalsewa.get(Calendar.MONTH),
						tanggalsewa.get(Calendar.DAY_OF_MONTH)).show();
			}
		});


		tgglsewa = tanggalsewa.get(Calendar.DAY_OF_MONTH);
		tgglrentang = rentgglsewa.get(Calendar.DAY_OF_MONTH);






	}

	public void keranjang(){
		HashMap<String,Object> map=new HashMap<>();
		map.put("idpenyewa",firebaseUser.getUid());
		map.put("idpenjual",idPenjual);
		map.put("namapenyewa",namapembeli);
		map.put("alamatpenyewa",alamatpembeli);
		map.put("namabarang",namabarangnya);
		map.put("nohppenyewa",nomorpembeli.getText().toString());
		map.put("gambarbarang",gambarbarangnya);
		map.put("totalharga",String.valueOf(totalharga));
		map.put("stok",String.valueOf(jumlahBunga));
		map.put("totalstok",stokbrangnya);
		map.put("tgglsewa",tanggalsewa.get(Calendar.DAY_OF_MONTH));
		map.put("rentgsewa",rentgglsewa.get(Calendar.DAY_OF_MONTH));
		map.put("jenis","keranjang");
		map.put("keybarangs",keynya);
		map.put("status","menunggu proses");
		reference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
			@Override
			public void onComplete(@NonNull Task<Void> task) {
				if(task.isSuccessful()){
					Toast.makeText(getApplicationContext(), "ditambah ke Keranjang!", Toast.LENGTH_SHORT).show();
					Intent i=new Intent(getApplicationContext(),Home_Activity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(i);
				}
			}
		});

	}

	public void pesanan(){
		HashMap<String,Object> map=new HashMap<>();
		map.put("idpenyewa",firebaseUser.getUid());
		map.put("idpenjual",idPenjual);
		map.put("namapenyewa",namapembeli);
		map.put("alamatpenyewa",alamatpembeli);
		map.put("namabarang",namabarangnya);
		map.put("nohppenyewa",nomorpembeli.getText().toString());
		map.put("gambarbarang",gambarbarangnya);
		map.put("totalharga",String.valueOf(totalharga));
		map.put("stok",String.valueOf(jumlahBunga));
		map.put("totalstok",stokbrangnya);
		map.put("keybarangs",keynya);
		map.put("tgglsewa",tanggalsewa.get(Calendar.DAY_OF_MONTH));
		map.put("rentgsewa",rentgglsewa.get(Calendar.DAY_OF_MONTH));
		map.put("jenis","pesanan");
		map.put("status","menunggu proses");
		reference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
			@Override
			public void onComplete(@NonNull Task<Void> task) {
				if(task.isSuccessful()){
					Toast.makeText(getApplicationContext(), "ditambah ke daftar pesanan!", Toast.LENGTH_SHORT).show();
					Toast.makeText(getApplicationContext(), "ditambah ke Keranjang!", Toast.LENGTH_SHORT).show();
					Intent i=new Intent(getApplicationContext(),Home_Activity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(i);
				}
			}
		});
	}
}