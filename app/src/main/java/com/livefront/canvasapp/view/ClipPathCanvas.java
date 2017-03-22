package com.livefront.canvasapp.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.livefront.canvasapp.R;

public class ClipPathCanvas extends View {

    @ColorInt
    private static final int DEFAULT_STROKE_COLOR = Color.RED;
    @ColorInt
    private static final int DEFAULT_FILL_COLOR = Color.CYAN;

    @ColorInt
    private int mFillColor = DEFAULT_FILL_COLOR;
    private Path mPath = new Path();
    private Paint mStrokePaint;

    public ClipPathCanvas(Context context) {
        super(context);
        init(null, 0, 0);
    }

    public ClipPathCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public ClipPathCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClipPathCanvas(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    private void init(@Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mStrokePaint = new Paint();
        mStrokePaint.setAntiAlias(true);
        mStrokePaint.setStyle(Paint.Style.STROKE);

        if (attrs == null) {
            mStrokePaint.setColor(DEFAULT_STROKE_COLOR);
            return;
        }

        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ClipPathCanvas,
                defStyleAttr,
                defStyleRes);

        mFillColor = a.getColor(R.styleable.ClipPathCanvas_fillColor, DEFAULT_FILL_COLOR);
        mStrokePaint.setColor(a.getColor(R.styleable.ClipPathCanvas_strokeColor, DEFAULT_STROKE_COLOR));
        if (a.hasValue(R.styleable.ClipPathCanvas_strokeWidth)) {
            mStrokePaint.setStrokeWidth(a.getDimension(R.styleable.ClipPathCanvas_strokeWidth, 0));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                onActionDown(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                onActionMove(event);
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                onActionUp(event);
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

    private void onActionDown(@NonNull MotionEvent event) {
        mPath.moveTo(event.getX(), event.getY());
    }

    private void onActionMove(@NonNull MotionEvent event) {
        mPath.lineTo(event.getX(), event.getY());
        invalidate();
    }

    private void onActionUp(@NonNull MotionEvent event) {
        mPath.lineTo(event.getX(), event.getY());
        mPath.close();
        invalidate();
    }

    public void reset() {
        mPath.reset();
        invalidate();
    }
}
