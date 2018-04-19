package com.niuub.anim;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Animations util
 * <p>
 *
 * @author mapsh
 * @date 17/12/29
 */

public class AnimationUtils {

    public static void slideToUp(View view, Animation.AnimationListener listener) {
        slideToUp(view, 400, listener);
    }

    public static void slideToUp(View view, long durationMillis, Animation.AnimationListener listener) {
        Animation slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);

        slide.setDuration(durationMillis);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        if (listener != null) {
            slide.setAnimationListener(listener);
        }

        view.startAnimation(slide);
    }
    public static void slideToDown(View view, Animation.AnimationListener listener) {
        slideToDown(view, 400, listener);
    }

    public static void slideToDown(View view,long durationMillis, Animation.AnimationListener listener) {
        Animation slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f);

        slide.setDuration(durationMillis);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        if (listener != null) {
            slide.setAnimationListener(listener);
        }

        view.startAnimation(slide);
    }
}
