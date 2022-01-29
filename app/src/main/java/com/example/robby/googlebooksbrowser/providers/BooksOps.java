package com.example.robby.googlebooksbrowser.providers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.robby.googlebooksbrowser.models.Book;

import java.util.ArrayList;

public class BooksOps {

    private ContentResolver contentResolver;

    public BooksOps(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    /**
     * Inserts a book into the database
     * @param book the Book to be inserted
     */
    public void insert(Book book) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BookContract.BookEntry._BOOKID, book.getId());
        contentValues.put(BookContract.BookEntry._TITLE, book.getTitle());
        contentValues.put(BookContract.BookEntry._AUTHOR, book.getAuthor());
        contentValues.put(BookContract.BookEntry._DESCRIPTION, book.getDescription());
        contentValues.put(BookContract.BookEntry._PUBLISHER, book.getPublisher());

        contentResolver.insert(BookContract.BookEntry.CONTENT_URI, contentValues);
    }

    /**
     * Delets a particular entry in the database
     * @param book the book to be deleted
     */
    public void delete(Book book) {
        contentResolver.delete(
                BookContract.BookEntry.CONTENT_URI,
                "_bookid=?",
                new String[]{ book.getId() });
    }

    /**
     * Clears the contents of the database
     */
    public void deleteAll() {
        contentResolver.delete(
                BookContract.BookEntry.CONTENT_URI,
                null,
                null);
    }

    /**
     * Query all the books in the database
     * @return an ArrayList containing the books in the database
     */
    public ArrayList<Book> queryAll() {
        Cursor cursor = contentResolver.query(
                BookContract.BookEntry.CONTENT_URI,
                BookContract.BookEntry.columns,
                null,
                null,
                null);
        ArrayList<Book> res = new ArrayList<>();
        if(cursor != null && cursor.moveToFirst()) {
            do {
                res.add(new Book(cursor));
            } while (cursor.moveToNext());
        }

        return res;
    }

    /**
     * Searches for a Book by ID and returns whether or not it was found
     * @param id the ID of the book to search for
     * @return true if the book was found, else false
     */
    public boolean searchById(String id) {
        Cursor cursor = contentResolver.query(
                BookContract.BookEntry.CONTENT_URI,
                BookContract.BookEntry.columns,
                "_bookid=?",
                new String[]{ id },
                null);

        boolean isFound = cursor != null && cursor.moveToFirst();

        if (cursor != null) {
            cursor.close();
        }

        return isFound;
    }
}
