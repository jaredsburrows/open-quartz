package com.openquartz.location;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.View;

public class ChronometerDrawer implements SurfaceHolder.Callback {
    private final ChronometerView mChronometerView;
    private SurfaceHolder mHolder;

    public ChronometerDrawer(Context context) {
        mChronometerView = new ChronometerView(context);
        mChronometerView.setListener(new ChronometerView.ChangeListener() {
            @Override
            public void onChange() {
                draw(mChronometerView);
            }
        });
        mChronometerView.setForceStart(true);
    }

    /**
     * Draws the view in the SurfaceHolder's canvas.
     */
    private void draw(View view) {
        Canvas canvas;
        try {
            canvas = mHolder.lockCanvas();
        } catch (Exception e) {
            return;
        }

        if (canvas != null) {
            view.draw(canvas);
            mHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        final int measuredWidth = View.MeasureSpec.makeMeasureSpec(width,
                View.MeasureSpec.EXACTLY);
        final int measuredHeight = View.MeasureSpec.makeMeasureSpec(height,
                View.MeasureSpec.EXACTLY);

        mChronometerView.measure(measuredWidth, measuredHeight);
        mChronometerView.layout(0, 0,
                mChronometerView.getMeasuredWidth(),
                mChronometerView.getMeasuredHeight());
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mHolder = holder;
        mChronometerView.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mChronometerView.stop();
        mHolder = null;
    }
}
