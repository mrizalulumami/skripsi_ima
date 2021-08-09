package com.project.penyewaanalatpesta.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.penyewaanalatpesta.Detail_barang_Activity;
import com.project.penyewaanalatpesta.R;
import com.project.penyewaanalatpesta.model.modelbarang.Barang_model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Adapter_Barang extends RecyclerView.Adapter<Adapter_Barang.MyViewHolder> {

	Context context;
	ArrayList<Barang_model> barang_models;

	public Adapter_Barang() {
	}

	public Adapter_Barang(Context context, ArrayList<Barang_model> barang_models) {
		this.context = context;
		this.barang_models = barang_models;
	}

	@NotNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

		View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_barang,parent,false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder,final int position) {

		holder.namaBarang.setText(barang_models.get(position).getNama_barang());
		holder.stok.setText(barang_models.get(position).getStok());
		holder.harga.setText(barang_models.get(position).getHarga());
		holder.satuan.setText(barang_models.get(position).getJenis_barang());
		holder.jenis_brg.setText(barang_models.get(position).getJenis_barang());

		if (barang_models.get(position).getGambar_barang().equals("default") || barang_models.get(position).getGambar_barang().equals(null) || barang_models.get(position).getGambar_barang().equals("")) {
			holder.gambarBarang.setImageResource(R.drawable.camera_ic);
		} else {
			Glide.with(context)
					.load(barang_models.get(position).getGambar_barang()).fitCenter()
					.into(holder.gambarBarang);
		}

		String keynya = barang_models.get(position).getKey();

		holder.beli.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent detailEdit = new Intent(context.getApplicationContext(), Detail_barang_Activity.class);
				detailEdit.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				detailEdit.putExtra("KEY", keynya);
				detailEdit.putExtra("NAMA_BARANG", barang_models.get(position).getNama_barang());
				detailEdit.putExtra("HARGA_BARANG", barang_models.get(position).getHarga());
				detailEdit.putExtra("STOK_BARANG", barang_models.get(position).getStok());
				detailEdit.putExtra("GAMBAR_BARANG", barang_models.get(position).getGambar_barang());
				detailEdit.putExtra("JENIS_BARANG", barang_models.get(position).getJenis_barang());
				detailEdit.putExtra("ID_PENJUAL", barang_models.get(position).getIdpenjual());
				context.startActivity(detailEdit);
			}
		});

	}

	@Override
	public int getItemCount() {
		return barang_models.size();
	}

	public class MyViewHolder extends RecyclerView.ViewHolder{

		TextView namaBarang, stok, harga,jenis_brg,satuan;
		ImageView gambarBarang,beli;

		public MyViewHolder(@NonNull @NotNull View itemView) {
			super(itemView);

			namaBarang = itemView.findViewById(R.id.nambar);
			stok = itemView.findViewById(R.id.stok_brg);
			harga = itemView.findViewById(R.id.harga_brg);
			jenis_brg = itemView.findViewById(R.id.jenis_brgview);
			satuan = itemView.findViewById(R.id.satuan);

			gambarBarang = itemView.findViewById(R.id.gamBar);
			beli = itemView.findViewById(R.id.beli_brg);


		}
	}

}
