package com.lj.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lj.sample.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class MapFragment extends Fragment {

    private static final String TAG = "MapFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
//        mapfunction(view);
        flatMapFunction(view);
        return view;
    }

    /**
     * map操作符，就是在被观察者在调用subscribe函数被订阅后，通过map操作符调用处理函数Function()，在这个
     * 函数中会将被观察者传过来的值进行一些定制化的处理，然后将处理完的值传给观察者。
     * @param view
     */
    private void mapfunction(final View view) {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
            }
        });

        observable.map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return "the result with map is:" + integer;
                    }})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        ((TextView)view.findViewById(R.id.tv)).setText(s);
                    }
        });
    }

    /**
     * 假设一个场景，查询到一个学生名字，通过这个学生名字查询到他报名的课程，然后将他的课程名打印出来。
     * 这种场景下可以用到flatmap。
     * flatmap操作符，原本发送事件的被观察者Observable通过flatmap操作符调用Function函数，做相应处理后，
     * 合成一个新的Observable，然后发送事件给观察者。
     * @param view
     */
    private void flatMapFunction(View view) {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.d(TAG, "Observable in thread:" + Thread.currentThread().getName());
                e.onNext("Tom");
                e.onComplete();
            }
        });
        observable.observeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        List<String> list = new ArrayList<>();
                        list.add("Tom's first lesson is: English.");
                        list.add("Tom's second lesson is: Math.");
                        list.add("Tom's third lesson is: History.");
                        Log.d(TAG, "current time is:" + System.currentTimeMillis() +
                                ". flatMap in thread:" + Thread.currentThread().getName());
                        return Observable.fromIterable(list).delay(100, TimeUnit.MILLISECONDS);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext! " + s + " now time is:" + System.currentTimeMillis() +
                                ". observer in thread:" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete!");
                    }
                });
    }
}
