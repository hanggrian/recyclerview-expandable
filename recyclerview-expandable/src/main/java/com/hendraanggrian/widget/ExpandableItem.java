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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.hendraanggrian.recyclerview.expandable.ExpandUtils;
import com.hendraanggrian.recyclerview.expandable.R;

public class ExpandableItem extends RelativeLayout {

    public static final CharSequence TAG = ExpandableItem.class.getCanonicalName();

    @NonNull private final FrameLayout layoutHeader;
    @NonNull private final FrameLayout layoutContent;
    private boolean isAnimationRunning = false;
    private boolean isOpened = false;
    private int duration;
    private boolean isClosedByUser = true;

    public ExpandableItem(Context context) {
        this(context, null);
    }

    public ExpandableItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_expandable, this, true);
        layoutHeader = (FrameLayout) findViewById(R.id.view_expandable_header);
        layoutContent = (FrameLayout) findViewById(R.id.view_expandable_content);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpandableItem);
        int headerId, contentId;
        try {
            headerId = a.getResourceId(R.styleable.ExpandableItem_layoutHeader, -1);
            contentId = a.getResourceId(R.styleable.ExpandableItem_layoutContent, -1);
            duration = a.getInt(R.styleable.ExpandableItem_duration, getContext().getResources().getInteger(android.R.integer.config_shortAnimTime));
        } finally {
            a.recycle();
        }
        if (headerId == -1 || contentId == -1)
            throw new IllegalArgumentException("HeaderLayout and ContentLayout cannot be null!");
        if (isInEditMode())
            return;
        View headerView = View.inflate(context, headerId, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        layoutHeader.addView(headerView);
        layoutHeader.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isOpened() && event.getAction() == MotionEvent.ACTION_UP) {
                    hide();
                    isClosedByUser = true;
                }
                return isOpened() && event.getAction() == MotionEvent.ACTION_DOWN;
            }
        });
        View contentView = View.inflate(context, contentId, null);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        layoutContent.addView(contentView);
        layoutContent.setVisibility(GONE);
        setTag(TAG);
    }

    public void hideNow() {
        layoutContent.getLayoutParams().height = 0;
        layoutContent.invalidate();
        layoutContent.setVisibility(View.GONE);
        isOpened = false;
    }

    public void showNow() {
        if (!this.isOpened()) {
            layoutContent.setVisibility(VISIBLE);
            this.isOpened = true;
            layoutContent.getLayoutParams().height = LayoutParams.WRAP_CONTENT;
            layoutContent.invalidate();
        }
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void show() {
        if (!isAnimationRunning) {
            ExpandUtils.expand(layoutContent, duration);
            isOpened = true;
            isAnimationRunning = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isAnimationRunning = false;
                }
            }, duration);
        }
    }

    @NonNull
    public FrameLayout getHeaderLayout() {
        return layoutHeader;
    }

    @NonNull
    public FrameLayout getContentLayout() {
        return layoutContent;
    }

    public void hide() {
        if (!isAnimationRunning) {
            ExpandUtils.collapse(layoutContent, duration);
            isOpened = false;
            isAnimationRunning = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isAnimationRunning = false;
                }
            }, duration);
        }
        isClosedByUser = false;
    }

    public boolean isClosedByUser() {
        return isClosedByUser;
    }
}