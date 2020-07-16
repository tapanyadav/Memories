package com.tapan.collegememories.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tapan.collegememories.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Objects;

public class ImageDetailsActivity extends AppCompatActivity  {

    private static final int PERMISSION_STORAGE_CODE = 1000;
    FloatingActionButton floatingActionButtonDown;
   // ImageView imageViewDetails;
   ProgressDialog progressDialog;
    String image;
    String fileName = "Memories ",fileExtension = ".jpg";
//    private ScaleGestureDetector mScaleGestureDetector;
//    private float mScaleFactor = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        Toolbar toolbar = findViewById(R.id.photo_details_toolbar);
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
        floatingActionButtonDown = findViewById(R.id.fabDownload);
        //imageViewDetails = findViewById(R.id.image_details);

        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);


        //mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
       // mScaleFactor = Math.max(0.9f, Math.min(mScaleFactor, 10.0f));
         image = getIntent().getStringExtra("fullImage");
        Glide.with(this).load(image).placeholder(R.drawable.loading_new).into(photoView);

        floatingActionButtonDown.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){

                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permissions,PERMISSION_STORAGE_CODE);
                }else {
                    startDownloading();
                }
            }else {
                startDownloading();
            }

        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_STORAGE_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startDownloading();
            } else {
                Toast.makeText(this, "Permission denied..", Toast.LENGTH_SHORT).show();
            }
        }
    }

//    public boolean onTouchEvent(MotionEvent motionEvent) {
//        mScaleGestureDetector.onTouchEvent(motionEvent);
//
//        final int action = MotionEventCompat.getActionMasked(motionEvent);
//
//        switch (action) {
//            case MotionEvent.ACTION_DOWN: {
//                final int pointerIndex = MotionEventCompat.getActionIndex(motionEvent);
//                final float x = MotionEventCompat.getX(motionEvent, pointerIndex);
//                final float y = MotionEventCompat.getY(motionEvent, pointerIndex);
//
//                // Remember where we started (for dragging)
//                mLastTouchX = x;
//                mLastTouchY = y;
//                // Save the ID of this pointer (for dragging)
//                mActivePointerId = MotionEventCompat.getPointerId(motionEvent, 0);
//                break;
//            }
//
//            case MotionEvent.ACTION_MOVE: {
//                // Find the index of the active pointer and fetch its position
//                final int pointerIndex =
//                        MotionEventCompat.findPointerIndex(motionEvent, mActivePointerId);
//
//                final float x = MotionEventCompat.getX(motionEvent, pointerIndex);
//                final float y = MotionEventCompat.getY(motionEvent, pointerIndex);
//
//                // Calculate the distance moved
//                final float dx = x - mLastTouchX;
//                final float dy = y - mLastTouchY;
//
//                mPosX += dx;
//                mPosY += dy;
//
//                invalidate();
//
//                // Remember this touch position for the next move event
//                mLastTouchX = x;
//                mLastTouchY = y;
//
//                break;
//            }
//
//            case MotionEvent.ACTION_UP: {
//                mActivePointerId = INVALID_POINTER_ID;
//                break;
//            }
//
//            case MotionEvent.ACTION_CANCEL: {
//                mActivePointerId = INVALID_POINTER_ID;
//                break;
//            }
//
//            case MotionEvent.ACTION_POINTER_UP: {
//
//                final int pointerIndex = MotionEventCompat.getActionIndex(motionEvent);
//                final int pointerId = MotionEventCompat.getPointerId(motionEvent, pointerIndex);
//
//                if (pointerId == mActivePointerId) {
//                    // This was our active pointer going up. Choose a new
//                    // active pointer and adjust accordingly.
//                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
//                    mLastTouchX = MotionEventCompat.getX(motionEvent, newPointerIndex);
//                    mLastTouchY = MotionEventCompat.getY(motionEvent, newPointerIndex);
//                    mActivePointerId = MotionEventCompat.getPointerId(motionEvent, newPointerIndex);
//                }
//                break;
//            }
//        }
//        return true;
//    }

//    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
//        @Override
//        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
//            mScaleFactor *= scaleGestureDetector.getScaleFactor();
//            mScaleFactor = Math.max(1f,
//                    Math.min(mScaleFactor, 10.0f));
//
//            imageViewDetails.setScaleX(mScaleFactor);
//            imageViewDetails.setScaleY(mScaleFactor);
//            return true;
//        }
//    }

    private void startDownloading() {

        DownloadManager downloadManager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(image);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Memories");
        request.setDescription("Image Downloading..");
        Toast.makeText(this, "Download Start..", Toast.LENGTH_SHORT).show();
        //request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,fileName+fileExtension);
        downloadManager.enqueue(request);
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(ImageDetailsActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }
}