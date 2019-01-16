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

package com.hendraanggrian.recyclerview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;

import com.hendraanggrian.recyclerview.expandable.R;

public class ExpandableItem extends RelativeLayout {

    static final String TAG = ExpandableItem.class.getCanonicalName();

    @NonNull private final ViewGroup contentLayout;
    @NonNull private final ViewGroup headerLayout;
    private int duration;
    private boolean closeByUser = true;
    private boolean isAnimationRunning = false;
    private boolean isOpened = false;

    public ExpandableItem(Context context) {
        this(context, null);
    }

    public ExpandableItem(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.expandableItemStyle);
    }

    public ExpandableItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.widget_expandableitem, this, true);
        headerLayout = findViewById(R.id.widget_expandableitem_headerlayout);
        contentLayout = findViewById(R.id.widget_expandableitem_contentlayout);

        final TypedArray a = context.obtainStyledAttributes(
            attrs, R.styleable.ExpandableItem, defStyleAttr, 0);
        final int headerId = a.getResourceId(R.styleable.ExpandableItem_layoutHeader, -1);
        final int contentId = a.getResourceId(R.styleable.ExpandableItem_layoutContent, -1);
        duration = a.getInt(R.styleable.ExpandableItem_duration,
            context.getResources().getInteger(android.R.integer.config_shortAnimTime));
        a.recycle();

        if (headerId == -1 || contentId == -1) {
            throw new IllegalArgumentException("HeaderLayout and ContentLayout cannot be null!");
        }
        if (isInEditMode()) {
            return;
        }

        final View headerView = inflater.inflate(headerId, headerLayout, false);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
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
        final View contentView = inflater.inflate(contentId, contentLayout, false);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        contentLayout.addView(contentView);
        contentLayout.setVisibility(GONE);

        setTag(TAG);
    }

    @UiThread
    private void expand(@NonNull final View view) {
        isOpened = true;
        view.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        final int targetHeight = view.getMeasuredHeight();
        view.getLayoutParams().height = 0;
        view.setVisibility(VISIBLE);

        final Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                view.getLayoutParams().height = (interpolatedTime == 1)
                    ? LayoutParams.WRAP_CONTENT
                    : (int) (targetHeight * interpolatedTime);
                view.requestLayout();
            }
        };
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    @UiThread
    private void collapse(@NonNull final View view) {
        isOpened = false;
        final int initialHeight = view.getMeasuredHeight();

        final Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    view.setVisibility(View.GONE);
                    isOpened = false;
                } else {
                    view.getLayoutParams().height = initialHeight -
                        (int) (initialHeight * interpolatedTime);
                    view.requestLayout();
                }
            }
        };
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    @UiThread
    public void hideNow() {
        contentLayout.getLayoutParams().height = 0;
        contentLayout.invalidate();
        contentLayout.setVisibility(View.GONE);
        isOpened = false;
    }

    @UiThread
    public void showNow() {
        if (!isOpened) {
            contentLayout.setVisibility(VISIBLE);
            isOpened = true;
            contentLayout.getLayoutParams().height = LayoutParams.WRAP_CONTENT;
            contentLayout.invalidate();
        }
    }

    @UiThread
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

    @UiThread
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

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    @NonNull
    public ViewGroup getHeaderLayout() {
        return headerLayout;
    }

    @NonNull
    public ViewGroup getContentLayout() {
        return contentLayout;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public boolean isClosedByUser() {
        return closeByUser;
    }
}