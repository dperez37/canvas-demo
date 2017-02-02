package com.livefront.canvasapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.livefront.canvasapp.R;

public class ClipRectFragment extends Fragment {

    public static ClipRectFragment newInstance() {
        ClipRectFragment fragment = new ClipRectFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_clip_rect, container, false);
    }
}
