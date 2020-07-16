package com.tapan.collegememories.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tapan.collegememories.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    Button btnSignIn;
    EditText etLogEmail, etLogPass;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignIn = findViewById(R.id.login_btn);
        etLogEmail = findViewById(R.id.login_email);
        etLogPass = findViewById(R.id.login_password);

        mAuth = FirebaseAuth.getInstance();

        btnSignIn.setOnClickListener(v -> {
            // Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            //  startActivity(intent);

            String loginEmail = etLogEmail.getText().toString();
            String loginPass = etLogPass.getText().toString();


            if (!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPass)) {
                //loginProgress.setVisibility(View.VISIBLE);

                showProgress();
                mAuth.signInWithEmailAndPassword(loginEmail, loginPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            String Id = mAuth.getCurrentUser().getUid();
                            HashMap<String,Object> userDefaultData = new HashMap<>();
                            if (loginEmail.equals("yadav.tapanpr@gmail.com")){
                                userDefaultData.put("userName","Tapan Yadav");
                            }if (loginEmail.equals("akankshag58@gmail.com")){
                                userDefaultData.put("userName","Akanksha Gupta");
                            }if (loginEmail.equals("akankshamishrakmggp123@gmail.com")){
                                userDefaultData.put("userName","Akanksha Mishra");
                            }if (loginEmail.equals("shivamgpt2111@gmail.com")){
                                userDefaultData.put("userName","Shivam Gupta");
                            }if (loginEmail.equals("prashantbhardwaj282@gmail.com")){
                                userDefaultData.put("userName","Prashant Bhardwaj");
                            }if (loginEmail.equals("utkarshgupta0808@gmail.com")){
                                userDefaultData.put("userName","Utkarsh Gupta");
                            }if (loginEmail.equals("nikitasharma.ns151@gmail.com")){
                                userDefaultData.put("userName","Nikita Sharma");
                            }if (loginEmail.equals("sonilrastogi66@gmail.com")){
                                userDefaultData.put("userName","Sonil Rastogi");
                            }if (loginEmail.equals("parulsinghps98@gmail.com")){
                                userDefaultData.put("userName","Parul Singh");
                            }if (loginEmail.equals("email2ujjwal.us@gmail.com")){
                                userDefaultData.put("userName","Ujjwal Singh");
                            }if (loginEmail.equals("nikhilyadav50586@gmail.com")){
                                userDefaultData.put("userName","Nikhil Yadav");
                            }if (loginEmail.equals("tshivam260@gmail.com")){
                                userDefaultData.put("userName","Shivam Tyagi");
                            }
                            userDefaultData.put("userEmail",loginEmail);
                            userDefaultData.put("userPassword",loginPass);

                            FirebaseFirestore.getInstance().collection("Users").document(Id).set(userDefaultData);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            Toast.makeText(LoginActivity.this, "Welcome to Memories!", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();

                        } else {

                            String errorMessage = task.getException().getMessage();
                            Toast.makeText(LoginActivity.this, "Error : " + errorMessage, Toast.LENGTH_LONG).show();


                        }
                        // loginProgress.setVisibility(View.INVISIBLE);
                        progressDialog.dismiss();
                    }
                });

            }
        });


    }

    private void showProgress() {

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI();
        }

    }

    private void updateUI() {
        Toast.makeText(this, "You are logged in!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}