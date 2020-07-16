package com.tapan.collegememories.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tapan.collegememories.Models.PeopleModel;
import com.tapan.collegememories.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class PeopleAdapter extends FirestoreRecyclerAdapter<PeopleModel,PeopleAdapter.PeopleViewHolder> {


    private OnListItemClick onListItemClick;

    public PeopleAdapter(@NonNull FirestoreRecyclerOptions<PeopleModel> options) {
        super(options);
    }

    @NonNull
    @Override
    public PeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_peoples,parent,false);
        return new PeopleViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull PeopleViewHolder holder, int position, @NonNull PeopleModel model) {
        holder.textViewPeopleName.setText(model.getPeopleName());
        Glide.with(holder.imageViewPeople.getContext()).load(model.getPeopleImage()).placeholder(R.drawable.loading_new).into(holder.imageViewPeople);
    }

    public void setOnListItemClick(OnListItemClick onListItemClick) {
        this.onListItemClick = onListItemClick;
    }
    public interface OnListItemClick {
        void onItemClick(DocumentSnapshot snapshot, int position);
    }

    public  class PeopleViewHolder extends RecyclerView.ViewHolder  {

        ImageView imageViewPeople;
        TextView textViewPeopleName;

        public PeopleViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewPeople=itemView.findViewById(R.id.profilePic);
            textViewPeopleName=itemView.findViewById(R.id.peopleName);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onListItemClick != null) {
                    onListItemClick.onItemClick(getSnapshots().getSnapshot(position), position);
                }
            });
        }


    }



}
