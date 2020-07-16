package com.tapan.collegememories.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tapan.collegememories.Models.FunnyImagesModel;
import com.tapan.collegememories.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class FunnyImageDetailsAdapter extends FirestoreRecyclerAdapter<FunnyImagesModel,FunnyImageDetailsAdapter.FunnyImagesViewHolder> {


    private OnListItemClick onListItemClick;

    public FunnyImageDetailsAdapter(@NonNull FirestoreRecyclerOptions<FunnyImagesModel> options) {
        super(options);
    }

    @NonNull
    @Override
    public FunnyImageDetailsAdapter.FunnyImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_zulfister_detail,parent,false);
        return new FunnyImagesViewHolder(view);
    }

    public void setOnListItemClick(FunnyImageDetailsAdapter.OnListItemClick onListItemClick) {
        this.onListItemClick = onListItemClick;
    }
    public interface OnListItemClick {
        void onItemClick(DocumentSnapshot snapshot, int position);
    }
    @Override
    protected void onBindViewHolder(@NonNull FunnyImageDetailsAdapter.FunnyImagesViewHolder holder, int position, @NonNull FunnyImagesModel model) {
        Glide.with(holder.imageViewZulfisterDetailImage.getContext()).load(model.getFunnyImage()).placeholder(R.drawable.loading_new).into(holder.imageViewZulfisterDetailImage);

    }



    public  class FunnyImagesViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewZulfisterDetailImage;
        public FunnyImagesViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewZulfisterDetailImage = itemView.findViewById(R.id.imageViewDetailZulfister);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onListItemClick != null) {
                    onListItemClick.onItemClick(getSnapshots().getSnapshot(position), position);
                }
            });
        }
    }
}
