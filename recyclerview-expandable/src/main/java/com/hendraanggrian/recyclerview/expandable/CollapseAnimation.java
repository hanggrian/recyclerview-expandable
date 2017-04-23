package com.hendraanggrian.recyclerview.expandable;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class CollapseAnimation extends Animation {

    @NonNull private final View view;
    private final int initialHeight;

    public CollapseAnimation(@NonNull View view) {
        this.view = view;
        this.initialHeight = view.getMeasuredHeight();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        view.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
        view.requestLayout();
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}