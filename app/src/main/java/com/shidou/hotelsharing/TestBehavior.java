package com.shidou.hotelsharing;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.TextView;

/**
 * @author mapsh on 2018/1/16 09:51.
 */

public class TestBehavior extends CoordinatorLayout.Behavior<TextView> {

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }
}
