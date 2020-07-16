package com.tapan.collegememories.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tapan.collegememories.Adapters.FunnyImagesAdapter;
import com.tapan.collegememories.Adapters.PeopleAdapter;
import com.tapan.collegememories.Adapters.TotalSixAdapter;
import com.tapan.collegememories.Adapters.ZulfisterAdapter;
import com.tapan.collegememories.Models.FunnyImagesModel;
import com.tapan.collegememories.Models.PeopleModel;
import com.tapan.collegememories.Models.TotalSixModel;
import com.tapan.collegememories.Models.ZulfisterModel;
import com.tapan.collegememories.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import me.toptas.fancyshowcase.FancyShowCaseView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,DrawerLayout.DrawerListener{

    FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    RecyclerView recyclerViewPeoples, recyclerViewZulfisters, recyclerViewTotalSix, recyclerViewFunnyImage;
    PeopleAdapter peopleAdapter;
    ZulfisterAdapter zulfisterAdapter;
    TotalSixAdapter totalSixAdapter;
    FunnyImagesAdapter funnyImagesAdapter;
    private ImageView imageViewHeart, imageViewHeartFunny;
    private AnimatedVectorDrawable emptyHeart;
    private AnimatedVectorDrawable fillHeart;
    TextView textViewZulfisterAll, textViewTotalSixAll, textViewFunnyAll;
    private boolean full = false;
    CoordinatorLayout coordinatorLayout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
//    VideoAdapter videoAdapter;
    ShimmerFrameLayout shimmerFrameLayout;
    public static FragmentManager fragmentManager;
    ProgressDialog progressDialog;
    CircleImageView circleImageView;
    TextView user_name;
    String name;
    public static HashMap<String, Object> profile_activity_data = new HashMap();
    static final float END_SCALE = 0.7f;
    boolean firstStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        shimmerFrameLayout = findViewById(R.id.shimmerAnimation);
        fragmentManager = getSupportFragmentManager();
        coordinatorLayout = findViewById(R.id.cordLay);

        imageViewHeart = findViewById(R.id.imageHeart);
        imageViewHeartFunny = findViewById(R.id.imageHeartFunny);
        recyclerViewPeoples = findViewById(R.id.recycler_peoples);
        recyclerViewZulfisters = findViewById(R.id.recycler_zulfisters);
        recyclerViewTotalSix = findViewById(R.id.recycler_total_six);
        recyclerViewFunnyImage = findViewById(R.id.recycler_funny);
        //recyclerViewVideo = findViewById(R.id.recycler_video);
        textViewZulfisterAll = findViewById(R.id.text_zulfister_all);
        textViewTotalSixAll = findViewById(R.id.text_total_six_details);
        textViewFunnyAll = findViewById(R.id.text_funny_all);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        //textViewVideoAll = findViewById(R.id.text_video_all);


        SharedPreferences sharedPreferences = getSharedPreferences("prefs",MODE_PRIVATE);
         firstStart = sharedPreferences.getBoolean("firstStart",true);

        View view = navigationView.getHeaderView(0);
        user_name = view.findViewById(R.id.menu_slogan);
        circleImageView = view.findViewById(R.id.profilePic);

        coordinatorLayout.setVisibility(View.GONE);

        new Handler().postDelayed(() -> {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
//            linearLayout.setVisibility(View.VISIBLE);
//            appBarLayout.setVisibility(View.VISIBLE);
            coordinatorLayout.setVisibility(View.VISIBLE);
            if (firstStart){
                mainDialog();
            }

        }, 2000);

        emptyHeart
                = (AnimatedVectorDrawable)
                getDrawable(
                        R.drawable.avd_heart_empty);
        fillHeart
                = (AnimatedVectorDrawable)
                getDrawable(
                        R.drawable.avd_heart_fill);

        animate(imageViewHeart);
        animate(imageViewHeartFunny);

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore.collection("Users").document(currentUserId);
        documentReference.addSnapshotListener((value, error) -> {
            name = (String) value.get("userName");

        });


        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openNavDrawer, R.string.closeNavDrawer);

        drawerLayout.addDrawerListener(toggle);
        drawerLayout.addDrawerListener(this);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        navigationView.setItemIconTintList(null);
        navigationView.getMenu().getItem(0).setChecked(true);




        animateNavigationDrawer();

        int currentNightMode = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Night mode is not active, we're using the light theme
                toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black));
                toolbar.setNavigationIcon(R.drawable.icon_menu);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active, we're using dark theme
                toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
                toolbar.setNavigationIcon(R.drawable.menu_white);
                break;
        }

        textViewZulfisterAll.setOnClickListener(v -> {
            Intent intent = new Intent(this, ZulfisterDetailActivity.class);
            startActivity(intent);
        });

        textViewTotalSixAll.setOnClickListener(v -> {
            Intent intent = new Intent(this, TotalSixDetailActivity.class);
            startActivity(intent);
        });

        textViewFunnyAll.setOnClickListener(v -> {
            Intent intent = new Intent(this, FunnyImageDetailsActivity.class);
            startActivity(intent);
        });

