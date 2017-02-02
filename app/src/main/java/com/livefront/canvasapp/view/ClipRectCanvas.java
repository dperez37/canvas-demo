package com.livefront.canvasapp.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Region;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.livefront.canvasapp.R;

public class ClipRectCanvas extends View {

    private Paint mPaint;

    public ClipRectCanvas(Context context) {
        super(context);
        init();
    }

    public ClipRectCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClipRectCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClipRectCanvas(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float left = canvas.getWidth() * 0.25f;
        float top = canvas.getHeight() * 0.25f;
        float right = canvas.getWidth() * 0.75f;
        float bottom = canvas.getHeight() * 0.75f;
        canvas.clipRect(left, top, right, bottom, Region.Op.INTERSECT);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mPaint);
    }
}
