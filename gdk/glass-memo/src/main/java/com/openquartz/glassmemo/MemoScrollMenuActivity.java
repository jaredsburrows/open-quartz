package com.openquartz.glassmemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MemoScrollMenuActivity extends Activity {
    private int scrollPosition = -1;
    private String memoText = "";
    private TextToSpeech readMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up the text to speech to read the memos
        readMemo = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) { /* Do nothing */ }
        });
        // grab the memo index and memo text from the intent
        scrollPosition = getIntent().getIntExtra("scrollposition", -1);
        memoText = getIntent().getStringExtra("memotext");
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        openOptionsMenu();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void openOptionsMenu() {
        super.openOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.scroll_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.read_memo_aloud:
                // make the glass read the memo out loud
                speakMemo(this, memoText);
                return true;
            case R.id.delete:
                // delete memo from shared preferences
                Utils.deleteMemoAtIndex(scrollPosition, this, getString(R.string.shared_memo_key));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        // close the memo
        finish();
    }

    public void speakMemo(Context context, String memo) {
        // Make the class speak the current memo
        // QUEUE_FLUSH => Gets rid of all queued items and sets the current on in the queue
        readMemo.speak(memo, TextToSpeech.QUEUE_FLUSH, null);
    }
}