//        textViewVideoAll.setOnClickListener(v -> {
//            Intent intent = new Intent(this, FunnyVideosDetailsActivity.class);
//            startActivity(intent);
//        });

        Query query = firebaseFirestore.collection("People").orderBy("peopleName");

        FirestoreRecyclerOptions<PeopleModel> peopleModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<PeopleModel>()
                .setQuery(query, PeopleModel.class).build();

        peopleAdapter = new PeopleAdapter(peopleModelFirestoreRecyclerOptions);
        recyclerViewPeoples.setHasFixedSize(true);
        recyclerViewPeoples.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewPeoples.setAdapter(peopleAdapter);
        peopleAdapter.notifyDataSetChanged();

        peopleAdapter.setOnListItemClick((snapshot, position) -> {
            String peopleId = snapshot.getId();
            Intent intent = new Intent(MainActivity.this, PeopleDetailsActivity.class);
            intent.putExtra("peopleId", peopleId);
            profile_activity_data.put("peopleId",peopleId);
            profile_activity_data.put("peopleName",(Serializable) snapshot.getData().get("peopleName"));
            profile_activity_data.put("loginUserName",name);
            intent.putExtra("peopleName", (Serializable) snapshot.getData().get("peopleName"));
            intent.putExtra("loginUserName",name);
            startActivity(intent);
        });

        Query query1 = firebaseFirestore.collection("Zulfisters").orderBy("priority");
        FirestoreRecyclerOptions<ZulfisterModel> zulfisterModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<ZulfisterModel>()
                .setQuery(query1, ZulfisterModel.class).build();

        zulfisterAdapter = new ZulfisterAdapter(zulfisterModelFirestoreRecyclerOptions);
        recyclerViewZulfisters.setHasFixedSize(true);
        recyclerViewZulfisters.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewZulfisters.setAdapter(zulfisterAdapter);
        zulfisterAdapter.notifyDataSetChanged();

        zulfisterAdapter.setOnListItemClick((snapshot, position) -> {
            String link = (String) snapshot.get("zulfisterImage");
            Intent intent = new Intent(this, ImageDetailsActivity.class);
            intent.putExtra("loginUserName",name);
            intent.putExtra("fullImage", link);
            startActivity(intent);
        });
        Query query2 = firebaseFirestore.collection("TotalSix").orderBy("priority");
        FirestoreRecyclerOptions<TotalSixModel> totalModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<TotalSixModel>()
                .setQuery(query2, TotalSixModel.class).build();

        totalSixAdapter = new TotalSixAdapter(totalModelFirestoreRecyclerOptions);
        recyclerViewTotalSix.setHasFixedSize(true);
        recyclerViewTotalSix.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTotalSix.setAdapter(totalSixAdapter);
        totalSixAdapter.notifyDataSetChanged();

        totalSixAdapter.setOnListItemClick((snapshot, position) -> {

            String link = (String) snapshot.get("totalSixImage");
            Intent intent = new Intent(this, ImageDetailsActivity.class);
            intent.putExtra("loginUserName",name);
            intent.putExtra("fullImage", link);
            startActivity(intent);
        });
        Query query3 = firebaseFirestore.collection("FunnyImages").orderBy("priority");
        FirestoreRecyclerOptions<FunnyImagesModel> funnyImagesModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<FunnyImagesModel>()
                .setQuery(query3, FunnyImagesModel.class).build();

        funnyImagesAdapter = new FunnyImagesAdapter(funnyImagesModelFirestoreRecyclerOptions);
        recyclerViewFunnyImage.setHasFixedSize(true);
        recyclerViewFunnyImage.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewFunnyImage.setAdapter(funnyImagesAdapter);
        funnyImagesAdapter.notifyDataSetChanged();

        funnyImagesAdapter.setOnListItemClick((snapshot, position) -> {
            String link = (String) snapshot.get("funnyImage");
            Intent intent = new Intent(this, ImageDetailsActivity.class);
            intent.putExtra("loginUserName",name);
            intent.putExtra("fullImage", link);
            startActivity(intent);
        });

