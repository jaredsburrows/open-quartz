package com.openquartz.launchywrapper;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;


public class LaunchyWrapper extends Service 
{
	/*
	 * (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) 
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) 
	{
		/* Start Launchy */
		this.startNewActivity("com.mikedg.android.glass.launchy");

		/* Equivalent to this.finish() from Activity */
		this.stopSelf();

		return START_STICKY;
	}

	/**
	 * http://stackoverflow.com/questions/3872063/android-launch-an-application-from-another-application/15465797#15465797
	 */
	public void startNewActivity(String packageName)
	{
		if (packageName != null)
		{
			Intent intent = this.getPackageManager().getLaunchIntentForPackage(packageName);
			if (intent != null)
			{
				// we found the activity
				// now start the activity
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
			else
			{
				Toast.makeText(getBaseContext(), "Not Installed", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
