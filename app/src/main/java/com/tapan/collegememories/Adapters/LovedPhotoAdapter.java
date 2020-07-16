package com.tapan.collegememories.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tapan.collegememories.Models.PhotosModel;
import com.tapan.collegememories.R;
import com.tapan.collegememories.activities.ImageDetailsActivity;

import java.util.ArrayList;

public class LovedPhotoAdapter extends RecyclerView.Adapter<LovedPhotoAdapter.MyViewHolder> {

    Context context;
    ArrayList<PhotosModel> photos_list;

    public LovedPhotoAdapter(Context context, ArrayList<PhotosModel> photos_list) {
        this.context = context;
        this.photos_list = photos_list;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_frag_love, parent, false);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            imageViewPhotos=itemView.findViewById(R.id.image_photo);
            textViewPhotoCaption=itemView.findViewById(R.id.text_photoCaption);
        }
    }
}
