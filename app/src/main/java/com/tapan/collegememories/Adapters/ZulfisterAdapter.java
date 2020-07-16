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


public class ZulfisterAdapter extends FirestoreRecyclerAdapter<ZulfisterModel,ZulfisterAdapter.ZulfisterViewHolder> {


    private OnListItemClick onListItemClick;

    public ZulfisterAdapter(@NonNull FirestoreRecyclerOptions<ZulfisterModel> options) {
        super(options);
    }

    @NonNull
    @Override
    public ZulfisterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_zulfister,parent,false);
        return new ZulfisterViewHolder(view);
    }

    public void setOnListItemClick(ZulfisterAdapter.OnListItemClick onListItemClick) {
        this.onListItemClick = onListItemClick;
    }
    public interface OnListItemClick {
        void onItemClick(DocumentSnapshot snapshot, int position);
    }

    @Override
    protected void onBindViewHolder(@NonNull ZulfisterViewHolder holder, int position, @NonNull ZulfisterModel model) {
        Glide.with(holder.imageViewZulfister.getContext()).load(model.getZulfisterImage()).placeholder(R.drawable.loading_new).into(holder.imageViewZulfister);

    }


    public  class ZulfisterViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewZulfister;
        public ZulfisterViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewZulfister=itemView.findViewById(R.id.iv_zulfister_recycler);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onListItemClick != null) {
                    onListItemClick.onItemClick(getSnapshots().getSnapshot(position), position);
                }
            });
        }
    }
}
