package com.example.robby.googlebooksbrowser;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.example.robby.googlebooksbrowser.models.Book;
import com.example.robby.googlebooksbrowser.providers.BooksOps;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DatabaseInstrumentationTest {

    BooksOps booksOps;
    Book dummyBook1, dummyBook2;

    @Before
    public void initialize_db() {
        Context appContext = InstrumentationRegistry.getContext();

        booksOps = new BooksOps(appContext.getContentResolver());
        booksOps.deleteAll();

        dummyBook1 = new Book("dummyid1", "dummytitle1", "dummyauthor1", "dummypublisher1", "dummydescription1");
        dummyBook2 = new Book("dummyid2", "dummytitle2", "dummyauthor2", "dummypublisher2", "dummydescription2");
    }

    @Test
    public void is_db_empty() {
        Assert.assertEquals(0, booksOps.queryAll().size());
    }

    @Test
    public void insert_one() {
        booksOps.insert(dummyBook1);
        Assert.assertEquals(1, booksOps.queryAll().size());
    }

    @Test
    public void delete_one() {
        booksOps.delete(dummyBook1);
        Assert.assertEquals(0, booksOps.queryAll().size());
    }

    @Test
    public void insert_mult() {
        booksOps.insert(dummyBook1);
        booksOps.insert(dummyBook2);
        Assert.assertEquals(2, booksOps.queryAll().size());
    }

    @Test
    public void delete_all() {
        booksOps.deleteAll();
        Assert.assertEquals(0, booksOps.queryAll().size());
    }
}
