package com.example.robby.googlebooksbrowser.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
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
import com.example.robby.googlebooksbrowser.providers.BooksOps;

import java.util.ArrayList;

public class MyBooksScreen extends AppCompatActivity implements Lister {

    private ProgressBar progressBarLoading;
    private LinearLayout layoutResults;
    private TextView txtSearchResults;
    private ListView listViewBooks;

    BooksOps booksOps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books_screen);

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

        booksOps = new BooksOps(getContentResolver());
    }

    @Override
    public void onResume() {
        super.onResume();
        showResults(booksOps.queryAll());
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
                    Intent intent = new Intent(MyBooksScreen.this, BookDetailsScreen.class);
                    intent.putExtra("book", booksArrayList.get(position));
                    startActivity(intent);
                }
            });
        } else {
            txtSearchResults.setText(R.string.no_books_saved);
        }

        adapter.notifyDataSetChanged();

        // Hide the loading progress bar
        progressBarLoading.setVisibility(View.INVISIBLE);
        layoutResults.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_books_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.delete:
                AlertDialog alertDialog = new AlertDialog.Builder(MyBooksScreen.this)
                        .setTitle("Are you sure you want to clear all bookmarks?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                booksOps.deleteAll();
                                showResults(new ArrayList<Book>());
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                alertDialog.show();
                break;
        }
        return true;
    }
}
