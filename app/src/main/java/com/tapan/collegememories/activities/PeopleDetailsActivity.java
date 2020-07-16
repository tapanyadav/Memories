package com.tapan.collegememories.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tapan.collegememories.Models.PeopleDetailsModel;
import com.tapan.collegememories.R;
import com.tapan.collegememories.fragments.BookmarkPhotoFragment;
import com.tapan.collegememories.fragments.PhotoFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PeopleDetailsActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    ImageView imageViewPeople,imageViewOne,imageViewTwo;
    int i;Uri uri,uriFacebook,uriSnapchat,uriLinkedin,uriGithub;
    TextView textViewPeopleName,textViewPeopleBio,textViewPeopleDob,textViewNickName,textViewCrush;
    ImageView imageViewInstagram,imageViewGithub,imageViewGithubLight,imageViewFacebook,imageViewLinkedIn,imageViewSnapchat;
    FloatingActionButton floatingActionButtonAddPhoto;
    String name;
    PhotoFragment photoFragment;
    BookmarkPhotoFragment bookmarkPhotoFragment;
    ProgressDialog progressDialog;
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_details);

        firebaseFirestore = FirebaseFirestore.getInstance();

        coordinatorLayout = findViewById(R.id.cordLayout);
        imageViewOne = findViewById(R.id.iv_yearOne);
        imageViewTwo = findViewById(R.id.iv_yearTwo);
        imageViewPeople = findViewById(R.id.profileDetailsImage);
        textViewNickName = findViewById(R.id.tv_nicknames);
        textViewPeopleBio = findViewById(R.id.tv_bio);
        textViewPeopleDob = findViewById(R.id.tv_birthdate);
        textViewPeopleName = findViewById(R.id.tv_name);
        textViewCrush = findViewById(R.id.tv_crushes);

        imageViewFacebook = findViewById(R.id.iv_facebook);
        imageViewGithub = findViewById(R.id.iv_github);
        imageViewGithubLight = findViewById(R.id.iv_github_light);
        imageViewInstagram = findViewById(R.id.iv_instagram);
        imageViewLinkedIn = findViewById(R.id.icon_link);
        imageViewSnapchat = findViewById(R.id.iv_snapchat);


        floatingActionButtonAddPhoto = findViewById(R.id.fabAddPhoto);

        coordinatorLayout.setVisibility(View.GONE);
        showProgress();

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);

        Toolbar toolbar = findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(v -> onBackPressed());

