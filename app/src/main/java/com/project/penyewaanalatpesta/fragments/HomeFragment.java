package com.project.penyewaanalatpesta.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.penyewaanalatpesta.R;
import com.project.penyewaanalatpesta.SpaceItemDecoration;
import com.project.penyewaanalatpesta.adapter.Adapter_Barang;
import com.project.penyewaanalatpesta.adapter.Adapter_Pesanan;
import com.project.penyewaanalatpesta.model.SliderModel;
import com.project.penyewaanalatpesta.model.User_model;
import com.project.penyewaanalatpesta.model.modelbarang.Barang_model;
import com.project.penyewaanalatpesta.model.pesananmodel.Pesanan_model;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {




	CarouselView carouselView;

	int[] sampleImages = {R.drawable.bgslider2, R.drawable.bgslider2};

	FirebaseUser firebaseUser;
	DatabaseReference reference,ref;

	ArrayList<Barang_model> list2;
	Adapter_Barang adapter2;
	private RecyclerView mRDagang;
	private GridLayoutManager MDManager;
	TextView namauser;
	CircleImageView gambaruser;

	SearchView searchView;





	public HomeFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view =inflater.inflate(R.layout.fragment_home, container,false);

		namauser = view.findViewById(R.id.namauser);
		gambaruser = view.findViewById(R.id.imageuser);
		carouselView= view.findViewById(R.id.carouselView);



		carouselView.setImageListener(imageListener);

		firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

		ref= FirebaseDatabase.getInstance().getReference("Barangs");

		reference= FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
		reference.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				User_model model=snapshot.getValue(User_model.class);
				namauser.setText(model.getFullname());
				if (model.getPhotoprofil().equals("default")){
					gambaruser.setImageResource(R.drawable.settings_ic);
				}else {
					Glide.with(getContext())
							.load(model.getPhotoprofil()).fitCenter()
							.into(gambaruser);
				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});

		//Recycleview Dagang
		mRDagang = view.findViewById(R.id.rv_home);
		searchView = view.findViewById(R.id.searchView);
		mRDagang.setHasFixedSize(true);

		MDManager = new GridLayoutManager(getContext(),2);
		mRDagang.addItemDecoration(new SpaceItemDecoration(16));
		MDManager.setReverseLayout(false);
		mRDagang.setLayoutManager(MDManager);



		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		list2 = new ArrayList<>();
		if (ref != null){
			ref.addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {
					if (snapshot.exists()){
						list2.clear();
						for (DataSnapshot ds:snapshot.getChildren()){
							Barang_model barang_model=ds.getValue(Barang_model.class);
							barang_model.setKey(ds.getKey());
							list2.add(barang_model);

						}
						adapter2=new Adapter_Barang(getContext(),list2);
						mRDagang.setAdapter(adapter2);
					}

				}

				@Override
				public void onCancelled(@NonNull DatabaseError error) {

				}
			});
		}
		if (searchView != null){
			searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
				@Override
				public boolean onQueryTextSubmit(String query) {
					return false;
				}

				@Override
				public boolean onQueryTextChange(String s) {
					search(s);
					return true;
				}
			});
		}
	}
	public void search(String str){
		ArrayList<Barang_model> mylist=new ArrayList<>();
		for (Barang_model object:list2){
			object.setKey(object.getKey());
			if (object.getNama_barang().toLowerCase().contains(str.toLowerCase())){
				mylist.add(object);
			}
			adapter2=new Adapter_Barang(getContext(),mylist);
			mRDagang.setAdapter(adapter2);
		}
	}
	ImageListener imageListener = new ImageListener() {
		@Override
		public void setImageForPosition(int position, ImageView imageView) {
			imageView.setImageResource(sampleImages[position]);
		}
	};

}