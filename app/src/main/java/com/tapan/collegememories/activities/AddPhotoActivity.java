package com.tapan.collegememories.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tapan.collegememories.R;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class AddPhotoActivity extends AppCompatActivity {

    CardView cardViewAddPhoto,cardViewAddPhotoImage;
    ImageView imageViewAddPhotoImage;
    FirebaseFirestore firebaseFirestore;
    int PICK_IMAGES = 1;
    private Uri getImageUri;
    private Chip  chipLike2;
    ChipGroup chipGroupNotLike;
    String userId;
    Button buttonSubmitPhoto;
    private StorageReference storageReference;
    ProgressDialog progressDialog;
    String captionData;
    EditText editTextCaption;
    String currentUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        cardViewAddPhoto=findViewById(R.id.cardAddPhoto);
        buttonSubmitPhoto = findViewById(R.id.btn_submit_photo);
        editTextCaption = findViewById(R.id.editCaption);
        cardViewAddPhotoImage = findViewById(R.id.cardImage);
        imageViewAddPhotoImage = findViewById(R.id.addPhotoImage);
        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();


        Toolbar toolbar = findViewById(R.id.add_photo_toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        int currentNightMode = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {

            case Configuration.UI_MODE_NIGHT_NO:
                toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black));
                toolbar.setNavigationIcon(R.drawable.icon_back_new);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
                toolbar.setNavigationIcon(R.drawable.icon_back_white);
                break;
        }

        chipWork();
        currentUserName = getIntent().getStringExtra("nameUser");
        firebaseFirestore.collection("Users");
        cardViewAddPhoto.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (ContextCompat.checkSelfPermission(AddPhotoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(AddPhotoActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(AddPhotoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                } else {

                    getPickImageIntentPhoto();

                }

            } else {

                getPickImageIntentPhoto();

            }
        });
        buttonSubmitPhoto.setOnClickListener(v -> {
            showProgress();

            captionData = editTextCaption.getText().toString().trim();

            if (getImageUri!=null && !captionData.equals("")){
                uploadImage(captionData);

            }else {
                progressDialog.dismiss();
                if (getImageUri==null){
                    Toast.makeText(this, "Please add one photo!", Toast.LENGTH_SHORT).show();
                }if (captionData.equals("")){
                    Toast.makeText(this, "Please add caption for this photo!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getPickImageIntentPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGES);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGES) {

                if (data.getData() != null) {
                    getImageUri = data.getData();
                    imageViewAddPhotoImage.setImageURI(getImageUri);
                    cardViewAddPhotoImage.setVisibility(View.VISIBLE);
                    editTextCaption.setVisibility(View.VISIBLE);
                }
            }

        }
    }

    private void uploadImage(String caption){


        UploadTask image_path = storageReference.child("Photos/").child(userId +"_"+currentUserName+ "_" + UUID.randomUUID()  + ".jpg").putFile(getImageUri);

        image_path.addOnSuccessListener(taskSnapshot -> {

            Task<Uri> task = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl();
            task.addOnSuccessListener(uri -> {

                String file = uri.toString();
                addPhotoData(file,caption);
            });

        });
    }

    private void addPhotoData(String image,String photoCaption) {

        HashMap<String, Object> photoData = new HashMap<>();
        photoData.put("photoImage", image);
        photoData.put("dateTime", FieldValue.serverTimestamp());
        photoData.put("photoCaption",photoCaption);
        if (chipLike2.isChecked()){
            photoData.put("photoStatus","Loved");
        }



        firebaseFirestore.collection("Users").document(userId).collection("Photos").add(photoData).addOnCompleteListener(task1 -> {



            if (task1.isSuccessful()) {
                progressDialog.dismiss();
                Toast.makeText(AddPhotoActivity.this, "Hurray! Photo is added to your profile.", Toast.LENGTH_LONG).show();
                finish();

            } else {
                progressDialog.dismiss();
                String error = Objects.requireNonNull(task1.getException()).getMessage();
                Toast.makeText(AddPhotoActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();

            }

        });
    }


    private void chipWork() {
        chipGroupNotLike = findViewById(R.id.chip_group_not);

        chipLike2 = findViewById(R.id.chipLoved);

        chipLike2.setOnClickListener(v -> chipListener(chipLike2));
        chipLike2.setOnCloseIconClickListener(v -> chipCloseListener(chipLike2));

    }

    void chipListener(Chip chipSelected) {
        chipSelected.setCloseIconVisible(true);
        chipSelected.setTextColor(getResources().getColor(R.color.white));
        chipSelected.setChecked(true);
        chipSelected.setCloseIconTint(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)));
        chipSelected.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorPrimary)));


    }

    void chipCloseListener(Chip chipClose) {
        chipClose.setCloseIconVisible(false);
        chipClose.setChecked(false);
        chipClose.setTextColor(getResources().getColor(R.color.colorPrimary));
        chipClose.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)));
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(AddPhotoActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }
}