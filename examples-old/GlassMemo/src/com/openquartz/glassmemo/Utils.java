/**
 * OpenQuartz Memo for Glass
 * Github - https://github.com/jaredsburrows/OpenQuartz
 * @author Andre Compagno
 * 
 * Copyright (C) 2013 OpenQuartz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.openquartz.glassmemo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.android.glass.widget.CardScrollView;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Utils 
{
	public static CardScrollView cardScroll;

	/**
	 * Checks if there exists an object in shared preferences with the chosen key
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean checkForObjectInSharedPrefs(final Context context, final String key)
	{
		return  PreferenceManager.getDefaultSharedPreferences(context).contains(key);
	}

	public static void commitNewMemoList(final Context context, final String key, final List<String> memoList) 
	{
		SharedPreferences.Editor sharedPrefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();

		if (! memoList.isEmpty()) 
		{
			sharedPrefEditor.putString(key, new JSONArray(memoList).toString());
		} 
		else 
		{
			sharedPrefEditor.putString(key, null);
		}

		sharedPrefEditor.commit();
	}
	/**
	 * Gets the arraylist from shared preferences 
	 * @param context
	 * @param key
	 * @return
	 */
	public static ArrayList<String> getStringArrayPref(final Context context, String key) 
	{

		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		String json = sharedPref.getString(key, null);
		ArrayList<String> memoList = new ArrayList<String>();

		if (json != null) 
		{
			try 
			{
				JSONArray a = new JSONArray(json);

				for (int i = 0; i < a.length(); i++) 
				{
					memoList.add(a.optString(i));
				}
			} catch (JSONException e) { }
		}

		return memoList;
	}

	/**
	 * Deletes an item from the list stored in shared preferences 
	 * @param index
	 * @param context
	 * @param key
	 */
	public static void deleteMemoAtIndex(int index , Context context , String key)
	{
		ArrayList<String> memoList = new ArrayList<String>(getStringArrayPref(context , key));
		memoList.remove(index);
		commitNewMemoList(context , key , memoList);
	}

}