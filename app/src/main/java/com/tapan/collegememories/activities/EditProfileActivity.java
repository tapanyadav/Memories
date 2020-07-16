package com.tapan.collegememories.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tapan.collegememories.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import id.zelory.compressor.Compressor;

public class EditProfileActivity extends AppCompatActivity {

    Button buttonSave;
    EditText editTextBio,editTextDob;
    ImageView imageViewProfileEdit;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    private Bitmap compressedImageFile;
    private Uri mainImageURI;
    private boolean isChanged = false;
    ProgressDialog progressDialog;
    String user_id;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        buttonSave = findViewById(R.id.btn_save_changes);
        editTextBio = findViewById(R.id.edit_bio);
        editTextDob = findViewById(R.id.edit_dob);
        imageViewProfileEdit = findViewById(R.id.userProfileEditImage);

        coordinatorLayout = findViewById(R.id.cordEditLayout);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        Toolbar toolbar = findViewById(R.id.edit_prof_toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        coordinatorLayout.setVisibility(View.GONE);
        showProgress();
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


        Glide.with(this).load(MainActivity.profile_activity_data.get("image")).placeholder(R.drawable.loading_new).into(imageViewProfileEdit);



            if (MainActivity.profile_activity_data.get("name").equals("Prashant Bhardwaj")) {
                firebaseFirestore.collection("PeoplesDetails").document("1NbrFJef3BnFXXHB1hWs").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        editTextDob.setText(Objects.requireNonNull(task.getResult()).getString("peopleDateOfBirth"));
                        editTextBio.setText(task.getResult().getString("peopleBio"));
                        textChange(task.getResult().getString("peopleBio"), task.getResult().getString("peopleDateOfBirth"));
                        progressDialog.dismiss();
                        coordinatorLayout.setVisibility(View.VISIBLE);
                    }

                });

            }
            if (MainActivity.profile_activity_data.get("name").equals("Tapan Yadav")) {
                firebaseFirestore.collection("PeoplesDetails").document("MWD7aWZWyfly3i9Swid6").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        editTextDob.setText(Objects.requireNonNull(task.getResult()).getString("peopleDateOfBirth"));
                        editTextBio.setText(task.getResult().getString("peopleBio"));
                        textChange(task.getResult().getString("peopleBio"), task.getResult().getString("peopleDateOfBirth"));
                        progressDialog.dismiss();
                        coordinatorLayout.setVisibility(View.VISIBLE);
                    }

                });
            }
            if (MainActivity.profile_activity_data.get("name").equals("Nikhil Yadav")) {
                firebaseFirestore.collection("PeoplesDetails").document("2fsvRV0rLqWqGduAwsaM").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        editTextDob.setText(Objects.requireNonNull(task.getResult()).getString("peopleDateOfBirth"));
                        editTextBio.setText(task.getResult().getString("peopleBio"));
                        textChange(task.getResult().getString("peopleBio"), task.getResult().getString("peopleDateOfBirth"));
                        progressDialog.dismiss();
                        coordinatorLayout.setVisibility(View.VISIBLE);
                    }

                });
            }
            if (MainActivity.profile_activity_data.get("name").equals("Akanksha Gupta")) {
                firebaseFirestore.collection("PeoplesDetails").document("4Q7Ft92vXfZunxb1SLr6").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        editTextDob.setText(Objects.requireNonNull(task.getResult()).getString("peopleDateOfBirth"));
                        editTextBio.setText(task.getResult().getString("peopleBio"));
                        textChange(task.getResult().getString("peopleBio"), task.getResult().getString("peopleDateOfBirth"));
                        progressDialog.dismiss();
                        coordinatorLayout.setVisibility(View.VISIBLE);
                    }

                });
            }
            if (MainActivity.profile_activity_data.get("name").equals("Nikita Sharma")) {
                firebaseFirestore.collection("PeoplesDetails").document("C0bGu6KBtOiWx3rAxdzf").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        editTextDob.setText(Objects.requireNonNull(task.getResult()).getString("peopleDateOfBirth"));
                        editTextBio.setText(task.getResult().getString("peopleBio"));
                        textChange(task.getResult().getString("peopleBio"), task.getResult().getString("peopleDateOfBirth"));
                        progressDialog.dismiss();
                        coordinatorLayout.setVisibility(View.VISIBLE);
                    }

                });
            }
            if (MainActivity.profile_activity_data.get("name").equals("Sonil Rastogi")) {
                firebaseFirestore.collection("PeoplesDetails").document("N1M8IjcoVP780tGHKCkP").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        editTextDob.setText(Objects.requireNonNull(task.getResult()).getString("peopleDateOfBirth"));
                        editTextBio.setText(task.getResult().getString("peopleBio"));
                        textChange(task.getResult().getString("peopleBio"), task.getResult().getString("peopleDateOfBirth"));
                        progressDialog.dismiss();
                        coordinatorLayout.setVisibility(View.VISIBLE);
                    }

                });
            }
            if (MainActivity.profile_activity_data.get("name").equals("Shivam Tyagi")) {
                firebaseFirestore.collection("PeoplesDetails").document("b1AdJs5Cr4i1jzeCk3SR").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        editTextDob.setText(Objects.requireNonNull(task.getResult()).getString("peopleDateOfBirth"));
                        editTextBio.setText(task.getResult().getString("peopleBio"));
                        textChange(task.getResult().getString("peopleBio"), task.getResult().getString("peopleDateOfBirth"));
                        progressDialog.dismiss();
                        coordinatorLayout.setVisibility(View.VISIBLE);
                    }

                });
            }
            if (MainActivity.profile_activity_data.get("name").equals("Akanksha Mishra")) {
                firebaseFirestore.collection("PeoplesDetails").document("gO6djLhnyVJGF2vIXkws").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        editTextDob.setText(Objects.requireNonNull(task.getResult()).getString("peopleDateOfBirth"));
                        editTextBio.setText(task.getResult().getString("peopleBio"));
                        textChange(task.getResult().getString("peopleBio"), task.getResult().getString("peopleDateOfBirth"));
                        progressDialog.dismiss();
                        coordinatorLayout.setVisibility(View.VISIBLE);
                    }

                });
            }
            if (MainActivity.profile_activity_data.get("name").equals("Ujjwal Singh")) {
                firebaseFirestore.collection("PeoplesDetails").document("i9pwzzEJ0Q9mBG2xx6QN").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        editTextDob.setText(Objects.requireNonNull(task.getResult()).getString("peopleDateOfBirth"));
                        editTextBio.setText(task.getResult().getString("peopleBio"));
                        textChange(task.getResult().getString("peopleBio"), task.getResult().getString("peopleDateOfBirth"));
                        progressDialog.dismiss();
                        coordinatorLayout.setVisibility(View.VISIBLE);
                    }

                });
            }
            if (MainActivity.profile_activity_data.get("name").equals("Shivam Gupta")) {
                firebaseFirestore.collection("PeoplesDetails").document("iAgViDxDQyNbgbjcsWsa").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        editTextDob.setText(Objects.requireNonNull(task.getResult()).getString("peopleDateOfBirth"));
                        editTextBio.setText(task.getResult().getString("peopleBio"));
                        textChange(task.getResult().getString("peopleBio"), task.getResult().getString("peopleDateOfBirth"));
                        progressDialog.dismiss();
                        coordinatorLayout.setVisibility(View.VISIBLE);
                    }

                });
            }
            if (MainActivity.profile_activity_data.get("name").equals("Parul Singh")) {
                firebaseFirestore.collection("PeoplesDetails").document("l7yW1g4XuKkM89pCZipd").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        editTextDob.setText(Objects.requireNonNull(task.getResult()).getString("peopleDateOfBirth"));
                        editTextBio.setText(task.getResult().getString("peopleBio"));
                        textChange(task.getResult().getString("peopleBio"), task.getResult().getString("peopleDateOfBirth"));
                        progressDialog.dismiss();
                        coordinatorLayout.setVisibility(View.VISIBLE);
                    }

                });
            }
            if (MainActivity.profile_activity_data.get("name").equals("Utkarsh Gupta")) {
                firebaseFirestore.collection("PeoplesDetails").document("s6R2XNn6TKDJXRq7YqtJ").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        editTextDob.setText(Objects.requireNonNull(task.getResult()).getString("peopleDateOfBirth"));
                        editTextBio.setText(task.getResult().getString("peopleBio"));
                        textChange(task.getResult().getString("peopleBio"), task.getResult().getString("peopleDateOfBirth"));
                        progressDialog.dismiss();
                        coordinatorLayout.setVisibility(View.VISIBLE);
                    }

                });
            }

        buttonSave.setOnClickListener(v -> {
            showProgress();

            final String user_bio = editTextBio.getText().toString();
            final String user_dob = editTextDob.getText().toString();

            if (!TextUtils.isEmpty(user_bio) && !TextUtils.isEmpty(user_dob)) {

                if (isChanged) {

                    user_id = firebaseAuth.getCurrentUser().getUid();

                    File newImageFile = new File(Objects.requireNonNull(mainImageURI.getPath()));
                    try {

                        compressedImageFile = new Compressor(this)
                                .setMaxHeight(125)
                                .setMaxWidth(125)
                                .setQuality(50)
                                .compressToBitmap(newImageFile);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] thumbData = baos.toByteArray();

                    UploadTask image_path = storageReference.child("PeoplesImage/").child(user_id +"_"+ MainActivity.profile_activity_data.get("name")+".jpg").putBytes(thumbData);

                    image_path.addOnSuccessListener(taskSnapshot -> {

                        Task<Uri> task = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl();
                        task.addOnSuccessListener(uri -> {
                            String file = uri.toString();
                            storeFirestore(file, user_bio,user_dob);
                        });

                    });  //TODO add onFailure listener

                } else {

                    storeDetails( user_bio,user_dob);

                }
            }

        });


        imageViewProfileEdit.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (ContextCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(EditProfileActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                } else {

                    BringImagePicker();

                }

            } else {

                BringImagePicker();

            }
        });


    }

    private void storeDetails(String user_bio, String user_dob) {
        Map<String,Object> userDetailsMap = new HashMap<>();

        userDetailsMap.put("peopleDateOfBirth",user_dob);
        userDetailsMap.put("peopleBio",user_bio);



        if (firebaseAuth.getCurrentUser().getUid().equals("ecnj8C3fJnU240KthDggUOoQmWE2")){
            firebaseFirestore.collection("PeoplesDetails").document("MWD7aWZWyfly3i9Swid6").update(userDetailsMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    progressDialog.dismiss();
                    String error = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_SHORT).show();

                }
            });
        }

        if (firebaseAuth.getCurrentUser().getUid().equals("2PgS11EAEPOn60o2u5o3oYKoMjX2")){
            firebaseFirestore.collection("PeoplesDetails").document("1NbrFJef3BnFXXHB1hWs").update(userDetailsMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                progressDialog.dismiss();
                Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_SHORT).show();
                finish();

            } else {
                progressDialog.dismiss();
                String error = Objects.requireNonNull(task.getException()).getMessage();
                Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_SHORT).show();

            }
        });
        }

        if (firebaseAuth.getCurrentUser().getUid().equals("FLisLrVPG3dpmvdcFcL2yf2dg3j1")){
            firebaseFirestore.collection("PeoplesDetails").document("2fsvRV0rLqWqGduAwsaM").update(userDetailsMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    progressDialog.dismiss();
                    String error = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_SHORT).show();

                }
            });
        }

        if (firebaseAuth.getCurrentUser().getUid().equals("D3HhH91OOjWi1Y891cMfbHXbhHk1")){
            firebaseFirestore.collection("PeoplesDetails").document("4Q7Ft92vXfZunxb1SLr6").update(userDetailsMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    progressDialog.dismiss();
                    String error = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_SHORT).show();

                }
            });
        }

        if (firebaseAuth.getCurrentUser().getUid().equals("Ba558lrXC6NkBXndyltmmVqx5TN2")){
            firebaseFirestore.collection("PeoplesDetails").document("C0bGu6KBtOiWx3rAxdzf").update(userDetailsMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    progressDialog.dismiss();
                    String error = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_SHORT).show();

                }
            });
        }

        if (firebaseAuth.getCurrentUser().getUid().equals("BXwbbUN3jRakBIfZbzWMpgeEa0O2")){
            firebaseFirestore.collection("PeoplesDetails").document("N1M8IjcoVP780tGHKCkP").update(userDetailsMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    progressDialog.dismiss();
                    String error = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_SHORT).show();

                }
            });
        }

        if (firebaseAuth.getCurrentUser().getUid().equals("RcjUVqbCoyc0XZtnvn5tT7M10uE2")){
            firebaseFirestore.collection("PeoplesDetails").document("b1AdJs5Cr4i1jzeCk3SR").update(userDetailsMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    progressDialog.dismiss();
                    String error = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_SHORT).show();

                }
            });
        }

        if (firebaseAuth.getCurrentUser().getUid().equals("CXMlLtaH4jSDHeeBE0lh24kv0N93")){
            firebaseFirestore.collection("PeoplesDetails").document("gO6djLhnyVJGF2vIXkws").update(userDetailsMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    progressDialog.dismiss();
                    String error = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_SHORT).show();

                }
            });
        }

        if (firebaseAuth.getCurrentUser().getUid().equals("DQfGxGysfhY2jD3gVcOxFkzGtc93")){
            firebaseFirestore.collection("PeoplesDetails").document("i9pwzzEJ0Q9mBG2xx6QN").update(userDetailsMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    progressDialog.dismiss();
                    String error = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_SHORT).show();

                }
            });
        }

        if (firebaseAuth.getCurrentUser().getUid().equals("bQjHXSg7Rhfr2pCLNudZJ9iPuHh1")){
            firebaseFirestore.collection("PeoplesDetails").document("iAgViDxDQyNbgbjcsWsa").update(userDetailsMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    progressDialog.dismiss();
                    String error = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_SHORT).show();

                }
            });
        }

        if (firebaseAuth.getCurrentUser().getUid().equals("XxSGgvFuuIeuDlcLFHjEPzv99wf2")){
            firebaseFirestore.collection("PeoplesDetails").document("l7yW1g4XuKkM89pCZipd").update(userDetailsMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    progressDialog.dismiss();
                    String error = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_SHORT).show();

                }
            });
        }

        if (firebaseAuth.getCurrentUser().getUid().equals("AFkEytVIQCRLizoiCNZn1KSKiDg2")){
            firebaseFirestore.collection("PeoplesDetails").document("s6R2XNn6TKDJXRq7YqtJ").update(userDetailsMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    progressDialog.dismiss();
                    String error = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

    private void textChange(String bio,String dob){
        editTextBio.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String textName = editTextBio.getText().toString();
                String textDob = editTextDob.getText().toString();
                if (textName.equals(bio) && textDob.equals(dob)) {
                    buttonSave.setEnabled(false);
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                buttonSave.setEnabled(true);
            }
        });
        editTextDob.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String textBio = editTextBio.getText().toString();
                String textName = editTextDob.getText().toString();
                if (textName.equals(dob) && textBio.equals(bio)) {
                    buttonSave.setEnabled(false);
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                buttonSave.setEnabled(true);
            }
        });
    }

    private void storeFirestore(String generatedFilePath, String user_bio,String user_dob) {


        Map<String, Object> userMap = new HashMap<>();
        Map<String,Object> userDetailsMap = new HashMap<>();

        userMap.put("peopleImage", generatedFilePath);
        userDetailsMap.put("peopleImage",generatedFilePath);
        userDetailsMap.put("peopleDateOfBirth",user_dob);
        userDetailsMap.put("peopleBio",user_bio);



        firebaseFirestore.collection("People").document(user_id).update(userMap).addOnCompleteListener(task1 -> {

            if (task1.isSuccessful()) {

                progressDialog.dismiss();
                Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            } else {
                progressDialog.dismiss();
                String error = Objects.requireNonNull(task1.getException()).getMessage();
                Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();

            }

        });

        if (firebaseAuth.getCurrentUser().getUid().equals("ecnj8C3fJnU240KthDggUOoQmWE2")){
            firebaseFirestore.collection("PeoplesDetails").document("MWD7aWZWyfly3i9Swid6").update(userDetailsMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    progressDialog.dismiss();
                    String error = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_SHORT).show();

                }
            });
        }

        if (firebaseAuth.getCurrentUser().getUid().equals("2PgS11EAEPOn60o2u5o3oYKoMjX2")){
            firebaseFirestore.collection("PeoplesDetails").document("1NbrFJef3BnFXXHB1hWs").update(userDetailsMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    progressDialog.dismiss();
                    String error = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_SHORT).show();

                }
            });
        }

        if (firebaseAuth.getCurrentUser().getUid().equals("FLisLrVPG3dpmvdcFcL2yf2dg3j1")){
            firebaseFirestore.collection("PeoplesDetails").document("2fsvRV0rLqWqGduAwsaM").update(userDetailsMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    progressDialog.dismiss();
                    String error = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_SHORT).show();

                }
            });
        }

        if (firebaseAuth.getCurrentUser().getUid().equals("D3HhH91OOjWi1Y891cMfbHXbhHk1")){
            firebaseFirestore.collection("PeoplesDetails").document("4Q7Ft92vXfZunxb1SLr6").update(userDetailsMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    progressDialog.dismiss();
                    String error = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_SHORT).show();

                }
            });
        }

        if (firebaseAuth.getCurrentUser().getUid().equals("Ba558lrXC6NkBXndyltmmVqx5TN2")){
            firebaseFirestore.collection("PeoplesDetails").document("C0bGu6KBtOiWx3rAxdzf").update(userDetailsMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    progressDialog.dismiss();
                    String error = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_SHORT).show();

                }
            });
        }

        if (firebaseAuth.getCurrentUser().getUid().equals("BXwbbUN3jRakBIfZbzWMpgeEa0O2")){
            firebaseFirestore.collection("PeoplesDetails").document("N1M8IjcoVP780tGHKCkP").update(userDetailsMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    progressDialog.dismiss();
                    String error = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_SHORT).show();

                }
            });
        }

        if (firebaseAuth.getCurrentUser().getUid().equals("RcjUVqbCoyc0XZtnvn5tT7M10uE2")){
            firebaseFirestore.collection("PeoplesDetails").document("b1AdJs5Cr4i1jzeCk3SR").update(userDetailsMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    progressDialog.dismiss();
                    String error = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_SHORT).show();

                }
            });
        }

        if (firebaseAuth.getCurrentUser().getUid().equals("CXMlLtaH4jSDHeeBE0lh24kv0N93")){
            firebaseFirestore.collection("PeoplesDetails").document("gO6djLhnyVJGF2vIXkws").update(userDetailsMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    progressDialog.dismiss();
                    String error = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_SHORT).show();

                }
            });
        }

        if (firebaseAuth.getCurrentUser().getUid().equals("DQfGxGysfhY2jD3gVcOxFkzGtc93")){
            firebaseFirestore.collection("PeoplesDetails").document("i9pwzzEJ0Q9mBG2xx6QN").update(userDetailsMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    progressDialog.dismiss();
                    String error = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_SHORT).show();

                }
            });
        }

        if (firebaseAuth.getCurrentUser().getUid().equals("bQjHXSg7Rhfr2pCLNudZJ9iPuHh1")){
            firebaseFirestore.collection("PeoplesDetails").document("iAgViDxDQyNbgbjcsWsa").update(userDetailsMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    progressDialog.dismiss();
                    String error = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_SHORT).show();

                }
            });
        }

        if (firebaseAuth.getCurrentUser().getUid().equals("XxSGgvFuuIeuDlcLFHjEPzv99wf2")){
            firebaseFirestore.collection("PeoplesDetails").document("l7yW1g4XuKkM89pCZipd").update(userDetailsMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    progressDialog.dismiss();
                    String error = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_SHORT).show();

                }
            });
        }

        if (firebaseAuth.getCurrentUser().getUid().equals("AFkEytVIQCRLizoiCNZn1KSKiDg2")){
            firebaseFirestore.collection("PeoplesDetails").document("s6R2XNn6TKDJXRq7YqtJ").update(userDetailsMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "The user Settings are updated.", Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    progressDialog.dismiss();
                    String error = Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(EditProfileActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void BringImagePicker() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(EditProfileActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mainImageURI = result.getUri();
                imageViewProfileEdit.setImageURI(mainImageURI);

                buttonSave.setEnabled(true);
                isChanged = true;

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
                Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(EditProfileActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }
}