package im.ene.androooid.jphacks.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by eneim on 12/16/14.
 */
public class SquareLinearLayoutByWidth extends LinearLayout {
    public SquareLinearLayoutByWidth(Context context) {
        super(context);
    }

    public SquareLinearLayoutByWidth(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareLinearLayoutByWidth(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SquareLinearLayoutByWidth(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }

    @Override
    public void requestLayout() {
        forceLayout();
    }

}
