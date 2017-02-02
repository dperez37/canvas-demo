package com.livefront.canvasapp.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Region;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.livefront.canvasapp.R;

public class ClipCirclesCanvas extends View {

    @ColorInt
    private static final int DEFAULT_FILL_COLOR = Color.CYAN;

    private static final float CIRCLE_RADIUS_PX = 40f;

    @ColorInt
    private int mFillColor = DEFAULT_FILL_COLOR;
    private Path mPath;

    public ClipCirclesCanvas(Context context) {
        super(context);
        init(null, 0, 0);
    }

    public ClipCirclesCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public ClipCirclesCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClipCirclesCanvas(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    private void init(@Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mPath = new Path();

        if (attrs == null) {
            return;
        }

        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ClipPathCanvas,
                defStyleAttr,
                defStyleRes);

        mFillColor = a.getColor(R.styleable.ClipPathCanvas_fillColor, DEFAULT_FILL_COLOR);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(), event.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                mPath.addCircle(event.getX(), event.getY(), CIRCLE_RADIUS_PX, Path.Direction.CW);
                invalidate();
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.clipPath(mPath, Region.Op.XOR);
        canvas.drawColor(mFillColor);
    }

    public void reset() {
        mPath.reset();
        invalidate();
    }

}
