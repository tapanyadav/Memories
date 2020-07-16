package com.tapan.collegememories.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tapan.collegememories.Models.PhotosModel;
import com.tapan.collegememories.R;
import com.tapan.collegememories.activities.ImageDetailsActivity;

import java.util.ArrayList;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.MyViewHolder> {

    Context context;
    ArrayList<PhotosModel> photos_list;

    public PhotosAdapter(Context context, ArrayList<PhotosModel> photos_list) {
        this.context = context;
        this.photos_list = photos_list;
    }



    @NonNull
    @Override
    public PhotosAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items_frag_photos, parent, false);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull PhotosAdapter.MyViewHolder holder, int position) {


        PhotosModel photosModel = photos_list.get(position);

        holder.textViewPhotoCaption.setText(photosModel.getPhotoCaption());
        Glide.with(holder.imageViewPhotos.getContext()).load(photosModel.getPhotoImage()).placeholder(R.drawable.loading_new).into(holder.imageViewPhotos);

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(holder.itemView.getContext(), ImageDetailsActivity.class);
            intent.putExtra("fullImage",photosModel.getPhotoImage());
            holder.itemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return photos_list.size();
    }


    public  class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPhotos;
        TextView textViewPhotoCaption;
        ToggleButton toggleButtonLike;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewPhotos=itemView.findViewById(R.id.image_photo);
            textViewPhotoCaption=itemView.findViewById(R.id.text_photoCaption);
            toggleButtonLike = itemView.findViewById(R.id.button_favorite);


        }
    }


}
