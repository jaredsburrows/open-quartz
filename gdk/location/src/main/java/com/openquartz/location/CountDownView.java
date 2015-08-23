package com.openquartz.location;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class CountDownView extends FrameLayout {
    /**
     * Interface to listen for changes in the countdown.
     */
    public interface CountDownListener {
        /**
         * Notified when the countdown is finished.
         */
        public void onFinish();

        /**
         * Notified of a tick, indicating a layout change.
         */
        public void onTick(long millisUntilFinish);
    }

    private static final float ALPHA_DELIMITER = 0.95f;

    /** Time delimiter specifying when the second component is fully shown. */
    public static final float ANIMATION_DURATION_IN_MILLIS = 850.0f;
    // About 24 FPS.
    private static final long DELAY_MILLIS = 41;
    private static final int MAX_TRANSLATION_Y = 30;
    private static final long SEC_TO_MILLIS = TimeUnit.SECONDS.toMillis(1);

    private final Handler mHandler = new Handler();

    private CountDownListener mListener;
    private  TextView mSecondsView;
    private boolean mStarted;
    private long mStopTimeInFuture;

    private long mTimeSeconds;

    private final Runnable mUpdateViewRunnable = new Runnable() {
        @Override
        public void run() {
            final long millisLeft = CountDownView.this.mStopTimeInFuture
                    - SystemClock.elapsedRealtime();

            // Count down is done.
            if (millisLeft <= 0) {
                CountDownView.this.mStarted = false;
                if (CountDownView.this.mListener != null) {
                    CountDownView.this.mListener.onFinish();
                }
            } else {
                CountDownView.this.updateView(millisLeft);
                if (CountDownView.this.mListener != null) {
                    CountDownView.this.mListener.onTick(millisLeft);
                }
                CountDownView.this.mHandler.postDelayed(
                        CountDownView.this.mUpdateViewRunnable,
                        CountDownView.DELAY_MILLIS);
            }
        }
    };

    public CountDownView(final Context context) {
        this(context, null, 0);
    }

    public CountDownView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownView(final Context context, final AttributeSet attrs,
            final int style) {
        super(context, attrs, style);
//        LayoutInflater.from(context).inflate(R.layout.card_countdown, this);

//        this.mSecondsView = (TextView) this.findViewById(R.id.seconds_view);
    }

    public long getCountDown() {
        return this.mTimeSeconds;
    }

    public void setCountDown(final long timeSeconds) {
        this.mTimeSeconds = timeSeconds;
    }

    /**
     * Set a {@link CountDownListener}.
     */
    public void setListener(final CountDownListener listener) {
        this.mListener = listener;
    }

    /**
     * Starts the countdown animation if not yet started.
     */
    public void start() {
        if (!this.mStarted) {
            this.mStopTimeInFuture = TimeUnit.SECONDS
                    .toMillis(this.mTimeSeconds)
                    + SystemClock.elapsedRealtime();
            this.mStarted = true;
            this.mHandler.postDelayed(this.mUpdateViewRunnable,
                    CountDownView.DELAY_MILLIS);
        }
    }

    /**
     * Updates the views to reflect the current state of animation.
     *
     * @params millisUntilFinish milliseconds until the countdown is done
     */
    private void updateView(final long millisUntilFinish) {
//        final long currentTimeSeconds = TimeUnit.MILLISECONDS
//                .toSeconds(millisUntilFinish) + 1;
//        final long frame = CountDownView.SEC_TO_MILLIS
//                - (millisUntilFinish % CountDownView.SEC_TO_MILLIS);
//
//        this.mSecondsView.setText(Long.toString(currentTimeSeconds));
//        if (frame <= CountDownView.ANIMATION_DURATION_IN_MILLIS) {
//            final float factor = frame
//                    / CountDownView.ANIMATION_DURATION_IN_MILLIS;
//            this.mSecondsView.setAlpha(factor * CountDownView.ALPHA_DELIMITER);
//            this.mSecondsView.setTranslationY(CountDownView.MAX_TRANSLATION_Y
//                    * (1 - factor));
//        } else {
//            final float factor = (frame - CountDownView.ANIMATION_DURATION_IN_MILLIS)
//                    / CountDownView.ANIMATION_DURATION_IN_MILLIS;
//            this.mSecondsView.setAlpha(CountDownView.ALPHA_DELIMITER
//                    + (factor * (1 - CountDownView.ALPHA_DELIMITER)));
//        }
    }

}
