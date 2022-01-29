package com.example.robby.googlebooksbrowser.providers;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public final class BookContract {

    public static final String CONTENT_AUTHORITY = "bikramjeet.mooc.bookprovider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class BookEntry implements BaseColumns {

        public static final String TABLE_NAME = "books_table";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI
                .buildUpon()
                .appendPath(TABLE_NAME)
                .build();
        public static final String CONTENT_ITEM_TYPE = "bikramjeet.android.cursor.item/"
                + CONTENT_AUTHORITY
                + "/"
                + TABLE_NAME;
        public static final String CONTENT_ITEMS_TYPE = "bikramjeet.android.cursor.dir/"
                + CONTENT_AUTHORITY
                + "/"
                + TABLE_NAME;

        public static final String[] columns = new String[] {
            BookEntry._ID,
            BookEntry._BOOKID,
            BookEntry._TITLE,
            BookEntry._AUTHOR,
            BookEntry._PUBLISHER,
            BookEntry._DESCRIPTION,
        };

        public static final String _BOOKID = "_bookid";
        public static final String _TITLE = "_title";
        public static final String _AUTHOR = "_author";
        public static final String _PUBLISHER = "_publisher";
        public static final String _DESCRIPTION = "_description";

        public static Uri buildUri(Long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

}
