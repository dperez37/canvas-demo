package com.livefront.canvasapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.livefront.canvasapp.R;

public class MyViewPager extends ViewPager {

    private static final boolean DEFAULT_IGNORE_SWIPE = false;

    private boolean ignoreSwipe = false;

    public MyViewPager(Context context) {
        super(context);
        init(null);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MyViewPager,
                0,
                0);

        ignoreSwipe = a.getBoolean(R.styleable.MyViewPager_ignoreSwipe, DEFAULT_IGNORE_SWIPE);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return !ignoreSwipe && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return !ignoreSwipe && super.onTouchEvent(ev);
    }
}
