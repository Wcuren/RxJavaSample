package com.lj.sample.fragment;

import android.support.v4.app.Fragment;

public class FragmentFactory {

    public static Fragment getFragmentWithOperator(int position) {
        switch (position) {
            case 0:
                return new MapFragment();
            case 1:
                return new BufferFragment();
            case 2:
                return new CreateFragment();
            case 3:
                return new EmptyFragment();
            case 4:
                return new FromFragment();
            case 5:
                return new IntervalFragment();
            default:
                return null;
        }
    }
}