//        Query query4 = firebaseFirestore.collection("Videos").orderBy("priority");
//        FirestoreRecyclerOptions<VideoModel> videoModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<VideoModel>()
//                .setQuery(query4, VideoModel.class).build();
//
//        videoAdapter = new VideoAdapter(videoModelFirestoreRecyclerOptions);
//        recyclerViewVideo.setHasFixedSize(true);
//        recyclerViewVideo.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
//        recyclerViewVideo.setAdapter(videoAdapter);
//        videoAdapter.notifyDataSetChanged();

        circleImageView.setOnClickListener(v -> {
            Intent intent = new Intent(this,EditProfileActivity.class);
            startActivity(intent);
        });


        firebaseFirestore.collection("People").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                user_name.setText(""+value.get("peopleName"));
                Glide.with(getApplicationContext()).load(value.get("peopleImage")).placeholder(R.drawable.loading_new).into(circleImageView);
                profile_activity_data.put("image",value.get("peopleImage"));
                profile_activity_data.put("name",value.get("peopleName"));
            }
        });
    }





    public void animate(View view) {
        AnimatedVectorDrawable drawable
                = full
                ? emptyHeart
                : fillHeart;
        imageViewHeart.setImageDrawable(drawable);
        imageViewHeartFunny.setImageDrawable(drawable);
        drawable.start();

        full = !full;
    }

    private void animateNavigationDrawer() {

        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                coordinatorLayout.setScaleX(offsetScale);
                coordinatorLayout.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = coordinatorLayout.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                coordinatorLayout.setTranslationX(xTranslation);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.home:
                drawerLayout.closeDrawer(Gravity.LEFT);
                return true;
            case R.id.feedback:
                showExpDialog();
                //Toast.makeText(this, "Feedback", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.quotes:
                Intent intent = new Intent(this, QuotesActivity.class);
                startActivity(intent);
                //Toast.makeText(this, "Quotes", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.log_out:
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder.setMessage("Are you sure you want to logout?")
//                        .setPositiveButton("OK", (dialogInterface, i) -> logOut()).setNegativeButton("Cancel", null);
//                AlertDialog alert = builder.create();
//                alert.show();

               logoutDialog();
                return true;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutDialog(){
        ViewGroup viewGroup = findViewById(android.R.id.content);

        View dialogView = LayoutInflater.from(this).inflate(R.layout.logout_dialog, viewGroup, false);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);

        Button buttonYes = alertDialog.findViewById(R.id.btn_logout);
        Button buttonCancel = alertDialog.findViewById(R.id.btn_cancel);
        ImageView imageViewClose = alertDialog.findViewById(R.id.imageClose);

        int currentNightMode = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Night mode is not active, we're using the light theme
                imageViewClose.setImageResource(R.drawable.ic_baseline_close_primary_24);

                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active, we're using dark theme
                imageViewClose.setImageResource(R.drawable.ic_baseline_close_24);
                break;
        }
        buttonYes.setOnClickListener(v -> {
            logOut();
        });
        buttonCancel.setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        assert imageViewClose != null;
        imageViewClose.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
    }

    private void mainDialog(){
        ViewGroup viewGroup = findViewById(android.R.id.content);

        View dialogView = LayoutInflater.from(this).inflate(R.layout.home_dialog, viewGroup, false);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);
        Button buttonOk;
        buttonOk = alertDialog.findViewById(R.id.main_ok);
        buttonOk.setOnClickListener(v -> {
            alertDialog.dismiss();
            SharedPreferences sharedPreferences = getSharedPreferences("prefs",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstStart",false);
            editor.apply();
        });
        ImageView imageViewClose = alertDialog.findViewById(R.id.imageClose);

        int currentNightMode = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Night mode is not active, we're using the light theme
                imageViewClose.setImageResource(R.drawable.ic_baseline_close_primary_24);

                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active, we're using dark theme
                imageViewClose.setImageResource(R.drawable.ic_baseline_close_24);
                break;
        }
        assert imageViewClose != null;
        imageViewClose.setOnClickListener(v -> {
            alertDialog.dismiss();
            SharedPreferences sharedPreferences = getSharedPreferences("prefs",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstStart",false);
            editor.apply();
        });


    }
    private void showExpDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);



        View dialogView = LayoutInflater.from(this).inflate(R.layout.experience_dialog, viewGroup, false);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);

        TextView textViewEmail = alertDialog.findViewById(R.id.text_email);
        EditText editTextFeedback  = alertDialog.findViewById(R.id.edit_text_feedback);

        textViewEmail.setText(mAuth.getCurrentUser().getEmail());


        Button buttonFeedback = alertDialog.findViewById(R.id.btnFeed);
        ImageView imageViewClose = alertDialog.findViewById(R.id.imageClose);

        assert buttonFeedback != null;
        buttonFeedback.setOnClickListener(v -> {
            showProgress();
            String feedback = editTextFeedback.getText().toString().trim();
            if (!feedback.equals("")){
                Map<String,Object> hashFeedback = new HashMap<>();
                hashFeedback.put("feedback",feedback);
                hashFeedback.put("email",mAuth.getCurrentUser().getEmail());
                firebaseFirestore.collection("Users").document(mAuth.getCurrentUser().getUid()).collection("Feedback").add(hashFeedback).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                        progressDialog.dismiss();
                        alertDialog.dismiss();
                        Toast.makeText(this, "Your feedback received!!", Toast.LENGTH_SHORT).show();
                    }else {

                        progressDialog.dismiss();
                        Toast.makeText(this, "Some error!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                progressDialog.dismiss();
                Toast.makeText(this, "Please add some feedback!!", Toast.LENGTH_SHORT).show();
            }


        });

        assert imageViewClose != null;
        imageViewClose.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
    }

    private void logOut() {
        mAuth.signOut();
        sendToLogin();
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void sendToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(MainActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        peopleAdapter.startListening();
        zulfisterAdapter.startListening();
        totalSixAdapter.startListening();
        funnyImagesAdapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        peopleAdapter.stopListening();
        zulfisterAdapter.stopListening();
        totalSixAdapter.stopListening();
        funnyImagesAdapter.stopListening();

    }

    private void showProgress() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }


    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

        if (firstStart){
            new FancyShowCaseView.Builder(this)
                    .focusOn(circleImageView)
                    .title("You can edit your profile from here..")
                    .titleStyle(R.style.CustomTitle2,Gravity.CENTER)
                    .focusBorderSize(12)
                    .focusBorderColor(R.color.colorPrimary)
                    .build()
                    .show();
        }

//        SharedPreferences sharedPreferences = getSharedPreferences("prefs",MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("firstStart",false);
//        editor.apply();

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {


    }
}