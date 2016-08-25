package com.ifox.android.lab.video;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.ifox.android.lab.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstScrollViewFragment extends ScrollTabHolderFragment {

    public static final String TAG = FirstScrollViewFragment.class.getSimpleName();
    private static final int NO_SCROLL_X = 0;
    private static final String ARG_POSITION = "position";

    private NotifyingScrollView mScrollView;
    private int mPosition;

    public static Fragment newInstance(int position) {
        FirstScrollViewFragment fragment = new FirstScrollViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public FirstScrollViewFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_scroll_view, container, false);
        mScrollView = (NotifyingScrollView) view.findViewById(R.id.scrollview);
        mScrollView.setOnScrollChangedListener( new NotifyingScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(ScrollView view, int l, int t, int oldl, int oldt) {

                if (mScrollTabHolder != null) {
                    mScrollTabHolder.onScrollViewScroll(view, l, t, oldl, oldt, mPosition);
                }
            }
        });

        mPosition = getArguments().getInt(ARG_POSITION);
        return view;
    }

    @Override
    public void adjustScroll(int scrollHeight, int headerHeight) {
        if (mScrollView == null) return;

        mScrollView.scrollTo(NO_SCROLL_X, headerHeight - scrollHeight);
    }
}
