package com.project.penyewaanalatpesta.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.penyewaanalatpesta.R;
import com.project.penyewaanalatpesta.adapter.Adapter_Barang;
import com.project.penyewaanalatpesta.adapter.Adapter_Pesanan;
import com.project.penyewaanalatpesta.model.modelbarang.Barang_model;
import com.project.penyewaanalatpesta.model.pesananmodel.Pesanan_model;

import java.util.ArrayList;

public class PesananFragment extends Fragment {

	FirebaseUser firebaseUser;
	DatabaseReference reference,ref,refdagang;

	ArrayList<Pesanan_model> list2;
	Adapter_Pesanan adapter2;
	private RecyclerView mRDagang;
	private LinearLayoutManager MDManager;

	SearchView searchView;


	public PesananFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_pesanan, container, false);



		//Recycleview Dagang
		mRDagang = view.findViewById(R.id.rv_pesanan);
		searchView = view.findViewById(R.id.searchView2);
		mRDagang.setHasFixedSize(true);

		MDManager = new LinearLayoutManager(getContext());
		MDManager.setReverseLayout(true);
		MDManager.setStackFromEnd(true);
		mRDagang.setLayoutManager(MDManager);

		firebaseUser= FirebaseAuth.getInstance().getCurrentUser();


		//AmbilData();

		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		list2 = new ArrayList<>();
		ref= FirebaseDatabase.getInstance().getReference("Penyewaan");
		if (ref != null){
			ref.addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {
					if (snapshot.exists()){
						list2.clear();
						for (DataSnapshot ds:snapshot.getChildren()){
							Pesanan_model pesanan_model=ds.getValue(Pesanan_model.class);
							pesanan_model.setKey(ds.getKey());
							if (pesanan_model.getIdpenyewa().equals(firebaseUser.getUid()) && pesanan_model.getJenis().equals("pesanan")){
								list2.add(pesanan_model);
							}

						}
						adapter2=new Adapter_Pesanan(getContext(),list2);
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
		ArrayList<Pesanan_model> mylist=new ArrayList<>();
		for (Pesanan_model object:list2){
			object.setKey(object.getKey());
			if (object.getNamabarang().toLowerCase().contains(str.toLowerCase())){
				if (object.getIdpenyewa().equals(firebaseUser.getUid()) && object.getJenis().equals("pesanan")) {
					mylist.add(object);
				}
			}
			adapter2=new Adapter_Pesanan(getContext(),mylist);
			mRDagang.setAdapter(adapter2);
		}
	}
}