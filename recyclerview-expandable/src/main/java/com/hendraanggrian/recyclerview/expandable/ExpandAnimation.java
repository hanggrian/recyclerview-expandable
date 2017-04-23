package com.hendraanggrian.recyclerview.expandable;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class ExpandAnimation extends Animation {

    @NonNull private final View view;
    private final int targetHeight;

    public ExpandAnimation(@NonNull View view) {
        this.view = view;
        this.view.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.targetHeight = view.getMeasuredHeight();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        view.getLayoutParams().height = (interpolatedTime == 1) ? RelativeLayout.LayoutParams.WRAP_CONTENT : (int) (targetHeight * interpolatedTime);
        view.requestLayout();
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}