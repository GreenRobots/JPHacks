package im.ene.androooid.jphacks;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;

import com.nineoldandroids.animation.ObjectAnimator;

import java.text.SimpleDateFormat;
import java.util.Date;

import im.ene.androooid.numberview.DigitalClockView;
import im.ene.androooid.numberview.font.DFont;

/**
 * Created by eneim on 12/17/14.
 */
public class NextMainActivity extends ActionBarActivity {

    public static final int            DURATION       = 1000;
    public static final int            NO_VALUE       = -1;

    private volatile ObjectAnimator objectAnimator = null;

    private volatile int from = NO_VALUE;
    private volatile int to   = NO_VALUE;

    private int position = 1;

//    private TimelyView mTimelyStepCount;

    public static final String EXTRA_MORPHING_DURATION = "morphing_duration";

//    private DigitalClockView mDigitalClockView;
//    private SystemClock mSystemClockManager;
//    private SimpleDateFormat mSimpleDateFormat;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_improved);

        getSupportActionBar().setElevation(1);

//        mDigitalClockView = (DigitalClockView) findViewById(R.id.text_heartbeat);
//        mDigitalClockView.setFont(new DFont(130, 10));

        int morphingDuration = getIntent().getIntExtra(EXTRA_MORPHING_DURATION, DigitalClockView.DEFAULT_MORPHING_DURATION);
//        mDigitalClockView.setMorphingDuration(morphingDuration);

//        mSimpleDateFormat = new SimpleDateFormat("hh:mm:ss");

        mHandler = new Handler();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                mDigitalClockView.setTime(mSimpleDateFormat.format(new Date()));

                mHandler.post(this);
            }
        }, 1000);

//        mTimelyStepCount = (TimelyView) findViewById(R.id.text_stepcount);

//        mTimelyStepCount.bringToFront();
//        (findViewById(R.id.image_avatar)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                from = position ++ % 10;
//
//                to = 6;
//
//                if(from != NO_VALUE && to != NO_VALUE) {
//                    objectAnimator = mTimelyStepCount.animate(from, to);
//                    objectAnimator.setDuration(DURATION);
//                } else {
//                    objectAnimator = null;
//                }
//            }
//        });
    }
}
