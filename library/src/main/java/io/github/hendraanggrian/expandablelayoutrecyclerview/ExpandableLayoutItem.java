/***********************************************************************************
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2014 Robin Chutaux
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ***********************************************************************************/
package io.github.hendraanggrian.expandablelayoutrecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;

public class ExpandableLayoutItem extends FrameLayout {

    private boolean isAnimationRunning = false;
    private boolean isOpened = false;
    private int duration;
    private ViewGroup contentLayout;
    private ViewGroup headerLayout;
    private boolean closeByUser = true;

    private OnExpandListener listener;
    private OnCollapsedByUser onCollapsedByUser;

    public ExpandableLayoutItem(Context context) {
        super(context);
    }

    public ExpandableLayoutItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ExpandableLayoutItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        final View rootView = View.inflate(context, R.layout.view_expandable, this);
        headerLayout = (ViewGroup) rootView.findViewById(R.id.view_expandable_headerlayout);
        contentLayout = (ViewGroup) rootView.findViewById(R.id.view_expandable_contentLayout);

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableLayoutItem);
        final int headerID = typedArray.getResourceId(R.styleable.ExpandableLayoutItem_layoutHeader, -1);
        final int contentID = typedArray.getResourceId(R.styleable.ExpandableLayoutItem_layoutContent, -1);
        duration = typedArray.getInt(R.styleable.ExpandableLayoutItem_duration, getContext().getResources().getInteger(android.R.integer.config_shortAnimTime));
        typedArray.recycle();

        if (headerID == -1 || contentID == -1)
            throw new IllegalArgumentException("HeaderLayout and ContentLayout cannot be null!");

        if (isInEditMode())
            return;

        final View headerView = View.inflate(context, headerID, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        headerLayout.addView(headerView);
        setTag(ExpandableLayoutItem.class.getName());
        final View contentView = View.inflate(context, contentID, null);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        contentLayout.addView(contentView);
        contentLayout.setVisibility(GONE);

        headerLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (isOpened() && event.getAction() == MotionEvent.ACTION_UP) {
                    hide();
                    closeByUser = true;

                    if (onCollapsedByUser != null)
                        onCollapsedByUser.onCollapsed();
                }

                return isOpened() && event.getAction() == MotionEvent.ACTION_DOWN;
            }
        });
    }

    public ViewGroup getHeaderLayout() {
        return headerLayout;
    }

    public ViewGroup getContentLayout() {
        return contentLayout;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public boolean getCloseByUser() {
        return closeByUser;
    }

    public void expand(final View v) {
        isOpened = true;
        v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();
        v.getLayoutParams().height = 0;
        v.setVisibility(VISIBLE);

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = (interpolatedTime == 1) ? LayoutParams.WRAP_CONTENT : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setDuration(duration);
        v.startAnimation(animation);
    }

    public void collapse(final View v) {
        isOpened = false;
        final int initialHeight = v.getMeasuredHeight();
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                    isOpened = false;
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        animation.setDuration(duration);
        v.startAnimation(animation);
    }

    public void showNow() {
        if (!this.isOpened()) {
            contentLayout.setVisibility(VISIBLE);
            this.isOpened = true;
            contentLayout.getLayoutParams().height = LayoutParams.WRAP_CONTENT;
            contentLayout.invalidate();
        }
    }

    public void hideNow() {
        contentLayout.getLayoutParams().height = 0;
        contentLayout.invalidate();
        contentLayout.setVisibility(View.GONE);
        isOpened = false;
    }

    public void show() {
        if (!isAnimationRunning) {
            if (listener != null) {
                listener.onExpanding();
                listener.collapsingCalled = false;
            }

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
            if (listener != null && !listener.collapsingCalled) {
                listener.onCollapsing();
                listener.collapsingCalled = true;
            }

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

    public void showOrHide() {
        if (isOpened()) hide();
        else show();
    }

    public void setOnExpandListener(OnExpandListener listener) {
        this.listener = listener;
    }

    protected void setOnCollapsedByUser(OnCollapsedByUser onCollapsedByUser) {
        this.onCollapsedByUser = onCollapsedByUser;
    }

    public static abstract class OnExpandListener {
        private boolean collapsingCalled = false;

        public abstract void onExpanding();

        public abstract void onCollapsing();
    }

    public static class SimpleOnExpandListener extends OnExpandListener {

        @Override
        public void onExpanding() {

        }

        @Override
        public void onCollapsing() {

        }
    }

    protected interface OnCollapsedByUser {
        void onCollapsed();
    }
}