package com.example.robby.googlebooksbrowser.services;

import android.os.Handler;
import android.os.Message;

import com.example.robby.googlebooksbrowser.interfaces.Lister;
import com.example.robby.googlebooksbrowser.models.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DownloadHandler extends Handler {

    private Lister lister;

    public DownloadHandler(Lister lister) {
        this.lister = lister;
    }

    /**
     * Appropriately handles the message received from the Service
     */
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        try {
            lister.showResults(convertJsonIntoJava((JSONObject) msg.obj));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts the JSON Object retrieved from the Google Books API into an ArrayList of
     * Java objects
     * @param jsonObject the JSON object to parse
     * @return an ArrayList of Java objects
     * @throws JSONException if there was an error parsing the JSON response
     */
    private ArrayList<Book> convertJsonIntoJava(JSONObject jsonObject) throws JSONException {
        ArrayList<Book> booksArrayList = new ArrayList<>();
        if(jsonObject.getInt("totalItems") > 0) {
            JSONArray booksJsonArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < booksJsonArray.length(); i++) {
                JSONObject bookJsonObject = booksJsonArray.getJSONObject(i);
                Book book = new Book();
                book.setId(bookJsonObject.getString("id"));
                book.setTitle(bookJsonObject.getJSONObject("volumeInfo").getString("title"));
                book.setAuthor(safelyGetStringArrayFromJson(bookJsonObject.getJSONObject("volumeInfo"), "authors"));
                book.setDescription(safelyGetStringFromJson(bookJsonObject.getJSONObject("volumeInfo"), "description"));
                book.setPublisher(safelyGetStringFromJson(bookJsonObject.getJSONObject("volumeInfo"), "publisher"));
                booksArrayList.add(book);
            }
        }
        return booksArrayList;
    }

    /**
     * Gets a String from a JSON object. If not found, returns a generic error String
     * @param jsonObject the JSON object from which to retrieve the String
     * @param name the name of the String to find
     * @return the value of the String as present in the JSON Object
     * @throws JSONException if there was an error parsing the JSON response
     */
    private String safelyGetStringFromJson(JSONObject jsonObject, String name) throws JSONException {
        if(jsonObject.has(name)) {
            return jsonObject.getString(name);
        } else {
            return "No " + name + " available";
        }
    }

    /**
     * Gets an array of Strings from a JSON object, and joins them into a single String. If not
     * found, returns a generic error String
     * @param jsonObject the JSON object from which to retrieve the String array
     * @param name the name of the String arry to find
     * @return a String consisting of a concatenation of all Strings in the specified array
     * @throws JSONException if there was an error parsing the JSON response
     */
    private String safelyGetStringArrayFromJson(JSONObject jsonObject, String name) throws JSONException {
        if(jsonObject.has(name)) {
            StringBuilder builder = new StringBuilder();
            JSONArray jsonArray = jsonObject.getJSONArray(name);
            for(int i = 0; i < jsonArray.length(); i++) {
                builder.append(jsonArray.getString(i));
                if(i != jsonArray.length() - 1) {
                    builder.append(", ");
                }
            }
            return builder.toString();
        } else {
            return "No " + name + " available";
        }
    }
}
