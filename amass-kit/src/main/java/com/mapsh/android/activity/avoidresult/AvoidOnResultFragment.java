package com.mapsh.android.activity.avoidresult;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * @author mapsh
 * @date 2017/12/27
 */

public class AvoidOnResultFragment extends Fragment {

    private SparseArray<PublishSubject<ActivityResultInfo>> mSubjects  = new SparseArray<>();
    private SparseArray<AvoidOnResult.Callback>             mCallbacks = new SparseArray<>();

    public AvoidOnResultFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public Observable<ActivityResultInfo> startForResult(final Intent intent, final int requestCode) {
        PublishSubject<ActivityResultInfo> subject = PublishSubject.create();
        mSubjects.put(requestCode, subject);
        return subject.doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                startActivityForResult(intent, requestCode);
            }
        });
    }

    public void startForResult(Intent intent, int requestCode, AvoidOnResult.Callback callback) {
        mCallbacks.put(requestCode, callback);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //rxjava方式的处理
        PublishSubject<ActivityResultInfo> subject = mSubjects.get(requestCode);
        if (subject != null) {
            subject.onNext(new ActivityResultInfo(requestCode, resultCode, data));
            subject.onComplete();
            mSubjects.remove(requestCode);
        }

        //callback方式的处理
        AvoidOnResult.Callback callback = mCallbacks.get(requestCode);
        if (callback != null) {
            callback.onActivityResult(requestCode, resultCode, data);
            mCallbacks.remove(requestCode);
        }
    }
}
