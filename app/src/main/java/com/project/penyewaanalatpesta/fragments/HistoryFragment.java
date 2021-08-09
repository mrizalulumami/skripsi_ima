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
import com.project.penyewaanalatpesta.adapter.Adapter_History;
import com.project.penyewaanalatpesta.model.History_model;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {


	FirebaseUser firebaseUser;
	DatabaseReference reference,ref,refdagang;

	ArrayList<History_model> list2;
	Adapter_History adapter2;
	private RecyclerView mRDagang;
	private LinearLayoutManager MDManager;

	SearchView searchView;




	public HistoryFragment() {
		// Required empty public constructor
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view =inflater.inflate(R.layout.fragment_history, container, false);
		//Recycleview Dagang
		mRDagang = view.findViewById(R.id.rv_history);
		searchView = view.findViewById(R.id.searchView4);
		mRDagang.setHasFixedSize(true);

		MDManager = new LinearLayoutManager(getContext());
		MDManager.setReverseLayout(true);
		MDManager.setStackFromEnd(true);
		mRDagang.setLayoutManager(MDManager);

		firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		list2 = new ArrayList<>();
		ref= FirebaseDatabase.getInstance().getReference("History").child("User").child(firebaseUser.getUid());
		if (ref != null){
			ref.addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {
					if (snapshot.exists()){
						list2.clear();
						for (DataSnapshot ds:snapshot.getChildren()){
							History_model keranjang_model=ds.getValue(History_model.class);
							keranjang_model.setKey(ds.getKey());
							if (keranjang_model.getIdpenyewa().equals(firebaseUser.getUid())){
								list2.add(keranjang_model);
							}

						}
						adapter2=new Adapter_History(getContext(),list2);
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
		ArrayList<History_model> mylist=new ArrayList<>();
		for (History_model object:list2){
			object.setKey(object.getKey());
			if (object.getNamabarang().toLowerCase().contains(str.toLowerCase())){
				if (object.getIdpenyewa().equals(firebaseUser.getUid())) {
					mylist.add(object);
				}
			}
			adapter2=new Adapter_History(getContext(),mylist);
			mRDagang.setAdapter(adapter2);
		}
	}
}