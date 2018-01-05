package com.mapsh.android.activity.avoidresult;

import android.content.Intent;

/**
 * @author mapsh
 * @date 2017/12/27
 */

public class ActivityResultInfo {
    private int    mRequestCode;
    private int    mResultCode;
    private Intent mData;

    public ActivityResultInfo(int requestCode, int resultCode, Intent data) {
        this.mRequestCode = requestCode;
        this.mResultCode = resultCode;
        this.mData = data;
    }

    public int getRequestCode() {
        return mRequestCode;
    }

    public void setRequestCode(int requestCode) {
        this.mRequestCode = requestCode;
    }

    public int getResultCode() {
        return mResultCode;
    }

    public void setResultCode(int resultCode) {
        this.mResultCode = resultCode;
    }

    public Intent getData() {
        return mData;
    }

    public void setData(Intent data) {
        this.mData = data;
    }
}
