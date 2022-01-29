package com.example.robby.googlebooksbrowser.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.robby.googlebooksbrowser.R;
import com.example.robby.googlebooksbrowser.utils.Helpers;

public class MainScreen extends AppCompatActivity {

    private EditText etBook;
    private RadioGroup radioGroupSearchOptions;
    private Button btnSearchBooks;
    private Button btnMyBooks;

    private boolean isTitleSelected; // If true, user has opted to search by Title, else by ISBN

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        // Find Views
        etBook = findViewById(R.id.et_book);
        radioGroupSearchOptions = findViewById(R.id.radio_group_search_options);
        btnSearchBooks = findViewById(R.id.btn_search_books);
        btnMyBooks = findViewById(R.id.btn_my_books);

        isTitleSelected = true;

        radioGroupSearchOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_title:
                        isTitleSelected = true;
                        etBook.setHint(R.string.enter_book_name);
                        break;
                    default:
                        isTitleSelected = false;
                        etBook.setHint(R.string.enter_book_isbn);
                        break;
                }
            }
        });

        btnSearchBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etBook.getText().toString().trim().isEmpty()) {
                    Toast.makeText(
                            MainScreen.this,
                            R.string.please_enter_a_name_or_isbn,
                            Toast.LENGTH_LONG
                    ).show();
                } else if (!Helpers.isNetworkAvailable(MainScreen.this)) {
                    Toast.makeText(
                            MainScreen.this,
                            R.string.please_check_internet,
                            Toast.LENGTH_LONG
                    ).show();
                } else {
                    Intent intent = new Intent(
                            MainScreen.this,
                            SearchResultsScreen.class
                    );
                    intent.putExtra("search_text", etBook.getText().toString());
                    intent.putExtra("search_by_title", isTitleSelected);
                    startActivity(intent);
                }
            }
        });

        btnMyBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, MyBooksScreen.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.help:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.layout_help_dialog, null);
                dialogBuilder.setView(dialogView);
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
                break;
        }
        return true;
    }

}
