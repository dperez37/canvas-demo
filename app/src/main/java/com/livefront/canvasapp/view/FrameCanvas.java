package com.livefront.canvasapp.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.livefront.canvasapp.R;
import com.livefront.canvasapp.util.MotionEventUtil;

public class FrameCanvas extends View {

    private static final int DEFAULT_FRAME_SIZE = 50;

    @DrawableRes
    private int mFrameId = R.drawable.frame_metal;
    private int mFrameSize = DEFAULT_FRAME_SIZE;

    private Paint mInnerPaint;
    private Paint mOuterPaint;
    private Point mStartPoint;
    private Point mEndPoint;
    private Rect mInnerRect;
    private Rect mOuterRect;

    private int mPaddingWidth = 0;
    private int mPaddingHeight = 0;

    private Bitmap mBitmap;
    private Bitmap mDrawBitmap;

    public FrameCanvas(Context context) {
        super(context);
        init(null, 0, 0);
    }

    public FrameCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public FrameCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FrameCanvas(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    private void init(@Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mInnerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setColor(Color.RED);
        mInnerPaint.setStrokeWidth(4f);

        mOuterPaint = new Paint(mInnerPaint);
        mOuterPaint.setColor(Color.GREEN);

        if (attrs == null) {
            return;
        }
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FrameCanvas,
                defStyleAttr,
                defStyleRes);

        BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(R.styleable.FrameCanvas_image);
        if (drawable != null) {
            mBitmap = drawable.getBitmap();
            mDrawBitmap = mBitmap.copy(mBitmap.getConfig(), true);
        }
        setFrameSize(a.getInteger(R.styleable.FrameCanvas_frameSize, DEFAULT_FRAME_SIZE));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mDrawBitmap, mPaddingWidth, mPaddingHeight, null);

        if (mOuterRect != null && mInnerRect != null) {
            canvas.drawRect(mOuterRect, mOuterPaint);
            canvas.drawRect(mInnerRect, mInnerPaint);
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
                mStartPoint = MotionEventUtil.eventToPoint(event);
                mEndPoint = null;
                return true;
            case MotionEvent.ACTION_MOVE:
                mEndPoint = MotionEventUtil.eventToPoint(event);
                calculateRect();
                invalidate();
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mEndPoint = MotionEventUtil.eventToPoint(event);
                calculateRect();
                calculateFrame();
                invalidate();
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    private void calculateRect() {
        if (mStartPoint == null || mEndPoint == null) {
            return;
        }
        MotionEventUtil.MultiRect multiRect = MotionEventUtil.getMultiRect(
                mStartPoint,
                mEndPoint,
                mBitmap.getWidth(),
                mBitmap.getHeight(),
                mPaddingWidth,
                mPaddingHeight,
                mFrameSize);
        mInnerRect = multiRect.innerRect;
        mOuterRect = multiRect.outerRect;
        if (multiRect.innerRect.width() <= 0 || multiRect.innerRect.height() <= 0) {
            mInnerRect = null;
            mOuterRect = null;
        }
    }

    private void calculateFrame() {
        if (mOuterRect == null || mInnerRect == null) {
            return;
        }
        Bitmap frame = BitmapFactory.decodeResource(getResources(), mFrameId);
        frame = Bitmap.createScaledBitmap(frame, mOuterRect.width(), mOuterRect.height(), false);
        mDrawBitmap = mBitmap.copy(mBitmap.getConfig(), true);

        int[] innerPix = new int[mInnerRect.width() * mInnerRect.height()];
        mDrawBitmap.getPixels(
                innerPix,
                0,
                mInnerRect.width(),
                mInnerRect.left - mPaddingWidth,
                mInnerRect.top - mPaddingHeight,
                mInnerRect.width(),
                mInnerRect.height());

        int[] framePix = new int[frame.getWidth() * frame.getHeight()];
        frame.getPixels(
                framePix,
                0,
                frame.getWidth(),
                0,
                0,
                frame.getWidth(),
                frame.getHeight());

        mDrawBitmap.setPixels(
                framePix,
                0,
                mOuterRect.width(),
                mOuterRect.left - mPaddingWidth,
                mOuterRect.top - mPaddingHeight,
                mOuterRect.width(),
                mOuterRect.height());

        mDrawBitmap.setPixels(
                innerPix,
                0,
                mInnerRect.width(),
                mInnerRect.left - mPaddingWidth,
                mInnerRect.top - mPaddingHeight,
                mInnerRect.width(),
                mInnerRect.height());
    }

    public void reset() {
        mDrawBitmap = mBitmap.copy(mBitmap.getConfig(), true);
        mStartPoint = null;
        mEndPoint = null;
        mInnerRect = null;
        mOuterRect = null;
        invalidate();
    }

    public void setFrameId(@DrawableRes int frameId) {
        if (mFrameId == frameId) {
            return;
        }
        mFrameId = frameId;
        calculateFrame();
        invalidate();
    }

    public int getFrameSize() {
        return mFrameSize;
    }

    public void setFrameSize(int frameSize) {
        if (mFrameSize == frameSize) {
            return;
        }
        mFrameSize = frameSize;
        calculateRect();
        calculateFrame();
        invalidate();
    }
}
