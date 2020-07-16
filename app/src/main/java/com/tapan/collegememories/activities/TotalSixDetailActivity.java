package com.tapan.collegememories.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tapan.collegememories.Adapters.TotalSixDetailsAdapter;
import com.tapan.collegememories.Models.TotalSixModel;
import com.tapan.collegememories.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class TotalSixDetailActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerViewTotalSix;
    TotalSixDetailsAdapter totalSixDetailsAdapter;
    FloatingActionButton floatingActionButton;
    int PICK_IMAGES = 1;
    private Uri getImageUri;
    String userId,currentUserName;
    private StorageReference storageReference;
    ProgressDialog progressDialog;
    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_six_detail);

        Toolbar toolbar = findViewById(R.id.total_six_details_toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        currentUserName = getIntent().getStringExtra("loginUserName");
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        recyclerViewTotalSix = findViewById(R.id.recyclerViewTotalSixDetail);
        floatingActionButton = findViewById(R.id.fabTotal);

        userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

        int currentNightMode = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Night mode is not active, we're using the light theme
                toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black));
                toolbar.setNavigationIcon(R.drawable.icon_back_new);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active, we're using dark theme
                toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
                toolbar.setNavigationIcon(R.drawable.icon_back_white);
                break;
        }
        Query query = firebaseFirestore.collection("TotalSix").orderBy("dateTime", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<TotalSixModel> zulfisterModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<TotalSixModel>()
                .setQuery(query,TotalSixModel.class).build();


        totalSixDetailsAdapter = new TotalSixDetailsAdapter(zulfisterModelFirestoreRecyclerOptions);
        recyclerViewTotalSix.setHasFixedSize(true);
        recyclerViewTotalSix.setLayoutManager(new GridLayoutManager(this,2));
        recyclerViewTotalSix.setAdapter(totalSixDetailsAdapter);
        totalSixDetailsAdapter.notifyDataSetChanged();

        totalSixDetailsAdapter.setOnListItemClick((snapshot, position) -> {
            String link = (String) snapshot.get("totalSixImage");
            //Toast.makeText(this,  link, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ImageDetailsActivity.class);
//            Toast.makeText(MainActivity.this, "Name: "+ snapshot.getData().get("peopleName"), Toast.LENGTH_SHORT).show();
            intent.putExtra("fullImage",link);
            //intent.putExtra("peopleName", (Serializable) snapshot.getData().get("peopleName"));
            startActivity(intent);
        });
        floatingActionButton.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (ContextCompat.checkSelfPermission(TotalSixDetailActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(TotalSixDetailActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(TotalSixDetailActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                } else {

                    getPickImageIntentPhoto();

                }

            } else {

                getPickImageIntentPhoto();

            }
        });
    }

    private void getPickImageIntentPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGES);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGES) {
                showProgress();
                if (data.getData() != null) {
                    getImageUri = data.getData();
                    uploadImage();
                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();

                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            getImageUri = item.getUri();
                            mArrayUri.add(getImageUri);

                            uploadImage();
                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
            }

//            if (getImageUri != null) {
//                showProgress();
//
//                for (int uploadCount = 0;uploadCount<mArrayUri.size();uploadCount++){
//                     UploadTask image_path = storageReference.child("ZulfistersImage/").child(userId + " (" + UUID.randomUUID() + " )" + ".jpg").putFile(getImageUri);
//
//                image_path.addOnSuccessListener(taskSnapshot -> {
//
//                    Task<Uri> task = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl();
//                    task.addOnSuccessListener(uri -> {
//
//                        String file = uri.toString();
//                        addPhotoData(file);
//                    });
//
//                });//TODO add onFailure listener
//            }
//                } else {
//                Toast.makeText(this, "Please add one photo!", Toast.LENGTH_SHORT).show();
//            }


        }
    }

    private void uploadImage(){
        UploadTask image_path = storageReference.child("TotalSixImage/").child(userId + "_"+currentUserName+ "_" + UUID.randomUUID()  + ".jpg").putFile(getImageUri);

        image_path.addOnSuccessListener(taskSnapshot -> {

            Task<Uri> task = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl();
            task.addOnSuccessListener(uri -> {

                String file = uri.toString();
                addPhotoData(file);
            });

        });
    }

    private void addPhotoData(String image) {

        HashMap<String, Object> totalSixPhoto = new HashMap<>();
        totalSixPhoto.put("totalSixImage", image);
        totalSixPhoto.put("dateTime", FieldValue.serverTimestamp());


        firebaseFirestore.collection("TotalSix").add(totalSixPhoto).addOnCompleteListener(task1 -> {


            if (task1.isSuccessful()) {
                progressDialog.dismiss();
                Toast.makeText(TotalSixDetailActivity.this, "Please wait.. Your network speed is slow", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(AddPhotoActivity.this,UserProfileActivity.class);
//                startActivity(intent);
//                finish();

            } else {
                progressDialog.dismiss();
                String error = Objects.requireNonNull(task1.getException()).getMessage();
                Toast.makeText(TotalSixDetailActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();

            }

        });
    }


    private void showProgress() {
        progressDialog = new ProgressDialog(TotalSixDetailActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        totalSixDetailsAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        totalSixDetailsAdapter.stopListening();
    }

}