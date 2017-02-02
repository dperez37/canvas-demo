package com.livefront.canvasapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.livefront.canvasapp.R;
import com.livefront.canvasapp.util.AdapterUtil;
import com.livefront.canvasapp.view.FrameCanvas;

public class FrameFragment extends Fragment {

    public static FrameFragment newInstance() {
        FrameFragment fragment = new FrameFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    private FrameCanvas mFrameCanvas;
    private Spinner mFrameSpinner;
    private TextView mSizeTextView;
    private SeekBar mSizeSeekBar;
    private Button mResetButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_frame, container, false);

        mFrameCanvas = (FrameCanvas) root.findViewById(R.id.frame_canvas);
        mSizeSeekBar = (SeekBar) root.findViewById(R.id.frame_size_seekbar);
        mSizeTextView = (TextView) root.findViewById(R.id.frame_size_seekbar_value);
        mFrameSpinner = (Spinner) root.findViewById(R.id.frame_type);
        mResetButton = (Button) root.findViewById(R.id.reset_button);

        mSizeSeekBar.setProgress(mFrameCanvas.getFrameSize());
        mSizeTextView.setText(getString(R.string.frame_size, mFrameCanvas.getFrameSize()));
        mSizeSeekBar.setOnSeekBarChangeListener(new AdapterUtil.OnSeekBarChangeAdapter() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mFrameCanvas.setFrameSize(progress);
                mSizeTextView.setText(getString(R.string.frame_size, progress));
            }
        });

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.frames,
                android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFrameSpinner.setAdapter(spinnerAdapter);
        mFrameSpinner.setOnItemSelectedListener(new AdapterUtil.OnItemSelectedAdapter() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mFrameCanvas.setFrameId(R.drawable.frame_wood);
                        break;
                    case 1:
                        mFrameCanvas.setFrameId(R.drawable.frame_metal);
                        break;
                }
            }
        });

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFrameCanvas.reset();
            }
        });

        return root;
    }
}
