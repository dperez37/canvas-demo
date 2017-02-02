package com.livefront.canvasapp.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.livefront.canvasapp.R;
import com.livefront.canvasapp.util.BlurUtil;
import com.livefront.canvasapp.util.MotionEventUtil;

public class BlurCanvas extends View {

    private static final int DEFAULT_BLUR_SIZE = 20;
    private static final int DEFAULT_BLUR_RADIUS = 25;

    private Bitmap mBitmap;
    private Bitmap mDrawBitmap;
    private Paint mPaint;
    private Rect mRect;
    private int paddingWidth = 0;
    private int paddingHeight = 0;
    private int mBlurSize = DEFAULT_BLUR_SIZE;
    private int mBlurRadius = DEFAULT_BLUR_RADIUS;

    public BlurCanvas(Context context) {
        super(context);
        init(null, 0, 0);
    }

    public BlurCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public BlurCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BlurCanvas(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    private void init(@Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(4f);

        if (attrs == null) {
            return;
        }

        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BlurCanvas,
                defStyleAttr,
                defStyleRes);

        BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(R.styleable.BlurCanvas_image);
        if (drawable != null) {
            mBitmap = drawable.getBitmap();
            mDrawBitmap = mBitmap.copy(mBitmap.getConfig(), true);
        }
        setBlurSize(a.getInt(R.styleable.BlurCanvas_blurSize, DEFAULT_BLUR_SIZE));
        setBlurRadius(a.getInt(R.styleable.BlurCanvas_blurRadius, DEFAULT_BLUR_RADIUS));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        paddingWidth = (w - mBitmap.getWidth()) / 2;
        paddingHeight = (h - mBitmap.getHeight()) / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                calculateRect(event);
                invalidate();
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
            default:
                return super.onTouchEvent(event);
        }
    }

    private void calculateRect(@NonNull MotionEvent event) {
        Point point = MotionEventUtil.eventToPoint(event);
        mRect = MotionEventUtil.sqRectFromPoint(
                point,
                mBlurSize,
                mBitmap.getWidth(),
                mBitmap.getHeight(),
                paddingWidth,
                paddingHeight);

        Point blurPoint = new Point(point.x - paddingWidth, point.y - paddingHeight);
        mDrawBitmap = BlurUtil.fastBlur(mDrawBitmap, blurPoint, mBlurSize, mBlurRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mRect == null) {
            canvas.drawBitmap(mBitmap, paddingWidth, paddingHeight, null);
            return;
        }

        canvas.drawBitmap(mDrawBitmap, paddingWidth, paddingHeight, null);
        canvas.drawRect(mRect, mPaint);
    }

    public void reset() {
        mDrawBitmap = mBitmap.copy(mBitmap.getConfig(), true);
        mRect = null;
        invalidate();
    }

    public int getBlurSize() {
        return mBlurSize;
    }

    public void setBlurSize(int blurSize) {
        mBlurSize = blurSize;
    }

    public int getBlurRadius() {
        return mBlurRadius;
    }

    public void setBlurRadius(int blurRadius) {
        mBlurRadius = blurRadius;
    }
}
