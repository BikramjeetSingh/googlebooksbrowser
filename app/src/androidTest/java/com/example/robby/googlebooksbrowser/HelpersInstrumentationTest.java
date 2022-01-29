package com.example.robby.googlebooksbrowser;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.example.robby.googlebooksbrowser.utils.Helpers;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HelpersInstrumentationTest {

    @Test
    public void network_available() {
        Context appContext = InstrumentationRegistry.getContext();

        assertTrue(Helpers.isNetworkAvailable(appContext));
    }
}
