package com.mapsh.utils;


import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * RXjava的帮助类
 */
public final class RxHelper {

    private static final FlowableTransformer flowableTransformerIoMain = new FlowableTransformer() {
        @Override
        public Publisher apply(@NonNull Flowable flowable) {
            return flowable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io());
        }
    };

    private static final FlowableTransformer flowableTransformerNewThreadMain = new FlowableTransformer() {
        @Override
        public Publisher apply(@NonNull Flowable flowable) {
            return flowable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io());
        }
    };

    @SuppressWarnings("unchecked")
    //调度器
    public static <T> FlowableTransformer<T, T> io2Main() {
        return (FlowableTransformer<T, T>) flowableTransformerIoMain;
    }

    @SuppressWarnings("unchecked")
    public static <T> FlowableTransformer<T, T> newThread2Main() {
        return (FlowableTransformer<T, T>) flowableTransformerNewThreadMain;
    }

    public static <T> LifecycleTransformer<T> bindToLifecycle(RxAppCompatActivity activity) {
        return activity.bindToLifecycle();
    }

    public static <T> LifecycleTransformer<T> bindToLifecycle(RxFragment fragment) {
        return fragment.bindToLifecycle();
    }

    /**
     * 倒计时
     *
     * @param time
     * @return
     */
    public static Flowable<Integer> countdown(int time) {
        if (time < 0) {
            time = 0;
        }
        final int countTime = time;
        return Flowable.interval(0, 1, TimeUnit.SECONDS)
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(@NonNull Long increaseTime) throws Exception {
                        return countTime - increaseTime.intValue();
                    }
                })
                .take(countTime + 1)
                .compose(RxHelper.<Integer>io2Main());

    }

}
