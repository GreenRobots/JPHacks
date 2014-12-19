package im.ene.androooid.jphacks;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.nineoldandroids.animation.ObjectAnimator;

import im.ene.androooid.timelytextview.TimelyView;

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

    private TimelyView mTimelyStepCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_improved);

        getSupportActionBar().setElevation(1);

        mTimelyStepCount = (TimelyView) findViewById(R.id.text_stepcount);

        mTimelyStepCount.bringToFront();
        (findViewById(R.id.image_avatar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                from = position ++ % 10;

                to = 6;

                if(from != NO_VALUE && to != NO_VALUE) {
                    objectAnimator = mTimelyStepCount.animate(from, to);
                    objectAnimator.setDuration(DURATION);
                } else {
                    objectAnimator = null;
                }
            }
        });
    }
}
