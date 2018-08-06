package com.lj.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lj.sample.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class IntervalFragment extends Fragment {

    private static final String TAG = "IntervalFragment";
    private CompositeDisposable mDisposable;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        mDisposable = new CompositeDisposable();
        intervalFunction();
        return view;
    }

    private Observable intervalObservable() {
        return Observable.interval(100, TimeUnit.MILLISECONDS);
    }

    /**
     * interval操作符每隔一段指定时间顺序发送一个数字，CompositeDisposable配合用来关闭这个循环操作
     */
    private void intervalFunction() {
        mDisposable.clear();
        DisposableObserver observer = new DisposableObserver() {
            @Override
            public void onNext(Object o) {
                if ((long)o > 10) {
                    mDisposable.clear();
                } else {
                    Log.d(TAG, "interval onNext:" + (long)o);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        intervalObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        mDisposable.add(observer);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDisposable.clear();
    }
}
