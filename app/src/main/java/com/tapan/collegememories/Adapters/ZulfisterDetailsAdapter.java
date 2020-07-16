package com.tapan.collegememories.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tapan.collegememories.Models.ZulfisterModel;
import com.tapan.collegememories.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class ZulfisterDetailsAdapter extends FirestoreRecyclerAdapter<ZulfisterModel,ZulfisterDetailsAdapter.MyViewHolder> {


    private OnListItemClick onListItemClick;
    public ZulfisterDetailsAdapter(@NonNull FirestoreRecyclerOptions<ZulfisterModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull ZulfisterModel model) {

        Glide.with(holder.imageViewZulfisterDetailImage).load(model.getZulfisterImage()).placeholder(R.drawable.loading_new).into(holder.imageViewZulfisterDetailImage);
    }

    public void setOnListItemClick(ZulfisterDetailsAdapter.OnListItemClick onListItemClick) {
        this.onListItemClick = onListItemClick;
    }
    public interface OnListItemClick {
        void onItemClick(DocumentSnapshot snapshot, int position);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_zulfister_detail,parent,false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewZulfisterDetailImage;
        public MyViewHolder(@NonNull View itemView) {
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
