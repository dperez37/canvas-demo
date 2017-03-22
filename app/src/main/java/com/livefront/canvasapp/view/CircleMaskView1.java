package com.livefront.canvasapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.livefront.canvasapp.R;

public class CircleMaskView1 extends AppCompatImageView {

    @ColorInt
    private static final int DEFAULT_MASK_COLOR = Color.WHITE;

    private Paint mMaskPaint;
    private Path mCircleClipPath;

    public CircleMaskView1(Context context) {
        super(context);
        init(null, 0, 0);
    }

    public CircleMaskView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public CircleMaskView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    private void init(@Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        // We disable Hardware Acceleration because of a bug in Samsung devices
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mMaskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMaskPaint.setColor(DEFAULT_MASK_COLOR);
        mCircleClipPath = new Path();

        if (attrs == null) {
            return;
        }

        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CircleMaskView1,
                defStyleAttr,
                defStyleRes);
        setMaskColor(a.getColor(R.styleable.CircleMaskView1_maskColor, DEFAULT_MASK_COLOR));
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float measuredWidth = getMeasuredWidth();
        float measuredHeight = getMeasuredHeight();
        float centerX = measuredWidth / 2f;
        float centerY = measuredHeight / 2f;
        float radius = Math.min(centerY, centerX);

        mCircleClipPath.reset();
        mCircleClipPath.addCircle(centerX, centerY, radius, Path.Direction.CW);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.clipPath(mCircleClipPath, Region.Op.XOR);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mMaskPaint);
    }

    @ColorInt
    public int getMaskColor() {
        return mMaskPaint.getColor();
    }

    /**
     * Sets the mask color via a resource
     */
    public void setMaskColorRes(@ColorRes int color) {
        setMaskColor(ContextCompat.getColor(getContext(), color));
    }

    /**
     * Sets the mask color via a {@link ColorInt}
     */
    public void setMaskColor(@ColorInt int color) {
        if (mMaskPaint.getColor() == color) {
            return;
        }
        mMaskPaint.setColor(color);
        invalidate();
    }

}
