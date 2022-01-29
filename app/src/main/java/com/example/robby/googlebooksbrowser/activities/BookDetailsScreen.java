package com.example.robby.googlebooksbrowser.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robby.googlebooksbrowser.R;
import com.example.robby.googlebooksbrowser.models.Book;
import com.example.robby.googlebooksbrowser.providers.BooksOps;

public class BookDetailsScreen extends AppCompatActivity {

    private TextView txtBookTitle;
    private TextView txtAuthor;
    private TextView txtPublisher;
    private TextView txtDescription;

    private Book book;
    private boolean isBookmarked;

    private BooksOps booksOps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details_screen);

        // Set up Action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Find Views
        txtBookTitle = findViewById(R.id.txt_book_title);
        txtAuthor = findViewById(R.id.txt_author);
        txtPublisher = findViewById(R.id.txt_publisher);
        txtDescription = findViewById(R.id.txt_description);

        // Set texts
        book = getIntent().getParcelableExtra("book");
        txtBookTitle.setText(book.getTitle());
        txtAuthor.setText(book.getAuthor());
        txtPublisher.setText(String.format("Published by: %s", book.getPublisher()));
        txtDescription.setText(book.getDescription());

        booksOps = new BooksOps(getContentResolver());

        isBookmarked = checkIfBookmarked();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_details_screen, menu);
        if(isBookmarked) {
            menu.findItem(R.id.bookmark).setIcon(R.drawable.ic_bookmarked);
        } else {
            menu.findItem(R.id.bookmark).setIcon(R.drawable.ic_bookmark);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.bookmark:
                if(isBookmarked) { // If already bookmarked, delete the bookmark
                    booksOps.delete(book);
                    item.setIcon(R.drawable.ic_bookmark);
                    Toast.makeText(
                            BookDetailsScreen.this,
                            "Bookmark Removed!",
                            Toast.LENGTH_SHORT
                    ).show();
                } else { // If not bookmarked, add the bookmark
                    booksOps.insert(book);
                    item.setIcon(R.drawable.ic_bookmarked);
                    Toast.makeText(
                            BookDetailsScreen.this,
                            "Bookmark Added!",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                isBookmarked = !isBookmarked;
                break;
        }
        return true;
    }

    private boolean checkIfBookmarked() {
        return booksOps.searchById(book.getId());
    }
}
