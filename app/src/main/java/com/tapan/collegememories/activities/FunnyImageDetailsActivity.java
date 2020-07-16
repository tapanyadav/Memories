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

import com.tapan.collegememories.Adapters.FunnyImageDetailsAdapter;
import com.tapan.collegememories.Models.FunnyImagesModel;
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


public class FunnyImageDetailsActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerViewFunnyDetails;
    FunnyImageDetailsAdapter funnyImageDetailsAdapter;
    FloatingActionButton floatingActionButton;
    int PICK_IMAGES = 1;
    private Uri getImageUri;
    String userId;
    private StorageReference storageReference;
    ProgressDialog progressDialog;
    String currentUserName;
    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funny_image_details);

        Toolbar toolbar = findViewById(R.id.funny_details_toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        currentUserName = getIntent().getStringExtra("loginUserName");
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

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
        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        recyclerViewFunnyDetails = findViewById(R.id.recyclerViewFunnyDetail);
        floatingActionButton = findViewById(R.id.fabFunny);

        userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

        Query query = firebaseFirestore.collection("FunnyImages").orderBy("dateTime", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<FunnyImagesModel> zulfisterModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<FunnyImagesModel>()
                .setQuery(query,FunnyImagesModel.class).build();


        funnyImageDetailsAdapter = new FunnyImageDetailsAdapter(zulfisterModelFirestoreRecyclerOptions);
        recyclerViewFunnyDetails.setHasFixedSize(true);
        recyclerViewFunnyDetails.setLayoutManager(new GridLayoutManager(this,2));
        recyclerViewFunnyDetails.setAdapter(funnyImageDetailsAdapter);
        funnyImageDetailsAdapter.notifyDataSetChanged();

        funnyImageDetailsAdapter.setOnListItemClick((snapshot, position) -> {
            String link = (String) snapshot.get("funnyImage");
            Intent intent = new Intent(this, ImageDetailsActivity.class);
          intent.putExtra("fullImage",link);
           startActivity(intent);
        });
        floatingActionButton.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (ContextCompat.checkSelfPermission(FunnyImageDetailsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(FunnyImageDetailsActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(FunnyImageDetailsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

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

        }
    }

    private void uploadImage(){
        UploadTask image_path = storageReference.child("FunnyImages/").child(userId + "_"+currentUserName+ "_" + UUID.randomUUID() +  ".jpg").putFile(getImageUri);

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
        totalSixPhoto.put("funnyImage", image);
        totalSixPhoto.put("dateTime", FieldValue.serverTimestamp());


        firebaseFirestore.collection("FunnyImages").add(totalSixPhoto).addOnCompleteListener(task1 -> {


            if (task1.isSuccessful()) {
                progressDialog.dismiss();
                Toast.makeText(FunnyImageDetailsActivity.this, "Please wait.. Your network speed is slow", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(AddPhotoActivity.this,UserProfileActivity.class);
//                startActivity(intent);
//                finish();

            } else {
                progressDialog.dismiss();
                String error = Objects.requireNonNull(task1.getException()).getMessage();
                Toast.makeText(FunnyImageDetailsActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();

            }

        });
    }


    private void showProgress() {
        progressDialog = new ProgressDialog(FunnyImageDetailsActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        funnyImageDetailsAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        funnyImageDetailsAdapter.stopListening();
    }


}