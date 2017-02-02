package com.livefront.canvasapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.livefront.canvasapp.R;
import com.livefront.canvasapp.view.ClipCirclesCanvas;

public class ClipCirclesFragment extends Fragment {

    private ClipCirclesCanvas mClipCirclesCanvas;
    private Button mResetButton;

    public static ClipCirclesFragment newInstance() {
        ClipCirclesFragment fragment = new ClipCirclesFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_clip_circles, container, false);

        mClipCirclesCanvas = (ClipCirclesCanvas) root.findViewById(R.id.canvas);
        mResetButton = (Button) root.findViewById(R.id.reset_button);

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClipCirclesCanvas.reset();
            }
        });

        return root;
    }

}
