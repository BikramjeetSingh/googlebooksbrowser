package com.example.robby.googlebooksbrowser.providers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

public class BookDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "saved_books_db";
    private static final int DATABASE_VERSION = 1;

    final String SQL_CREATE_BOOK_TABLE =
            "CREATE TABLE " + BookContract.BookEntry.TABLE_NAME + " ("
                    + BookContract.BookEntry._ID + " INTEGER PRIMARY KEY, "
                    + BookContract.BookEntry._BOOKID + " TEXT NOT NULL, "
                    + BookContract.BookEntry._TITLE + " TEXT NOT NULL, "
                    + BookContract.BookEntry._AUTHOR + " TEXT NOT NULL, "
                    + BookContract.BookEntry._PUBLISHER + " TEXT NOT NULL, "
                    + BookContract.BookEntry._DESCRIPTION + " TEXT NOT NULL "
                    + " );";

    public BookDatabaseHelper(Context context) {
        super(context,
                context.getCacheDir() + File.separator + DATABASE_NAME,
                null,
                DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BookContract.BookEntry.TABLE_NAME);
        onCreate(db);
    }
}
