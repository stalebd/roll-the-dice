package com.example.projectdice;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HistoryManager {
    public static ArrayList<String> historyList = new ArrayList<>();
    private static final String PREF_NAME = "DicePrefs";
    private static final String KEY_HISTORY = "history_list";

    // Loads history from SharedPreferences
    public static void loadHistory(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String jsonString = prefs.getString(KEY_HISTORY, null);

        historyList.clear();
        if (jsonString != null) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    historyList.add(jsonArray.getString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // Saves the current list to the phones storage
    public static void saveHistory(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        JSONArray jsonArray = new JSONArray(historyList);
        editor.putString(KEY_HISTORY, jsonArray.toString());
        editor.apply();
    }

    // Adds a new result
    public static void addResult(Context context, String resultText) {

        String currentTime = new SimpleDateFormat("dd.MM | HH:mm", Locale.getDefault()).format(new Date());
        String fullEntry = currentTime + " | " + resultText;
        historyList.add(0, fullEntry);

        saveHistory(context);
    }

    // Clears history
    public static void clearHistory(Context context) {
        historyList.clear();
        saveHistory(context);
    }
}