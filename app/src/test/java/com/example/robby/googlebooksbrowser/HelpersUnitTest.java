package com.example.robby.googlebooksbrowser;

import android.content.Context;

import com.example.robby.googlebooksbrowser.utils.Helpers;

import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.*;

public class HelpersUnitTest {

    @Test
    public void empty_reader_toString() throws Exception {
        assertEquals("", Helpers.convertReaderToString(new StringReader("")));
    }

    @Test
    public void nonEmpty_reader_toString() throws Exception {
        assertEquals("abcde", Helpers.convertReaderToString(new StringReader("abcde")));
    }
}
