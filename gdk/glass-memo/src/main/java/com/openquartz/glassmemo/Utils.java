package com.openquartz.glassmemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

public class Utils {
    /**
     * Checks if there exists an object in shared preferences with the chosen key
     */
    public static boolean checkForObjectInSharedPrefs(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).contains(key);
    }

    public static void commitNewMemoList(Context context, String key, List<String> memoList) {
        final SharedPreferences.Editor sharedPrefEditor =
            PreferenceManager.getDefaultSharedPreferences(context).edit();

        if (!memoList.isEmpty()) {
            sharedPrefEditor.putString(key, new JSONArray(memoList).toString());
        } else {
            sharedPrefEditor.putString(key, null);
        }

        sharedPrefEditor.apply();
    }

    /**
     * Gets the arraylist from shared preferences
     */
    public static ArrayList<String> getStringArrayPref(Context context, String key) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        final String json = sharedPref.getString(key, null);
        final ArrayList<String> memoList = new ArrayList<>();

        if (json != null) {
            try {
                final JSONArray a = new JSONArray(json);

                for (int i = 0; i < a.length(); i++) {
                    memoList.add(a.optString(i));
                }
            } catch (JSONException ignored) {
            }
        }

        return memoList;
    }

    /**
     * Deletes an item from the list stored in shared preferences
     */
    public static void deleteMemoAtIndex(int index, Context context, String key) {
        final ArrayList<String> memoList = new ArrayList<>(getStringArrayPref(context, key));
        memoList.remove(index);
        commitNewMemoList(context, key, memoList);
    }
}
