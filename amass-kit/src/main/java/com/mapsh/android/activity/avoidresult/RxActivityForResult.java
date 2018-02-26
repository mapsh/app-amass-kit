package com.mapsh.android.activity.avoidresult;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

/**
 * @author mapsh
 * @date 2018/1/5
 */

public class RxActivityForResult {
    private final String TAG = "RxActivityForResult";

    private RxActivityForResultFragment mRxActivityForResultFragment;

    public RxActivityForResult(@NonNull Activity activity) {
        mRxActivityForResultFragment = getRxActivityForResultFragment(activity);
    }

    private RxActivityForResultFragment getRxActivityForResultFragment(Activity activity) {
        RxActivityForResultFragment rxActivityForResultFragment = findRxActivityForResultFragment(activity);
        if (rxActivityForResultFragment == null) {
            rxActivityForResultFragment = new RxActivityForResultFragment();
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(rxActivityForResultFragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return rxActivityForResultFragment;
    }

    private RxActivityForResultFragment findRxActivityForResultFragment(Activity activity) {
        return (RxActivityForResultFragment) activity.getFragmentManager().findFragmentByTag(TAG);
    }

    public Observable<RxActivityForResultInfo> startActivityForResult(@NonNull Intent intent, int requestCode) {
        return requestImplementation(intent, requestCode);
    }


    public Observable<RxActivityForResultInfo> startActivityForResult(@NonNull Class<?> clazz, int requestCode) {
        Intent intent = new Intent(mRxActivityForResultFragment.getActivity(), clazz);
        return requestImplementation(intent, requestCode);
    }

    private Observable<RxActivityForResultInfo> requestImplementation(Intent intent, int requestCode) {
        return mRxActivityForResultFragment.startActForResult(intent, requestCode);
    }
}
