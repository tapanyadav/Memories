package com.tapan.collegememories.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tapan.collegememories.Models.TotalSixModel;
import com.tapan.collegememories.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class TotalSixAdapter extends FirestoreRecyclerAdapter<TotalSixModel,TotalSixAdapter.TotalSixViewHolder> {


    private OnListItemClick onListItemClick;

    public TotalSixAdapter(@NonNull FirestoreRecyclerOptions<TotalSixModel> options) {
        super(options);
    }

    @NonNull
    @Override
    public TotalSixViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_totalsix,parent,false);
        return new TotalSixViewHolder(view);
    }

    public void setOnListItemClick(TotalSixAdapter.OnListItemClick onListItemClick) {
        this.onListItemClick = onListItemClick;
    }
    public interface OnListItemClick {
        void onItemClick(DocumentSnapshot snapshot, int position);
    }
    @Override
    protected void onBindViewHolder(@NonNull TotalSixViewHolder holder, int position, @NonNull TotalSixModel model) {
        Glide.with(holder.imageViewTotal.getContext()).load(model.getTotalSixImage()).placeholder(R.drawable.loading_new).into(holder.imageViewTotal);

    }


    public  class TotalSixViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewTotal;
        public TotalSixViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewTotal=itemView.findViewById(R.id.iv_total_recycler);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onListItemClick != null) {
                    onListItemClick.onItemClick(getSnapshots().getSnapshot(position), position);
                }
            });
        }
    }
}
