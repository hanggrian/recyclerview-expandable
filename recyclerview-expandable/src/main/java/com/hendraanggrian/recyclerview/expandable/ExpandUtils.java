package com.hendraanggrian.recyclerview.expandable;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

import static android.view.View.VISIBLE;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class ExpandUtils {

    public static void expand(@NonNull final View view, int duration) {
        view.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = view.getMeasuredHeight();
        view.getLayoutParams().height = 0;
        view.setVisibility(VISIBLE);

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                view.getLayoutParams().height = (interpolatedTime == 1) ? RelativeLayout.LayoutParams.WRAP_CONTENT : (int) (targetHeight * interpolatedTime);
                view.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    public static void collapse(@NonNull final View view, int duration) {
        final int initialHeight = view.getMeasuredHeight();
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setDuration(duration);
        view.startAnimation(animation);
    }
}