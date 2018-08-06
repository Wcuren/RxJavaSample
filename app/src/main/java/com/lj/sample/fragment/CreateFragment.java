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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 这里主要是查看create操作创建一个Observable被多次订阅的时候，Observable发送事件以及Observer处理事件的顺序
 */
public class CreateFragment extends Fragment {

    private static final String TAG = "CreateFragment";
    private Observable mObservable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        for (int i = 0; i < 3; i++) {
            createFunction(i);
        }
        return view;
    }

    private void createFunction(final int position) {
        createObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "Observer position is:" + position + ";onNext :" + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private Observable<String> createObservable() {
        if (mObservable == null) {
            mObservable = Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> e) throws Exception {
                    for (int i = 0; i < 10; i++) {
                        e.onNext("observable" + i);
                    }
                    e.onComplete();
                }
            });
        }
        return mObservable;
    }
}
