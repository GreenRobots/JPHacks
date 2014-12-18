package im.ene.androooid.jphacks.watchface;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.Date;

import im.ene.androooid.jphacks.R;

/**
 * Created by Takahiko on 2014/12/18.
 */
public class DigitalWatchFaceService extends CanvasWatchFaceService {
    private static final String TAG = "DigitalWatchFaceService";

    /**
     * Update rate in milliseconds for normal (not ambient and not mute) mode. We update twice
     * a second to blink the colons.
     */
    private static final long NORMAL_UPDATE_RATE_MS = 100;

    @Override
    public Engine onCreateEngine() {
        /* provide your watch face implementation */
        return new Engine();
    }

    /* implement service callback methods */
    private class Engine extends CanvasWatchFaceService.Engine {

        private Movie mAvatarImage;

        private int mBackgroundColor;
        private int mPrimaryColor;

        private Paint mPaintPrimary;
        private Paint mPaintRevert;

        private Paint mPaintTextPrimary;
        private Paint mPaintTextRevert;

        // TODO set from dimen
        private float mClockTextSizeLarge = 80;
        private float mClockTextSizeSmall = 40;

        // TODO set from dimen
        private float mClockOffsetX = 50;
        private float mClockOffsetY = 300;

        private float mClockOffsetSmallX = 260;
        private float mClockOffsetSmallY = 300;

        // TODO set from dimen
        private float mClockRectWidth = 200;
        private float mClockRectHeight = 60;

        private Date mDate;

        /** Alpha value for drawing time when in mute mode. */
        static final int MUTE_ALPHA = 100;

        /** Alpha value for drawing time when not in mute mode. */
        static final int NORMAL_ALPHA = 255;

        static final int MSG_UPDATE_TIME = 0;

        /** How often {@link #mUpdateTimeHandler} ticks in milliseconds. */
        long mInteractiveUpdateRateMs = NORMAL_UPDATE_RATE_MS;

        /** Handler to update the time periodically in interactive mode. */
        final Handler mUpdateTimeHandler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case MSG_UPDATE_TIME:
                        if (Log.isLoggable(TAG, Log.VERBOSE)) {
                            Log.v(TAG, "updating time");
                        }
                        invalidate();
                        if (shouldTimerBeRunning()) {
                            long timeMs = System.currentTimeMillis();
                            long delayMs =
                                    mInteractiveUpdateRateMs - (timeMs % mInteractiveUpdateRateMs);
                            mUpdateTimeHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, delayMs);
                        }
                        break;
                }
            }
        };

        @Override
        public void onCreate(SurfaceHolder holder) {
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "onCreate");
            }
            super.onCreate(holder);

            mBackgroundColor = Color.parseColor("#4db6ac");
            mPrimaryColor = Color.WHITE;

            mPaintPrimary = new Paint();
            mPaintPrimary.setColor(mPrimaryColor);
            mPaintTextPrimary = new Paint(mPaintPrimary);
            mPaintTextPrimary.setTextSize(mClockTextSizeSmall);
            mPaintRevert = new Paint();
            mPaintRevert.setColor(mBackgroundColor);
            mPaintTextRevert = new Paint(mPaintRevert);
            mPaintTextRevert.setTextSize(mClockTextSizeLarge);

            mAvatarImage = getResources().getMovie(R.raw.androidify_normal_eat);

            mDate = new Date();
        }

        @Override
        public void onDestroy() {
            mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME);
            super.onDestroy();
        }

        @Override
        public void onPropertiesChanged(Bundle properties) {
            /* get device features (burn-in, low-bit ambient) */
        }

        @Override
        public void onTimeTick() {
            /* the time changed */
        }

        @Override
        public void onAmbientModeChanged(boolean inAmbientMode) {
            super.onAmbientModeChanged(inAmbientMode);
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "onAmbientModeChanged: " + inAmbientMode);
            }

            // Whether the timer should be running depends on whether we're in ambient mode (as well
            // as whether we're visible), so we may need to start or stop the timer.
            updateTimer();

            invalidate();
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            canvas.drawColor(isInAmbientMode() ? Color.BLACK: mBackgroundColor);

            // Draw Gif Animation
            if(!isInAmbientMode()) {
                int posInMilliSec = ((int)System.currentTimeMillis()) % mAvatarImage.duration();
                mAvatarImage.setTime(posInMilliSec);
                mAvatarImage.draw(canvas, 0, 0);
            }

            mDate.setTime(System.currentTimeMillis());

            if(!isInAmbientMode()) {
                // Draw hour and minute
                canvas.drawRect(mClockOffsetX,
                        mClockOffsetY - mClockRectHeight,
                        mClockOffsetX + mClockRectWidth,
                        mClockOffsetY + mClockRectHeight,
                        mPaintPrimary);
            }

            // i know it's deprecated. fix someday,,.
            int hours = mDate.getHours();
            int minutes = mDate.getMinutes();
            String timeStr = (hours<10 ? " " : "") + hours
                    + ":"
                    + (minutes<10 ? "0" : "") + minutes;
            canvas.drawText(timeStr, mClockOffsetX, mClockOffsetY, mPaintTextRevert);

            // Draw seconds
            if(!isInAmbientMode()) {
                int seconds = mDate.getSeconds();
                canvas.drawText((seconds<10? "0":"") + seconds,
                        mClockOffsetSmallX,
                        mClockOffsetSmallY, mPaintTextPrimary);
            }
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "onVisibilityChanged: " + visible);
            }

            // Whether the timer should be running depends on whether we're visible (as well as
            // whether we're in ambient mode), so we may need to start or stop the timer.
            updateTimer();

            invalidate();
        }

        /**
         * Returns whether the {@link #mUpdateTimeHandler} timer should be running. The timer should
         * only run when we're visible and in interactive mode.
         */
        private boolean shouldTimerBeRunning() {
            return isVisible() && !isInAmbientMode();
        }

        /**
         * Starts the {@link #mUpdateTimeHandler} timer if it should be running and isn't currently
         * or stops it if it shouldn't be running but currently is.
         */
        private void updateTimer() {
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "updateTimer");
            }
            mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME);
            if (shouldTimerBeRunning()) {
                mUpdateTimeHandler.sendEmptyMessage(MSG_UPDATE_TIME);
            }
        }
    }
}
