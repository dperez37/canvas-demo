package com.livefront.canvasapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.livefront.canvasapp.R;
import com.livefront.canvasapp.view.PixelMoverCanvas;

public class PixelMoverFragment extends Fragment {

    public static PixelMoverFragment newInstance() {
        PixelMoverFragment fragment = new PixelMoverFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    private PixelMoverCanvas mPixelMoverCanvas;
    private Button mButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pixel_mover, container, false);

        mPixelMoverCanvas = (PixelMoverCanvas) root.findViewById(R.id.pixel_mover_canvas);
        mButton = (Button) root.findViewById(R.id.reset_button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPixelMoverCanvas.reset();
            }
        });

        return root;
    }
}
