package com.example.robby.googlebooksbrowser.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;

public class BookProvider extends ContentProvider {

    private BookDatabaseHelper bookDatabaseHelper;

    private UriMatcher uriMatcher = buildUriMatcher();
    private Context context;

    private static final int SINGLE_BOOK = 2852;
    private static final int MULT_BOOKS = 2853;

    @Override
    public boolean onCreate() {
        context = getContext();
        bookDatabaseHelper = new BookDatabaseHelper(context);
        return true;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case MULT_BOOKS:
                return BookContract.BookEntry.CONTENT_ITEMS_TYPE;
            case SINGLE_BOOK:
                return BookContract.BookEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        return bookDatabaseHelper.getReadableDatabase().query(
                BookContract.BookEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        Uri returnUri;

        long id = bookDatabaseHelper.getWritableDatabase().insert(
                BookContract.BookEntry.TABLE_NAME,
                null,
                values);

        if(id > 0) {
            returnUri = BookContract.BookEntry.buildUri(id);
        } else {
            returnUri = null;
        }

        context.getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        switch (uriMatcher.match(uri)) {
            case SINGLE_BOOK:
                bookDatabaseHelper.getWritableDatabase().delete(
                        BookContract.BookEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            case MULT_BOOKS:
                bookDatabaseHelper.getWritableDatabase().delete(
                        BookContract.BookEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // Not required
        return 0;
    }

    /**
     * Builds and returns a UriMatcher object
     * @return a UriMatcher object
     */
    protected static UriMatcher buildUriMatcher() {
        final UriMatcher matcher =
                new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(BookContract.CONTENT_AUTHORITY,
                BookContract.BookEntry.TABLE_NAME,
                MULT_BOOKS);
        matcher.addURI(BookContract.CONTENT_AUTHORITY,
                BookContract.BookEntry.TABLE_NAME + "/#",
                SINGLE_BOOK);
        return matcher;
    }

}
