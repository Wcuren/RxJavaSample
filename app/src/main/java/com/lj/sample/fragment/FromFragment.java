package com.lj.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.LoginFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lj.sample.R;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class FromFragment extends Fragment {

    private static final String TAG = "FromFragment";
    private int[] mInts = new int[]{1,2,3,4,5};
    private int a = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
//        fromFunction();
        deferFunction();
        return view;
    }

    /**
     * fromArray()等价于just(),源码中fromArray()最后都调用到RxJavaPlugins.onAssembly(),这里面参数是ObservableFromArray，
     * 这个只有一个属性T[]，这个属性和just()最后调用RxJavaPlugins.onAssembly()传入参数ObservableJust中的属性T的作用一样。
     * @return
     */
    private Observable fromObservable() {
        return Observable.fromArray(a);

    }

    /**
     * defer操作符，作用是当调用subscribe后才开始初始化Observable实例，相对于from操作符，调用from操作符
     * 后立即初始化Observable实例。
     * @return
     */
    private Observable deferObservable() {
        return Observable.defer(new Callable<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> call() throws Exception {
                return Observable.just(a);
            }
        });
    }

    private Observable justObservable() {
        return Observable.just(mInts);
    }

    private void deferFunction() {
        Observable observable = deferObservable();
        a = 3;
        observable.subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Log.d(TAG, "defer operator accept:" + (int)o);

            }
        });
    }

    private void fromFunction() {
        Observable observable = fromObservable();
        a = 1;
        observable.subscribe(new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                Log.d(TAG, "from operator onNext:" + (int)o);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


}
