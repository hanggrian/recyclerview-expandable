/***********************************************************************************
 * The MIT License (MIT)
 * Copyright (c) 2014 Robin Chutaux
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ***********************************************************************************/

package com.hendraanggrian.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.hendraanggrian.recyclerview.expandable.CollapseAnimation;
import com.hendraanggrian.recyclerview.expandable.ExpandAnimation;
import com.hendraanggrian.recyclerview.expandable.R;

public class ExpandableItem extends RelativeLayout {

    public static final String TAG = "io.github.hendraanggrian.widget.ExpandableItem";

    @NonNull private final FrameLayout contentLayout;
    @NonNull private final FrameLayout headerLayout;

    private int duration;
    private boolean isAnimationRunning;
    private boolean isOpened;
    private boolean closeByUser = true;

    public ExpandableItem(Context context) {
        this(context, null);
    }

    public ExpandableItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View.inflate(context, R.layout.view_expandable, this);
        headerLayout = (FrameLayout) findViewById(R.id.view_expandable_header);
        contentLayout = (FrameLayout) findViewById(R.id.view_expandable_content);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ExpandableItem);
        int headerID, contentID;
        try {
            duration = array.getInt(R.styleable.ExpandableItem_duration, getContext().getResources().getInteger(android.R.integer.config_shortAnimTime));
            headerID = array.getResourceId(R.styleable.ExpandableItem_layoutHeader, -1);
            contentID = array.getResourceId(R.styleable.ExpandableItem_layoutContent, -1);
        } finally {
            array.recycle();
        }

        if (headerID == -1 || contentID == -1)
            throw new IllegalArgumentException("HeaderLayout and ContentLayout cannot be null!");

        if (isInEditMode())
            return;

        setTag(TAG);

        View headerView = View.inflate(context, headerID, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        headerLayout.addView(headerView);
        headerLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isOpened() && event.getAction() == MotionEvent.ACTION_UP) {
                    hide();
                    closeByUser = true;
                }

                return isOpened() && event.getAction() == MotionEvent.ACTION_DOWN;
            }
        });

        View contentView = View.inflate(context, contentID, null);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        contentLayout.addView(contentView);
        contentLayout.setVisibility(GONE);
    }

    @NonNull
    public FrameLayout getHeaderLayout() {
        return headerLayout;
    }

    @NonNull
    public FrameLayout getContentLayout() {
        return contentLayout;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void hideNow() {
        contentLayout.getLayoutParams().height = 0;
        contentLayout.invalidate();
        contentLayout.setVisibility(View.GONE);
        isOpened = false;
    }

    public void showNow() {
        if (!isOpened) {
            contentLayout.setVisibility(VISIBLE);
            isOpened = true;
            contentLayout.getLayoutParams().height = LayoutParams.WRAP_CONTENT;
            contentLayout.invalidate();
        }
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void show() {
        if (!isAnimationRunning) {
            expand(contentLayout);
            isAnimationRunning = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isAnimationRunning = false;
                }
            }, duration);
        }
    }

    public void hide() {
        if (!isAnimationRunning) {
            collapse(contentLayout);
            isAnimationRunning = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isAnimationRunning = false;
                }
            }, duration);
        }
        closeByUser = false;
    }

    public boolean isClosedByUser() {
        return closeByUser;
    }

    private void expand(@NonNull final View view) {
        isOpened = true;

        Animation animation = new ExpandAnimation(view);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.getLayoutParams().height = 0;
                view.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    private void collapse(@NonNull final View view) {
        isOpened = false;

        Animation animation = new CollapseAnimation(view) {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    view.setVisibility(View.GONE);
                    isOpened = false;
                } else {
                    super.applyTransformation(interpolatedTime, t);
                }
            }
        };
        animation.setDuration(duration);
        view.startAnimation(animation);
    }
}