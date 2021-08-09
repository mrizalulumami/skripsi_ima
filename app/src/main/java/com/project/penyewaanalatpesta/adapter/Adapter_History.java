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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.penyewaanalatpesta.R;
import com.project.penyewaanalatpesta.model.History_model;

import java.util.ArrayList;

public class Adapter_History extends RecyclerView.Adapter<Adapter_History.MyViewHolder> {

	Context context;
	ArrayList<History_model> listBarang;


	FirebaseUser firebaseUser;


	public Adapter_History(Context context, ArrayList<History_model> listBarang) {
		this.context = context;
		this.listBarang = listBarang;


		firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history,parent, false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

		String imagehistory,namabarang,namapenyewa,alamat,idpenyewa,jumlahsewa,totalharga;
		holder.namabarangView.setText(listBarang.get(position).getNamabarang());
		holder.namaprdkView.setText(listBarang.get(position).getNamapenyewa());
		holder.merek.setText(listBarang.get(position).getAlamat());
		holder.sttskeranjang.setText(listBarang.get(position).getIdpenyewa());
		holder.jumlahbeli.setText(listBarang.get(position).getJumlahsewa());
		holder.totalhargaview.setText(listBarang.get(position).getTotalharga());


		if (listBarang.get(position).getImagehistory().equals("default") || listBarang.get(position).getImagehistory().equals(null) || listBarang.get(position).getImagehistory().equals("")) {
			holder.foto.setImageResource(R.drawable.camera_ic);
		} else {
			Glide.with(context)
					.load(listBarang.get(position).getImagehistory()).fitCenter()
					.into(holder.foto);
		}


		String keynya=listBarang.get(position).getKey();


		holder.delete.setOnClickListener(new View.OnClickListener() {
				 @Override
				 public void onClick(View view) {
					 final Dialog dialog = new Dialog(context);

					 dialog.setContentView(R.layout.allertdeletehistory);

					 dialog.setCanceledOnTouchOutside(false);
					 dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
							 WindowManager.LayoutParams.WRAP_CONTENT);

					 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

					 dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
					 Button btnbtal = dialog.findViewById(R.id.btnbtlhistory);
					 Button btnlanjut = dialog.findViewById(R.id.btnlnlnjthistory);

					 btnbtal.setOnClickListener(new View.OnClickListener() {
						 @Override
						 public void onClick(View v) {
							 dialog.cancel();
						 }
					 });
					 btnlanjut.setOnClickListener(new View.OnClickListener() {
						 @Override
						 public void onClick(View v) {
							 DatabaseReference refe = FirebaseDatabase.getInstance().getReference();
							 refe.child("History").child("User").child(firebaseUser.getUid()).child(keynya).removeValue()
									 .addOnSuccessListener(new OnSuccessListener<Void>() {
										 @Override
										 public void onSuccess(Void aVoid) {
											 Toast.makeText(context, "dihapus dari history!", Toast.LENGTH_LONG).show();
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
		return listBarang.size();
	}

	public class MyViewHolder extends RecyclerView.ViewHolder {

		TextView namabarangView, namaprdkView,merek, totalhargaview,sttskeranjang,jumlahbeli;
		ImageView foto;
		ImageButton delete;




		public MyViewHolder(@NonNull View itemView) {
			super(itemView);

			namabarangView = itemView.findViewById(R.id.nambarhis);
			namaprdkView = itemView.findViewById(R.id.namperhis);
			merek = itemView.findViewById(R.id.merekhis);
			jumlahbeli = itemView.findViewById(R.id.jumbelhis);

			totalhargaview=itemView.findViewById(R.id.tothis);
			sttskeranjang=itemView.findViewById(R.id.sttshis);

			foto = itemView.findViewById(R.id.imagehistor);
			delete = itemView.findViewById(R.id.deletehis);



		}
	}
}
