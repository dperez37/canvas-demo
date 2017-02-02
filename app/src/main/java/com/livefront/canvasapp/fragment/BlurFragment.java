package com.livefront.canvasapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.livefront.canvasapp.R;
import com.livefront.canvasapp.util.AdapterUtil;
import com.livefront.canvasapp.view.BlurCanvas;

public class BlurFragment extends Fragment {

    private BlurCanvas mBlurCanvas;
    private Button mResetButton;
    private TextView mRadiusSeekBarText;
    private SeekBar mRadiusSeekBar;
    private TextView mSizeSeekBarText;
    private SeekBar mSizeSeekBar;

    public static BlurFragment newInstance() {
        BlurFragment fragment = new BlurFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_blur, container, false);

        mBlurCanvas = (BlurCanvas) root.findViewById(R.id.canvas);
        mResetButton = (Button) root.findViewById(R.id.reset_button);
        mRadiusSeekBarText = (TextView) root.findViewById(R.id.blur_radius_seekbar_value);
        mRadiusSeekBar = (SeekBar) root.findViewById(R.id.blur_radius_seekbar);
        mSizeSeekBarText = (TextView) root.findViewById(R.id.blur_size_seekbar_value);
        mSizeSeekBar = (SeekBar) root.findViewById(R.id.blur_size_seekbar);

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBlurCanvas.reset();
            }
        });

        mRadiusSeekBarText.setText(getString(R.string.blur_quantity, mBlurCanvas.getBlurRadius()));
        mRadiusSeekBar.setProgress(mBlurCanvas.getBlurRadius());
        mRadiusSeekBar.setOnSeekBarChangeListener(new AdapterUtil.OnSeekBarChangeAdapter() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mRadiusSeekBarText.setText(getString(R.string.blur_quantity, progress));
                mBlurCanvas.setBlurRadius(progress);
            }
        });

        mSizeSeekBarText.setText(getString(R.string.blur_area, mBlurCanvas.getBlurSize()));
        mSizeSeekBar.setProgress(mBlurCanvas.getBlurSize());
        mSizeSeekBar.setOnSeekBarChangeListener(new AdapterUtil.OnSeekBarChangeAdapter() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mSizeSeekBarText.setText(getString(R.string.blur_area, progress));
                mBlurCanvas.setBlurSize(progress);
            }
        });

        return root;
    }

}
