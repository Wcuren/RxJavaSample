package com.lj.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lj.sample.R;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

public class BufferFragment extends Fragment {
    private static final String TAG = "BufferFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
//        bufferFunction(3);
//        bufferFunction(3,2);
        bufferFunction(100L);
        return view;
    }

    private Observable<String> getStringObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                String[] strs = new String[]{"0","1","2","3","4","5","6","7","8","9","10","11","12",};
                for (String str : strs) {
                    e.onNext(str);
                }
//                e.onComplete();
            }
        });
    }

    /**
     * buffer(int count)这个函数，count表示缓存数量，缓存count个Observable发送过来的数据，
     * 然后组成一个发送List数据的Observable，发送给Observer。如果最后发送的数据不满count个，
     * 则不会发送给Observer,除非Observable中调用onComplete(),则会将最后的不满count个的数据
     * 发送给Observer。
     * 示例count = 3，结果 [0, 1, 2]，[3, 4, 5]，[9, 10, 11]
     * @param count
     */
    private void bufferFunction(int count) {
        getStringObservable().buffer(count)
        .subscribe(new Consumer<List<String>>() {
            @Override
            public void accept(List<String> list) throws Exception {
                Log.d(TAG, list.toString());
            }
        });
    }

    private void bufferFunction(int count, int skip) {
        getStringObservable().buffer(count, skip)
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> list) throws Exception {
                        Log.d(TAG, list.toString());
                    }
                });
    }

    private void bufferFunction(long timespan) {
        getStringObservable().buffer(timespan, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> list) throws Exception {
                        Log.d(TAG, list.toString());
                    }
                });
    }
}
