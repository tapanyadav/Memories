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


public class FunnyImagesAdapter extends FirestoreRecyclerAdapter<FunnyImagesModel,FunnyImagesAdapter.FunnyImagesViewHolder> {


    private OnListItemClick onListItemClick;

    public FunnyImagesAdapter(@NonNull FirestoreRecyclerOptions<FunnyImagesModel> options) {
        super(options);
    }

    @NonNull
    @Override
    public FunnyImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_funnyimages,parent,false);
        return new FunnyImagesViewHolder(view);
    }

    public void setOnListItemClick(FunnyImagesAdapter.OnListItemClick onListItemClick) {
        this.onListItemClick = onListItemClick;
    }
    public interface OnListItemClick {
        void onItemClick(DocumentSnapshot snapshot, int position);
    }

    @Override
    protected void onBindViewHolder(@NonNull FunnyImagesViewHolder holder, int position, @NonNull FunnyImagesModel model) {
        Glide.with(holder.imageViewFunny.getContext()).load(model.getFunnyImage()).placeholder(R.drawable.loading_new).into(holder.imageViewFunny);

    }


    public class FunnyImagesViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewFunny;
        public FunnyImagesViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewFunny=itemView.findViewById(R.id.iv_funny_recycler);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onListItemClick != null) {
                    onListItemClick.onItemClick(getSnapshots().getSnapshot(position), position);
                }
            });
        }
    }
}
