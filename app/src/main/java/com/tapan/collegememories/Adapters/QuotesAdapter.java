package com.tapan.collegememories.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tapan.collegememories.Models.QuotesModel;
import com.tapan.collegememories.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class QuotesAdapter extends FirestoreRecyclerAdapter<QuotesModel,QuotesAdapter.MyViewHolder> {

    public QuotesAdapter(@NonNull FirestoreRecyclerOptions<QuotesModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull QuotesModel model) {
        holder.textViewQuoteWriter.setText(model.getQuoteWriter());
        holder.textViewQuotes.setText(model.getQuote());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_quotes,parent,false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewQuotes,textViewQuoteWriter;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewQuotes = itemView.findViewById(R.id.text_quotes);
            textViewQuoteWriter = itemView.findViewById(R.id.quoteWriter);
        }
    }
}
