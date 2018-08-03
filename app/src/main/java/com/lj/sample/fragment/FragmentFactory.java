package com.lj.sample.fragment;

import android.support.v4.app.Fragment;

public class FragmentFactory {

    public static Fragment getFragmentWithOperator(int position) {
        switch (position) {
            case 0:
                return new MapFragment();
            case 1:
                return new BufferFragment();
            default:
                return null;
        }
    }
}
