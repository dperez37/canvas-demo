package com.livefront.canvasapp.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.livefront.canvasapp.R;
import com.livefront.canvasapp.util.AdapterUtil;
import com.livefront.canvasapp.view.CircleMaskView1;

public class Hole1Fragment extends Fragment {

    @ColorInt
    public static int adjustAlpha(@ColorInt int color,
                                  @IntRange(from = 0, to = 255) int newAlpha) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(newAlpha, red, green, blue);
    }

    public static Hole1Fragment newInstance() {
        Hole1Fragment fragment = new Hole1Fragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    private TextView mOpacityText;
    private SeekBar mOpacitySeekBar;
    private CircleMaskView1 mCircleMaskView1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hole_1, container, false);

        mCircleMaskView1 = (CircleMaskView1) root.findViewById(R.id.circle_mask);
        mOpacitySeekBar = (SeekBar) root.findViewById(R.id.opacity_seekbar);
        mOpacityText = (TextView) root.findViewById(R.id.opacity_seekbar_value);

        mOpacityText.setText(getString(R.string.opacity_size, mOpacitySeekBar.getProgress()));
        mOpacitySeekBar.setOnSeekBarChangeListener(new AdapterUtil.OnSeekBarChangeAdapter() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mOpacityText.setText(getString(R.string.opacity_size, progress));
                mCircleMaskView1.setMaskColor(adjustAlpha(mCircleMaskView1.getMaskColor(), progress));
            }
        });

        return root;
    }
}
