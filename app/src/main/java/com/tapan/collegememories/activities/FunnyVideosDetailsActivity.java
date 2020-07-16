package com.tapan.collegememories.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import com.tapan.collegememories.Adapters.FunnyVideosDetailsAdapter;
import com.tapan.collegememories.Models.VideoModel;
import com.tapan.collegememories.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class FunnyVideosDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerViewVideoDetail;
    FunnyVideosDetailsAdapter funnyVideosDetailsAdapter;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;
    FloatingActionButton floatingActionButtonAddVideo;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funny_videos_details);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        recyclerViewVideoDetail = findViewById(R.id.recyclerViewVideoDetail);

        floatingActionButtonAddVideo = findViewById(R.id.fabFunnyVideo);

        Toolbar toolbar = findViewById(R.id.funny_video_details_toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

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

        Query query = firebaseFirestore.collection("Videos").orderBy("order", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<VideoModel> videoModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<VideoModel>()
                .setQuery(query,VideoModel.class).build();

        funnyVideosDetailsAdapter = new FunnyVideosDetailsAdapter(videoModelFirestoreRecyclerOptions);
        recyclerViewVideoDetail.setHasFixedSize(true);
        recyclerViewVideoDetail.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewVideoDetail.setAdapter(funnyVideosDetailsAdapter);
        funnyVideosDetailsAdapter.notifyDataSetChanged();

        floatingActionButtonAddVideo.setOnClickListener(v -> {

            Toast.makeText(this, "Sorry! This feature is not available yet..Stay tuned", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        funnyVideosDetailsAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        funnyVideosDetailsAdapter.stopListening();
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(FunnyVideosDetailsActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }
}