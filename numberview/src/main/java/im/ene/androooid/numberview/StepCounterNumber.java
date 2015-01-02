package im.ene.androooid.numberview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * Created by eneim on 12/19/14.
 */
public class StepCounterNumber extends LinearLayout {
    public StepCounterNumber(Context context) {
        super(context);
        init(null);
    }

    public StepCounterNumber(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public StepCounterNumber(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StepCounterNumber(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private int digits = 1;

    private NumberView[] mDigits;

    private void init(AttributeSet attrs) {
        inflate(getContext(), R.layout.widget_step_numbers, this);

        if (attrs == null)
            return;

        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.StepCounter);

        try {
            digits = ta.getInteger(R.styleable.StepCounter_digits, 1);
        } finally {
            ta.recycle();
        }

        Log.d("digits", digits + "");

        mDigits = new NumberView[digits];
        for (int i = 0; i < digits; i++) {
            mDigits[i] = new NumberView(getContext(), attrs);
            addView(mDigits[i]);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = getMeasuredHeight();

        Log.d("step counter height", height + "");

//        for (int i = 0; i < mDigits.length; i++) {
//            mDigits[i].setSize(height);
//        }
    }
}
