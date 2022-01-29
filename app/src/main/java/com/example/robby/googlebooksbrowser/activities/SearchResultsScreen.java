package com.example.robby.googlebooksbrowser.activities;

import android.content.Intent;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.robby.googlebooksbrowser.R;
import com.example.robby.googlebooksbrowser.interfaces.Lister;
import com.example.robby.googlebooksbrowser.models.Book;
import com.example.robby.googlebooksbrowser.services.DownloadHandler;
import com.example.robby.googlebooksbrowser.services.GetBooksService;

import java.util.ArrayList;

public class SearchResultsScreen extends AppCompatActivity implements Lister {

    private ProgressBar progressBarLoading;
    private LinearLayout layoutResults;
    private TextView txtSearchResults;
    private ListView listViewBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_screen);

        // Set up Action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Find Views
        progressBarLoading = findViewById(R.id.progress_bar_loading);
        layoutResults = findViewById(R.id.layout_results);
        txtSearchResults = findViewById(R.id.txt_search_results);
        listViewBooks = findViewById(R.id.listview_books);

        // Display the loading progress bar
        progressBarLoading.setVisibility(View.VISIBLE);
        layoutResults.setVisibility(View.INVISIBLE);

        // Start the service to search for books matching the query
        Intent intent = new Intent(this, GetBooksService.class);
        intent.putExtra("search_text", getIntent().getStringExtra("search_text"));
        intent.putExtra("search_by_title", getIntent().getBooleanExtra("search_by_title", true));
        intent.putExtra("messenger", new Messenger(new DownloadHandler(this)));
        startService(intent);
    }

    @Override
    public void showResults(final ArrayList<Book> booksArrayList) {
        String[] titles = new String[booksArrayList.size()];
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);
        listViewBooks.setAdapter(adapter);

        if(booksArrayList.size() > 0) {
            for (int i = 0; i < booksArrayList.size(); i++) {
                titles[i] = booksArrayList.get(i).getTitle();
            }

            listViewBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(SearchResultsScreen.this, BookDetailsScreen.class);
                    intent.putExtra("book", booksArrayList.get(position));
                    startActivity(intent);
                }
            });
        } else {
            txtSearchResults.setText(R.string.search_results_empty);
        }

        adapter.notifyDataSetChanged();

        // Hide the loading progress bar
        progressBarLoading.setVisibility(View.INVISIBLE);
        layoutResults.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
