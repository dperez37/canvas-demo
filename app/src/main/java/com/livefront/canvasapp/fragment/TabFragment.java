package com.livefront.canvasapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.livefront.canvasapp.R;

import java.util.List;

public abstract class TabFragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TabAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new TabAdapter(getFragmentManager(), createTabItemList());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tab, container, false);

        mTabLayout = (TabLayout) root.findViewById(R.id.tabs);
        mViewPager = (ViewPager) root.findViewById(R.id.view_pager);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setClickable(false);

        return root;
    }

    protected abstract List<TabItem> createTabItemList();

    public class TabAdapter extends FragmentPagerAdapter {

        private List<TabItem> mTabItems;

        public TabAdapter(@NonNull FragmentManager fm, @NonNull List<TabItem> tabItems) {
            super(fm);
            mTabItems = tabItems;
        }

        @Override
        public Fragment getItem(int position) {
            return mTabItems.get(position).getFragment();
        }

        @Override
        public int getCount() {
            return mTabItems.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabItems.get(position).getLabel();
        }
    }

    /**
     * Used in {@link TabAdapter}, contains the Fragment and the tile to be displayed.
     */
    public static class TabItem {

        private Fragment mFragment;
        private String mLabel;

        public TabItem(@NonNull Fragment fragment, @NonNull String label) {
            mFragment = fragment;
            mLabel = label;
        }

        public Fragment getFragment() {
            return mFragment;
        }

        public String getLabel() {
            return mLabel;
        }
    }

}
