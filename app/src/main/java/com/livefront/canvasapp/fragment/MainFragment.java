package com.livefront.canvasapp.fragment;

import android.os.Bundle;

import com.livefront.canvasapp.R;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends TabFragment {

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected List<TabFragment.TabItem> createTabItemList() {
        List<TabFragment.TabItem> list = new ArrayList<>();
        list.add(new TabItem(
                ClipRectFragment.newInstance(),
                getString(R.string.clipped_rect)));
        list.add(new TabItem(
                Hole1Fragment.newInstance(),
                getString(R.string.fragment_hole_1)));
        list.add(new TabItem(
                Hole2Fragment.newInstance(),
                getString(R.string.fragment_hole_2)));
        list.add(new TabItem(
                Hole3Fragment.newInstance(),
                getString(R.string.fragment_hole_3)));
        list.add(new TabItem(
                PixelMoverFragment.newInstance(),
                getString(R.string.pixel_mover)));
        list.add(new TabItem(
                FrameFragment.newInstance(),
                getString(R.string.fragment_frame)));
        list.add(new TabItem(
                BlurFragment.newInstance(),
                getString(R.string.fragment_distort)));
        list.add(new TabItem(
                ClipPathFragment.newInstance(),
                getString(R.string.fragment_clip)));
        list.add(new TabItem(
                ClipCirclesFragment.newInstance(),
                getString(R.string.fragment_circle)));
        return list;
    }
}
