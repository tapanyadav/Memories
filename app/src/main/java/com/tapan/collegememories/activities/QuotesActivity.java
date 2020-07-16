package com.tapan.collegememories.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.tapan.collegememories.Adapters.QuotesAdapter;
import com.tapan.collegememories.Models.QuotesModel;
import com.tapan.collegememories.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class QuotesActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    QuotesAdapter quotesAdapter;
    RecyclerView recyclerViewQuotes;
    FloatingActionButton floatingActionButtonAddQuote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerViewQuotes = findViewById(R.id.recyclerViewQuotes);
        floatingActionButtonAddQuote = findViewById(R.id.fabQuotes);

        Toolbar toolbar = findViewById(R.id.quotes_toolbar);
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

        Query query = firebaseFirestore.collection("Quotes").orderBy("dateTime", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<QuotesModel> quotesModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<QuotesModel>()
                .setQuery(query,QuotesModel.class).build();

        quotesAdapter = new QuotesAdapter(quotesModelFirestoreRecyclerOptions);
        recyclerViewQuotes.setHasFixedSize(true);
        recyclerViewQuotes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewQuotes.setAdapter(quotesAdapter);
        quotesAdapter.notifyDataSetChanged();

        floatingActionButtonAddQuote.setOnClickListener(v -> {
            Intent intent = new Intent(this,AddQuotesActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        quotesAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        quotesAdapter.stopListening();
    }

}