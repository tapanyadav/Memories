package com.tapan.collegememories.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tapan.collegememories.Adapters.OnBoardAdapter;
import com.tapan.collegememories.Models.OnBoardModel;
import com.tapan.collegememories.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class OnBoardActivity extends AppCompatActivity {

    private ViewPager viewPager;
    OnBoardAdapter onBoardAdapter ;

    TabLayout tabIndicator;
    Button btnNext;
    int position = 0 ;
    Button btnLogin;
    Animation btnAnim ;
    TextView tvSkip;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        if (restorePrefData()) {
//
//            Intent mainActivity = new Intent(getApplicationContext(),LoginActivity.class );
//            startActivity(mainActivity);
//            finish();
//
//
//        }
        setContentView(R.layout.activity_on_board);

        mAuth = FirebaseAuth.getInstance();

        btnNext = findViewById(R.id.btn_next);
        btnLogin = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);
        tvSkip = findViewById(R.id.tv_skip);
        final List<OnBoardModel> mList = new ArrayList<>();
        mList.add(new OnBoardModel("Welcome","",R.drawable.welcome_girl));
        mList.add(new OnBoardModel("Buddies","Behind us are memories beside us are friends before us are dreams.",R.drawable.groupphoto));
        mList.add(new OnBoardModel("BFF's","Best friends are the siblings God forgot to give us.",R.drawable.threefriends));

        // setup viewpager
        viewPager =findViewById(R.id.screen_viewpager);
        onBoardAdapter = new OnBoardAdapter(this,mList);
        viewPager.setAdapter(onBoardAdapter);

        // setup tablayout with viewpager

        tabIndicator.setupWithViewPager(viewPager);

        // next button click Listener

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = viewPager.getCurrentItem();
                if (position < mList.size()) {

                    position++;
                    viewPager.setCurrentItem(position);


                }

                if (position == mList.size()-1) { // when we rech to the last screen

                    // TODO : show the GETSTARTED Button and hide the indicator and the next button

                    loadLastScreen();

                }

            }
        });

        // tablayout add change listener

        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size()-1) {

                    loadLastScreen();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


            }
        });



        // Get Started button click listener

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //open main activity

                Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginActivity);
                // also we need to save a boolean value to storage so next time when the user run the app
                // we could know that he is already checked the intro screen activity
                // i'm going to use shared preferences to that process
                //savePrefsData();
                finish();



            }
        });

        // skip button click listener

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(mList.size());
            }
        });
    }

//    private boolean restorePrefData() {
//
//
//        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
//        return pref.getBoolean("isIntroOpend",false);
//
//
//
//    }

//    private void savePrefsData() {
//
//        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putBoolean("isIntroOpnend",true);
//        editor.commit();
//
//    }

    // show the GETSTARTED Button and hide the indicator and the next button
    private void loadLastScreen() {

        btnNext.setVisibility(View.INVISIBLE);
        btnLogin.setVisibility(View.VISIBLE);
        tvSkip.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);

        // TODO : ADD an animation the getstarted button
        // setup animation
        btnLogin.setAnimation(btnAnim);



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
        Toast.makeText(this, "Welcome Back!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(OnBoardActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}