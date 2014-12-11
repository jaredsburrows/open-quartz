package com.openquartz.location;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MenuActivity extends Activity {
    /*
     * (non-Javadoc)
     * @see android.app.ActiviOty#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        this.getMenuInflater().inflate(R.menu.stopwatch, menu);
        return true;
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.stop:
                this.stopService(new Intent(this, LocationService.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onOptionsMenuClosed(android.view.Menu)
     */
    @Override
    public void onOptionsMenuClosed(final Menu menu) {
        this.finish();
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    public void onResume() {
        super.onResume();
        this.openOptionsMenu();
    }
}
