package com.lj.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lj.sample.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class EmptyFragment extends Fragment {

    private static final String TAG = EmptyFragment.class.getName();
    private Observable mObservable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        function("empty", emptyObservable());
        function("never", neverObservable());
        function("throw", throwObservable());
        return view;
    }

    private void function(final String operator, Observable observable) {
        observable.subscribe(new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                Log.d(TAG, "operator is:" + operator + "--onNext()!");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "operator is:" + operator + "--onError()!");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "operator is:" + operator + "--onComplete()!");
            }
        });
    }

    private Observable emptyObservable() {
        return Observable.empty();
    }

    private Observable neverObservable() {
        return Observable.never();
    }

    private Observable throwObservable() {
        return Observable.error(new Throwable("error message!"));
    }

    private Observable createObservable() {
        if (mObservable == null) {
            mObservable = Observable.create(new ObservableOnSubscribe() {
                @Override
                public void subscribe(ObservableEmitter e) throws Exception {

                }
            });
        }
        return mObservable;
    }
}
