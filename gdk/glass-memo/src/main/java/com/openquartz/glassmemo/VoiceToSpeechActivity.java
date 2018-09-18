package com.openquartz.glassmemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import java.util.ArrayList;

public class VoiceToSpeechActivity extends Activity {
    private static final int SPEECH_REQUEST = 0;
    private String memoResult;
    private ArrayList<String> memoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        displaySpeechRecognizer();
    }

    private void displaySpeechRecognizer() {
        startActivityForResult(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH),
            SPEECH_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQUEST && resultCode == RESULT_OK) {
            // Get results of speech to text
            memoResult = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
            // get list of memos saved in shared prefs
            if (Utils.checkForObjectInSharedPrefs(this, getString(R.string.shared_memo_key))) {
                memoList = new ArrayList<>(
                    Utils.getStringArrayPref(this, getString(R.string.shared_memo_key)));
            } else {
                memoList = new ArrayList<>();
            }
            // add the new memo to the list
            memoList.add(memoResult);
            // save the new list
            Utils.commitNewMemoList(this, getString(R.string.shared_memo_key), memoList);
            // update the list in the live card
            stopService(new Intent(this, ViewMemoService.class));
            final Intent serviceIntent = new Intent(this, ViewMemoService.class);
            serviceIntent.putExtra("update", true);
            startService(serviceIntent);
        }
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
