package com.example.robby.googlebooksbrowser.models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.robby.googlebooksbrowser.providers.BookContract;

public class Book implements Parcelable {

    private String id;
    private String title;
    private String author;
    private String publisher;
    private String description;

    public Book(){

    }

    public Book(String id, String title, String author, String publisher, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
    }

    public Book(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(BookContract.BookEntry._BOOKID));
        title = cursor.getString(cursor.getColumnIndex(BookContract.BookEntry._TITLE));
        author = cursor.getString(cursor.getColumnIndex(BookContract.BookEntry._AUTHOR));
        publisher = cursor.getString(cursor.getColumnIndex(BookContract.BookEntry._PUBLISHER));
        description = cursor.getString(cursor.getColumnIndex(BookContract.BookEntry._DESCRIPTION));
    }

    protected Book(Parcel in) {
        id = in.readString();
        title = in.readString();
        author = in.readString();
        publisher = in.readString();
        description = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(publisher);
        dest.writeString(description);
    }
}