//       String recPeopleId = getIntent().getExtras().get("peopleId").toString();
        String recPeopleName =getIntent().getStringExtra("peopleName");
        name = getIntent().getStringExtra("loginUserName");


        floatingActionButtonAddPhoto.setOnClickListener(v -> {


            if (name.equals(recPeopleName)){

                Intent intent = new Intent(this,AddPhotoActivity.class);
                intent.putExtra("nameUser",name);
                startActivity(intent);
            }else {
                Toast.makeText(this, "Oops! You are not allowed to add photo to other user", Toast.LENGTH_SHORT).show();
            }



        });
         photoFragment = new PhotoFragment();
         bookmarkPhotoFragment = new BookmarkPhotoFragment();




        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(photoFragment, "Photos");
        viewPagerAdapter.addFragment(bookmarkPhotoFragment, "Loved");

        viewPager.setAdapter(viewPagerAdapter);

        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.icon_photo);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.icon_like_photo);


        int currentNightMode = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Night mode is not active, we're using the light theme
                toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black));
                imageViewGithubLight.setVisibility(View.VISIBLE);
                toolbar.setNavigationIcon(R.drawable.icon_back_new);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active, we're using dark theme
                imageViewGithub.setVisibility(View.VISIBLE);
                toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
                toolbar.setNavigationIcon(R.drawable.icon_back_white);
                break;
        }

        Query query = firebaseFirestore.collection("PeoplesDetails").whereEqualTo("peopleName", recPeopleName);

        query.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("CdActivity", "cat details:onEvent", error);
                return;
            }


            List<PeopleDetailsModel> peopleDetailsModelList = value.toObjects(PeopleDetailsModel.class);
            for ( i=0;i<peopleDetailsModelList.size();i++){
                textViewPeopleName.setText(peopleDetailsModelList.get(i).getPeopleName().split(" ")[0]);
                textViewPeopleBio.setText(peopleDetailsModelList.get(i).getPeopleBio());
                textViewPeopleDob.setText(peopleDetailsModelList.get(i).getPeopleDateOfBirth());
                textViewNickName.setText(peopleDetailsModelList.get(i).getPeopleNickname());
                textViewCrush.setText(peopleDetailsModelList.get(i).getPeopleCrush());

                Glide.with(getApplicationContext()).load(peopleDetailsModelList.get(i).getPeopleDateOne()).placeholder(R.drawable.loading_new).into(imageViewOne);
                Glide.with(getApplicationContext()).load(peopleDetailsModelList.get(i).getPeopleDateTwo()).placeholder(R.drawable.loading_new).into(imageViewTwo);
                Glide.with(getApplicationContext()).load(peopleDetailsModelList.get(i).getPeopleImage()).placeholder(R.drawable.loading_new).into(imageViewPeople);
                if (peopleDetailsModelList.get(i).getInstagramLink()!=null){
                    uri = Uri.parse(peopleDetailsModelList.get(i).getInstagramLink());
                }
                if (peopleDetailsModelList.get(i).getFacebookLink()!=null){
                    uriFacebook = Uri.parse(peopleDetailsModelList.get(i).getFacebookLink());
                }
                if (peopleDetailsModelList.get(i).getSnapchatLink()!=null){
                    uriSnapchat = Uri.parse(peopleDetailsModelList.get(i).getSnapchatLink());
                }
                if (peopleDetailsModelList.get(i).getGithubLink()!=null){
                    uriGithub = Uri.parse(peopleDetailsModelList.get(i).getGithubLink());
                }
                if (peopleDetailsModelList.get(i).getLinkedinLink()!=null){
                    uriLinkedin = Uri.parse(peopleDetailsModelList.get(i).getLinkedinLink());
                }

                progressDialog.dismiss();
                coordinatorLayout.setVisibility(View.VISIBLE);

            }


            //Toast.makeText(PeopleDetailsActivity.this, "Data: "+value.toObjects(PeopleDetailsModel.class).toString(), Toast.LENGTH_SHORT).show();
        });

        imageViewInstagram.setOnClickListener(v -> {

            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
            likeIng.setPackage("com.instagram.android");

            try {
                startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        uri));
            }
        });
        imageViewFacebook.setOnClickListener(v -> {

            if (uriFacebook!=null){
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uriFacebook);
                likeIng.setPackage("com.facebook.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            uriFacebook));
                }

            }else {
                Toast.makeText(this, "Sorry! No account found..", Toast.LENGTH_SHORT).show();
            }
        });

        imageViewSnapchat.setOnClickListener(v -> {

            if (uriSnapchat!=null){
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uriSnapchat);
                likeIng.setPackage("com.snapchat.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            uriSnapchat));
                }

            }else {
                Toast.makeText(this, "Sorry! No account found..", Toast.LENGTH_SHORT).show();
            }
        });

        imageViewLinkedIn.setOnClickListener(v -> {

            if (uriLinkedin!=null){
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uriLinkedin);
                likeIng.setPackage("com.linkedin.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            uriLinkedin));
                }

            }else {
                Toast.makeText(this, "Sorry! No account found..", Toast.LENGTH_SHORT).show();
            }
        });

        imageViewGithub.setOnClickListener(v -> {

            if (uriGithub!=null){
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uriGithub);
                likeIng.setPackage("com.github.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            uriGithub));
                }

            }else {
                Toast.makeText(this, "Sorry! No account found..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }


    private void showProgress() {
        progressDialog = new ProgressDialog(PeopleDetailsActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }

}