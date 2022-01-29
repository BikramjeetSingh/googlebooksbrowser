package com.example.robby.googlebooksbrowser.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class Helpers {

    /**
     * Reads the contents of a Reader object and appends it to a single string
     * @param reader the Reader object to read from
     * @return the contents of the Reader object as a single String
     * @throws IOException if the Reader could not be successfully read from
     */
    public static String convertReaderToString(Reader reader) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        StringBuilder builder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }

    /**
     * Checks whether the device is able to access the internet
     * @param context the context in which to check the connectivity
     * @return true if internet connectivity is available, else false
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
