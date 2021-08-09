package com.project.penyewaanalatpesta.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.penyewaanalatpesta.R;
import com.project.penyewaanalatpesta.model.User_model;
import com.project.penyewaanalatpesta.model.keranjangmodel.Keranjang_model;
import com.project.penyewaanalatpesta.model.modelbarang.Barang_model;
import com.project.penyewaanalatpesta.model.pesananmodel.Pesanan_model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class Adapter_Keranjang extends RecyclerView.Adapter<Adapter_Keranjang.MyViewHolder> {


	Context context;
	ArrayList<Keranjang_model> keranjang_models;
	 public String stokbarangs;

	DatabaseReference reference,refe;

	public Adapter_Keranjang(Context context, ArrayList<Keranjang_model> keranjang_models) {
		this.context = context;
		this.keranjang_models = keranjang_models;

		reference = FirebaseDatabase.getInstance().getReference();
	}

	@NotNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_keranjang, parent, false);

		return new MyViewHolder(v);
	}

	@Override
	public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, final int position) {

		holder.Namabarang.setText(keranjang_models.get(position).getNamabarang());
		holder.TotalHarga.setText(keranjang_models.get(position).getTotalharga());
		holder.NamaPenyewa.setText(keranjang_models.get(position).getNamapenyewa());
		holder.alamatPenyewa.setText(keranjang_models.get(position).getAlamatpenyewa());
		holder.noHp.setText(keranjang_models.get(position).getNohppenyewa());
		holder.statuspesanan.setText(keranjang_models.get(position).getStatus());
		holder.totpesker.setText(keranjang_models.get(position).getStok());

		if (keranjang_models.get(position).getGambarbarang().equals("default")) {
			holder.gambarbarang.setImageResource(R.drawable.settings_ic);
		} else {
			Glide.with(context)
					.load(keranjang_models.get(position).getGambarbarang()).fitCenter()
					.into(holder.gambarbarang);
		}

		final int stok2=Integer.parseInt(keranjang_models.get(position).getStok());


		refe = FirebaseDatabase.getInstance().getReference("Barangs").child(keranjang_models.get(position).getKeybarangs());

		keranjang_models.get(position).getTotalstok();

		holder.keybarangsview.setText(keranjang_models.get(position).getTotalstok());

		final int stok1=Integer.parseInt(keranjang_models.get(position).getTotalstok());




		final int sisa=stok1-stok2;


		String keynya = keranjang_models.get(position).getKey();

		holder.pesank.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HashMap<String,Object> map=new HashMap<>();
				map.put("jenis","pesanan");

				HashMap<String,Object> map2=new HashMap<>();
				map2.put("stok",String.valueOf(sisa));

				reference.child("Penyewaan").child(keynya).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
					@Override
					public void onComplete(@NonNull Task<Void> task) {
						if(task.isSuccessful()){
							Toast.makeText(context, "Lanjut Memesan!", Toast.LENGTH_SHORT).show();

						}
					}
				});

				refe.updateChildren(map2);
			}
		});

		holder.Delete.setOnClickListener(new View.OnClickListener() {
				 @Override
				 public void onClick(View view) {
					 final Dialog dialog = new Dialog(context);

					 dialog.setContentView(R.layout.allertdeletek);

					 dialog.setCanceledOnTouchOutside(false);
					 dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
							 WindowManager.LayoutParams.WRAP_CONTENT);

					 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

					 dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
					 Button btnbtal = dialog.findViewById(R.id.btnbtaldeletek);
					 Button btnlanjut = dialog.findViewById(R.id.btnokdeletek);

					 btnbtal.setOnClickListener(new View.OnClickListener() {
						 @Override
						 public void onClick(View v) {
							 dialog.cancel();
						 }
					 });
					 btnlanjut.setOnClickListener(new View.OnClickListener() {
						 @Override
						 public void onClick(View v) {
							 reference.child("Penyewaan").child(keynya).removeValue()
									 .addOnSuccessListener(new OnSuccessListener<Void>() {
										 @Override
										 public void onSuccess(Void aVoid) {
											 Toast.makeText(context, "dibatalkan!", Toast.LENGTH_LONG).show();
										 }
									 });
							 dialog.cancel();
						 }
					 });

					 dialog.show();

				 }
			 }
		);

	}

	@Override
	public int getItemCount() {
		return keranjang_models.size();
	}

	public class MyViewHolder extends RecyclerView.ViewHolder {

		ImageView gambarbarang, Delete;
		TextView Namabarang, TotalHarga, NamaPenyewa, alamatPenyewa, noHp, statuspesanan,totpesker,keybarangsview;
		Button pesank;

		public MyViewHolder(@NonNull @NotNull View itemView) {
			super(itemView);

			gambarbarang = itemView.findViewById(R.id.gambarpesanank);
			Delete = itemView.findViewById(R.id.batalkanpesanank);
			pesank = itemView.findViewById(R.id.pesank);

			Namabarang = itemView.findViewById(R.id.nambar_pesanank);
			keybarangsview = itemView.findViewById(R.id.keybarangsview);
			TotalHarga = itemView.findViewById(R.id.totalhargapesanank);
			NamaPenyewa = itemView.findViewById(R.id.namapenyewak);
			alamatPenyewa = itemView.findViewById(R.id.alamatPenyewak);
			noHp = itemView.findViewById(R.id.nohpenyewak);
			statuspesanan = itemView.findViewById(R.id.statuspesanank);
			totpesker = itemView.findViewById(R.id.totstokker);
		}
	}
}
