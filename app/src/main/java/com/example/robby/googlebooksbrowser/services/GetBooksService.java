package com.example.robby.googlebooksbrowser.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.example.robby.googlebooksbrowser.utils.Constants;
import com.example.robby.googlebooksbrowser.utils.Helpers;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetBooksService extends IntentService {

    public GetBooksService() {
        super("GetBooksService");
    }

    /**
     * Receives the Intent object and dispatches a network request to make search for books
     * accordingly
     * @param intent the Intent containing the search parameters
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Messenger messenger = (Messenger) intent.getExtras().get("messenger");

            JSONObject result;
            if(intent.getBooleanExtra("search_by_title", true)) {
                result = searchBooksByTitle(intent.getStringExtra("search_text"));
            } else {
                result = searchBooksByIsbn(intent.getStringExtra("search_text"));
            }

            messenger.send(makeSuccessMessage(result));
        } catch (IOException | JSONException | RemoteException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Searches the Google Books API for books matching the specified ISBN
     * @param title the title to search books for
     * @return a JSON object containing the search results
     * @throws IOException if there was an error making the request
     * @throws JSONException if there was an error parsing the JSON response
     */
    private JSONObject searchBooksByTitle(String title) throws IOException, JSONException {
        HttpURLConnection conn = (HttpURLConnection) (new URL(Constants.BASE_URL + "?q=" + title + "&maxResults=40")).openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        InputStream inputStream = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String result = Helpers.convertReaderToString(reader);
        reader.close();
        inputStream.close();

        conn.disconnect();

        return new JSONObject(result);
    }

    /**
     * Searches the Google Books API for books matching the specified ISBN
     * @param isbn the ISBN to search books for
     * @return a JSON object containing the search results
     * @throws IOException if there was an error making the request
     * @throws JSONException if there was an error parsing the JSON response
     */
    private JSONObject searchBooksByIsbn(String isbn) throws IOException, JSONException {
        HttpURLConnection conn = (HttpURLConnection) (new URL(Constants.BASE_URL + "?q=isbn:" + isbn + "&maxResults=40")).openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        InputStream inputStream = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String result = Helpers.convertReaderToString(reader);
        reader.close();
        inputStream.close();

        conn.disconnect();

        return new JSONObject(result);
    }

    /**
     * Creates and returns a Message object containing a JSON
     * @param result the JSON object to add to the Message payload
     * @return a Message containing the JSON object
     */
    private Message makeSuccessMessage(JSONObject result) {
        Message message = Message.obtain();
        message.obj = result;
        return message;
    }
}
