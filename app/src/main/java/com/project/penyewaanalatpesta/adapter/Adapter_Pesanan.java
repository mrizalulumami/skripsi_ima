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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.penyewaanalatpesta.R;
import com.project.penyewaanalatpesta.model.pesananmodel.Pesanan_model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class Adapter_Pesanan extends RecyclerView.Adapter<Adapter_Pesanan.MyViewHolder> {


	Context context;
	ArrayList<Pesanan_model> pesanan_models;

	DatabaseReference reference,refe;
	FirebaseUser firebaseUser;
	String keynya;
	Integer tgglsewa;
	Integer rentgsewa;
	Integer operasi;
	String total;
	Integer totalsemua;

	public Adapter_Pesanan(Context context, ArrayList<Pesanan_model> pesanan_models) {
		this.context = context;
		this.pesanan_models = pesanan_models;

		reference= FirebaseDatabase.getInstance().getReference();
		firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
	}

	@NotNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pesanan,parent,false);

		return new MyViewHolder(v);
	}

	@Override
	public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, final int position) {

		holder.Namabarang.setText(pesanan_models.get(position).getNamabarang());
		holder.NamaPenyewa.setText(pesanan_models.get(position).getNamapenyewa());
		holder.alamatPenyewa.setText(pesanan_models.get(position).getAlamatpenyewa());
		holder.noHp.setText(pesanan_models.get(position).getNohppenyewa());
		holder.statuspesanan.setText(pesanan_models.get(position).getStatus());
		holder.totpes.setText(pesanan_models.get(position).getStok());

		holder.tgglPeminjaman.setText(pesanan_models.get(position).getTanggalsewa());
		holder.rentang_sewa.setText(pesanan_models.get(position).getRentangsewa());

		tgglsewa = pesanan_models.get(position).getTgglsewa();
		rentgsewa = pesanan_models.get(position).getRentgsewa();
		operasi=rentgsewa-tgglsewa;

		total = pesanan_models.get(position).getTotalharga();

		totalsemua=operasi * Integer.parseInt(total);

		holder.TotalHarga.setText(String.valueOf(totalsemua));




		if (pesanan_models.get(position).getGambarbarang().equals("default")){
			holder.gambarbarang.setImageResource(R.drawable.settings_ic);
		}else{
			Glide.with(context)
					.load(pesanan_models.get(position).getGambarbarang()).fitCenter()
					.into(holder.gambarbarang);
		}

		keynya=pesanan_models.get(position).getKey();

		if (pesanan_models.get(position).getStatus().equals("di proses")){
			holder.terima.setEnabled(false);
			holder.Delete.setVisibility(View.GONE);
		} else if (pesanan_models.get(position).getStatus().equals("sampai")){
			holder.terima.setEnabled(true);;
			holder.Delete.setVisibility(View.GONE);
		}else if (pesanan_models.get(position).getStatus().equals("menunggu proses")){
			holder.terima.setEnabled(false);
			holder.Delete.setVisibility(View.VISIBLE);
		}else if (pesanan_models.get(position).getStatus().equals("di kirim")){
			holder.terima.setEnabled(false);
			holder.Delete.setVisibility(View.GONE);
		}else if (pesanan_models.get(position).getStatus().equals("di terima")){
			holder.terima.setEnabled(false);
			holder.Delete.setVisibility(View.GONE);
		}

		holder.terima.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				refe = FirebaseDatabase.getInstance().getReference();

				HashMap<String, Object> hashMap = new HashMap<>();

				hashMap.put("imagehistory", pesanan_models.get(position).getGambarbarang());
				hashMap.put("namabarang", pesanan_models.get(position).getNamabarang());
				hashMap.put("namapenyewa", pesanan_models.get(position).getNamapenyewa());
				hashMap.put("alamat", pesanan_models.get(position).getAlamatpenyewa());
				hashMap.put("idpenyewa", firebaseUser.getUid());
				hashMap.put("jumlahsewa", pesanan_models.get(position).getTotalstok());
				hashMap.put("totalharga", pesanan_models.get(position).getTotalharga());

				refe.child("History").child("User").child(firebaseUser.getUid()).push().setValue(hashMap)
						.addOnSuccessListener(new OnSuccessListener<Void>() {
							@Override
							public void onSuccess(Void unused) {
								Toast.makeText(context,"Pesanan Telah diterima",Toast.LENGTH_SHORT).show();
								refe.child("History").child("Admin").push().setValue(hashMap);
								refe.child("Penyewaan").child(keynya).removeValue();
							}
						});

			}
		});

		holder.Delete.setOnClickListener(new View.OnClickListener() {
			  @Override
			  public void onClick(View view) {
				  final Dialog dialog = new Dialog(context);

				  dialog.setContentView(R.layout.allertdelete);

				  dialog.setCanceledOnTouchOutside(false);
				  dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
						  WindowManager.LayoutParams.WRAP_CONTENT);

				  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

				  dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
				  Button btnbtal = dialog.findViewById(R.id.btnbtaldelete);
				  Button btnlanjut = dialog.findViewById(R.id.btnokdelete);

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
		return pesanan_models.size();
	}

	public class MyViewHolder extends RecyclerView.ViewHolder{

		ImageView gambarbarang,Delete;
		TextView Namabarang,TotalHarga,NamaPenyewa,alamatPenyewa,noHp,statuspesanan,totpes,tgglPeminjaman,rentang_sewa;
		Button terima;
		public MyViewHolder(@NonNull @NotNull View itemView) {
			super(itemView);

			gambarbarang=itemView.findViewById(R.id.gambarpesanan);
			Delete=itemView.findViewById(R.id.batalkanpesanan);
			terima=itemView.findViewById(R.id.terima);

			tgglPeminjaman=itemView.findViewById(R.id.tgglPeminjaman);
			rentang_sewa=itemView.findViewById(R.id.rentang_sewa);

			Namabarang=itemView.findViewById(R.id.nambar_pesanan);
			TotalHarga=itemView.findViewById(R.id.totalhargapesanan);
			NamaPenyewa=itemView.findViewById(R.id.namapenyewa);
			alamatPenyewa=itemView.findViewById(R.id.alamatPenyewa);
			noHp=itemView.findViewById(R.id.nohpenyewa);
			statuspesanan=itemView.findViewById(R.id.statuspesanan);
			totpes=itemView.findViewById(R.id.totstokpes);
		}
	}
}
