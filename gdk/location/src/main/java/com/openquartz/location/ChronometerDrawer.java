package com.openquartz.location;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.View;

public class ChronometerDrawer implements SurfaceHolder.Callback {
    private final ChronometerView mChronometerView;
    private SurfaceHolder mHolder;

    public ChronometerDrawer(final Context context) {

        this.mChronometerView = new ChronometerView(context);
        this.mChronometerView.setListener(new ChronometerView.ChangeListener() {
            /*
             * (non-Javadoc)
             * @see com.google.android.glass.sample.stopwatch.ChronometerView.
             * ChangeListener#onChange()
             */
            @Override
            public void onChange() {
                ChronometerDrawer.this
                        .draw(ChronometerDrawer.this.mChronometerView);
            }
        });
        this.mChronometerView.setForceStart(true);
    }

    /**
     * Draws the view in the SurfaceHolder's canvas.
     */
    private void draw(final View view) {
        Canvas canvas;
        try {
            canvas = this.mHolder.lockCanvas();
        } catch (final Exception e) {
            return;
        }

        if (canvas != null) {
            view.draw(canvas);
            this.mHolder.unlockCanvasAndPost(canvas);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder
     * , int, int, int)
     */
    @Override
    public void surfaceChanged(final SurfaceHolder holder, final int format,
            final int width, final int height) {
        final int measuredWidth = View.MeasureSpec.makeMeasureSpec(width,
                View.MeasureSpec.EXACTLY);
        final int measuredHeight = View.MeasureSpec.makeMeasureSpec(height,
                View.MeasureSpec.EXACTLY);

        this.mChronometerView.measure(measuredWidth, measuredHeight);
        this.mChronometerView.layout(0, 0,
                this.mChronometerView.getMeasuredWidth(),
                this.mChronometerView.getMeasuredHeight());
    }

    /*
     * (non-Javadoc)
     * @see
     * android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder
     * )
     */
    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        this.mHolder = holder;
        this.mChronometerView.start();
    }

    /*
     * (non-Javadoc)
     * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.
     * SurfaceHolder)
     */
    @Override
    public void surfaceDestroyed(final SurfaceHolder holder) {
        this.mChronometerView.stop();
        this.mHolder = null;
    }
}
