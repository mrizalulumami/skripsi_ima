package com.project.penyewaanalatpesta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.project.penyewaanalatpesta.model.User_model;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profil_User extends AppCompatActivity {

	DatabaseReference ref,reference;

	FirebaseUser firebaseUser;

	EditText nama,alamat,email;

	Button simpan;

	CircleImageView profilPhoto;

	StorageReference storageReference;

	//Kode permintaan untuk memilih metode pengambilan gamabr
	private static final int REQUEST_CODE_CAMERA = 1;
	private static final int REQUEST_CODE_GALLERY = 2;
	private Uri uri;
	StorageTask uploadTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profil_user);



		nama=findViewById(R.id.txtNama);
		alamat=findViewById(R.id.txtAlamat);
		email=findViewById(R.id.txtemail);

		simpan=findViewById(R.id.pSimpan);

		profilPhoto=findViewById(R.id.pFoto);

		storageReference = FirebaseStorage.getInstance().getReference("Users");

		profilPhoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getImage();
			}
		});
		simpan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				uploadImage1();
			}
		});

		firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

		ref= FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid().toString());
		ref.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				User_model user=snapshot.getValue(User_model.class);
				String nama1=user.getFullname();
				String alamatuser1=user.getAddress();
				String email1=user.getEmail();
				nama.setText(nama1);
				alamat.setText(alamatuser1);
				email.setText(email1);
				if (user.getPhotoprofil().equals("default")){
					profilPhoto.setImageResource(R.drawable.settings_ic);
				}else{
					Glide.with(getApplicationContext())
							.load(user.getPhotoprofil()).fitCenter()
							.into(profilPhoto);
				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});
	}

	private void getImage(){
		CharSequence[] menu = {"Kamera", "Galeri"};
		AlertDialog.Builder dialog = new AlertDialog.Builder(this)
				.setTitle("Upload Image")
				.setItems(menu, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which){
							case 0:
								//Mengambil gambar dari Kemara ponsel
								Intent imageIntentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
								startActivityForResult(imageIntentCamera, REQUEST_CODE_CAMERA);
								break;

							case 1:
								//Mengambil gambar dari galeri
								Intent imageIntentGallery = new Intent(Intent.ACTION_PICK,
										MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
								startActivityForResult(imageIntentGallery, REQUEST_CODE_GALLERY);
								break;
						}
					}
				});
		dialog.create();
		dialog.show();
	}
	private String getFileExtension(Uri uri){
		ContentResolver contentResolver=getApplicationContext().getContentResolver();
		MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
		return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
	}

	private void uploadImage1(){
		final ProgressDialog pd= new ProgressDialog(this);
		pd.setMessage("uploading");
		pd.show();

		if (uri != null){
			final StorageReference fileRefrence=storageReference.child(System.currentTimeMillis()
					+"."+getFileExtension(uri));

			uploadTask=fileRefrence.putFile(uri);
			uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
				@Override
				public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
					if (!task.isSuccessful()){
						throw task.getException();
					}
					return fileRefrence.getDownloadUrl();
				}
			}).addOnCompleteListener(new OnCompleteListener<Uri>() {
				@Override
				public void onComplete(@NonNull Task<Uri> task) {
					if (task.isSuccessful()){
						reference= FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

						//upload image

						Uri downloaduri=task.getResult();
						String mUri=downloaduri.toString();

						HashMap<String,Object> hashMap=new HashMap<>();
						hashMap.put("photoprofil",mUri);
						hashMap.put("fullname",nama.getText().toString());
						hashMap.put("address",alamat.getText().toString());
						hashMap.put("email",email.getText().toString());
						reference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
							@Override
							public void onComplete(@NonNull Task<Void> task) {
								if(task.isSuccessful()){
									Toast.makeText(getApplicationContext(), "Data berhasil ditambah berhasil di update!", Toast.LENGTH_SHORT).show();
								}
							}
						});

						pd.dismiss();
					}else{
						Toast.makeText(getApplicationContext(), "berhasil!", Toast.LENGTH_SHORT).show();
						pd.dismiss();
					}
				}
			}).addOnFailureListener(new OnFailureListener() {
				@Override
				public void onFailure(@NonNull Exception e) {
					Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
					pd.dismiss();
				}
			});

		}else{
			Toast.makeText(getApplicationContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
		}



	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//Menghandle hasil data yang diambil dari kamera atau galeri untuk ditampilkan pada ImageView
		switch(requestCode){
			case REQUEST_CODE_CAMERA:
				if(resultCode == RESULT_OK){
					profilPhoto.setVisibility(View.VISIBLE);
					Bitmap bitmap = (Bitmap) data.getExtras().get("data");
					profilPhoto.setImageBitmap(bitmap);
				}
				break;

			case REQUEST_CODE_GALLERY:
				if(resultCode == RESULT_OK){
					profilPhoto.setVisibility(View.VISIBLE);
					uri = data.getData();
					profilPhoto.setImageURI(uri);
				}
				break;
		}
	}
}