package com.example.robby.googlebooksbrowser.interfaces;

import com.example.robby.googlebooksbrowser.models.Book;

import java.util.ArrayList;

/**
 * This interface represents a class that is able to represent a list of Books in an Activity
 */
public interface Lister {

    void showResults(ArrayList<Book> bookArrayList);
}
