package com.tapan.collegememories.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tapan.collegememories.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddQuotesActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    EditText editTextQuote,editTextQuoteWriter;
    Button buttonAddQuote;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quotes);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        editTextQuote = findViewById(R.id.edit_quote);
        editTextQuoteWriter = findViewById(R.id.edit_writer_name);
        buttonAddQuote = findViewById(R.id.btn_add_quote);

        String id = firebaseAuth.getCurrentUser().getUid();
        Toolbar toolbar = findViewById(R.id.add_quote_toolbar);
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

        buttonAddQuote.setOnClickListener(v -> {
            String quote = editTextQuote.getText().toString().trim();
            String writer = editTextQuoteWriter.getText().toString().trim();

            if (quote.equals("")){
                Toast.makeText(this, "Please add some quote", Toast.LENGTH_SHORT).show();
            }else {

                showProgress();
                addData(quote,writer);
            }

        });
    }

    private void addData(String quote, String writer) {

        Map<String,Object> addQuote = new HashMap<>();
        addQuote.put("quote",quote);
        addQuote.put("dateTime", FieldValue.serverTimestamp());
        if (writer.equals("")){
            addQuote.put("quoteWriter","Unknown");
        }else {
            addQuote.put("quoteWriter",writer);
        }

        firebaseFirestore.collection("Quotes").add(addQuote).addOnCompleteListener(task -> {
           if (task.isSuccessful()){
               progressDialog.dismiss();
               finish();
               Toast.makeText(this, "Your quote is added..", Toast.LENGTH_SHORT).show();
           }else {
               progressDialog.dismiss();
               Toast.makeText(this, "Upload error", Toast.LENGTH_SHORT).show();
           }
        });

    }


    private void showProgress() {
        progressDialog = new ProgressDialog(AddQuotesActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }
}