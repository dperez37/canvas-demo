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
import com.livefront.canvasapp.util.MotionEventUtil;

public class PixelMoverCanvas extends View {

    private static final int SIZE_PX = 800;

    private Paint mPaint;
    private Bitmap mBitmap;
    private Bitmap mDrawBitmap;

    private Rect mOriginalRect;
    private int[] mPix;
    private Rect mIntermediateRect;
    private int[] mIntermediatePix;

    private int mPaddingWidth = 0;
    private int mPaddingHeight = 0;

    private int mSectionSize = SIZE_PX;

    public PixelMoverCanvas(Context context) {
        super(context);
        init(null, 0, 0);
    }

    public PixelMoverCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public PixelMoverCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PixelMoverCanvas(Context context, AttributeSet attrs,
                            int defStyleAttr, int defStyleRes) {
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
                R.styleable.PixelMoverCanvas,
                defStyleAttr,
                defStyleRes);

        BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(
                R.styleable.PixelMoverCanvas_image);
        mSectionSize = a.getDimensionPixelSize(R.styleable.PixelMoverCanvas_sectionSize, SIZE_PX);
        if (drawable != null) {
            mBitmap = drawable.getBitmap();
            mDrawBitmap = mBitmap.copy(mBitmap.getConfig(), true);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mDrawBitmap, mPaddingWidth, mPaddingHeight, null);

        if (mOriginalRect != null) {
            canvas.drawRect(mOriginalRect, mPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPaddingWidth = (w - mBitmap.getWidth()) / 2;
        mPaddingHeight = (h - mBitmap.getHeight()) / 2;
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

    private void onActionDown(@NonNull MotionEvent event) {
        Point point = MotionEventUtil.eventToPoint(event);
        mOriginalRect = MotionEventUtil.sqRectFromPoint(
                point,
                mSectionSize,
                mBitmap.getWidth(),
                mBitmap.getHeight(),
                mPaddingWidth,
                mPaddingHeight);

        mPix = new int[mOriginalRect.width() * mOriginalRect.height()];
        mDrawBitmap.getPixels(
                mPix,
                0,
                mOriginalRect.width(),
                mOriginalRect.left - mPaddingWidth,
                mOriginalRect.top - mPaddingHeight,
                mOriginalRect.width(),
                mOriginalRect.height());

        invalidate();
    }

    private void onActionMove(@NonNull MotionEvent event) {
        // Reset intermediate rect pixels
        if (mIntermediatePix != null) {
            mDrawBitmap.setPixels(
                    mIntermediatePix,
                    0,
                    mIntermediateRect.width(),
                    mIntermediateRect.left - mPaddingWidth,
                    mIntermediateRect.top - mPaddingHeight,
                    mIntermediateRect.width(),
                    mIntermediateRect.height());
        }

        Point point = MotionEventUtil.eventToPoint(event);
        mIntermediateRect = MotionEventUtil.sqRectFromPoint(
                point,
                mSectionSize,
                mBitmap.getWidth(),
                mBitmap.getHeight(),
                mPaddingWidth,
                mPaddingHeight);

        mIntermediatePix = new int[mIntermediateRect.width() * mIntermediateRect.height()];
        mDrawBitmap.getPixels(
                mIntermediatePix,
                0,
                mIntermediateRect.width(),
                mIntermediateRect.left - mPaddingWidth,
                mIntermediateRect.top - mPaddingHeight,
                mIntermediateRect.width(),
                mIntermediateRect.height());

        mDrawBitmap.setPixels(
                mPix,
                0,
                mIntermediateRect.width(),
                mIntermediateRect.left - mPaddingWidth,
                mIntermediateRect.top - mPaddingHeight,
                mIntermediateRect.width(),
                mIntermediateRect.height());
        invalidate();
    }

    private void onActionUp(@NonNull MotionEvent event) {
        // Reset intermediate rect pixels
        mDrawBitmap.setPixels(
                mIntermediatePix,
                0,
                mIntermediateRect.width(),
                mIntermediateRect.left - mPaddingWidth,
                mIntermediateRect.top - mPaddingHeight,
                mIntermediateRect.width(),
                mIntermediateRect.height());

        Point point = MotionEventUtil.eventToPoint(event);
        Rect rect = MotionEventUtil.sqRectFromPoint(
                point,
                mSectionSize,
                mBitmap.getWidth(),
                mBitmap.getHeight(),
                mPaddingWidth,
                mPaddingHeight);

        mDrawBitmap.setPixels(
                mPix,
                0,
                rect.width(),
                rect.left - mPaddingWidth,
                rect.top - mPaddingHeight,
                rect.width(),
                rect.height());

        mIntermediateRect = null;
        mIntermediatePix = null;
        mPix = null;

        invalidate();
    }

    public void reset() {
        mDrawBitmap = mBitmap.copy(mBitmap.getConfig(), true);
        mOriginalRect = null;
        mIntermediateRect = null;
        mIntermediatePix = null;
        mPix = null;
        invalidate();
    }

    public int getSectionSize() {
        return mSectionSize;
    }

    public void setSectionSize(int sectionSize) {
        mSectionSize = sectionSize;
    }

}
