package com.hendraanggrian.widget;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class ExpandableRecyclerView extends RecyclerView {

    public ExpandableRecyclerView(Context context) {
        this(context, null);
    }

    public ExpandableRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (!(layout instanceof LinearLayoutManager))
            throw new IllegalArgumentException("layout manager must be an instance of LinearLayoutManager!");
        super.setLayoutManager(layout);
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (!(adapter instanceof Adapter))
            throw new IllegalArgumentException("adapter must be an instance of ExpandableRecyclerView.Adapter!");
        super.setAdapter(adapter);
    }

    public static abstract class Adapter<VH extends ViewHolder> extends RecyclerView.Adapter<VH> {
        @NonNull private final LinearLayoutManager layout;
        private int currentPosition = -1;

        public Adapter(@NonNull LinearLayoutManager layout) {
            this.layout = layout;
        }

        @Override
        @CallSuper
        public void onBindViewHolder(VH holder, int position) {
            ExpandableItem expandableItem = (ExpandableItem) holder.itemView.findViewWithTag(ExpandableItem.TAG);
            expandableItem.setOnClickListener(getOnExpandableClickListener(position));
            if (currentPosition != position && expandableItem.isOpened())
                expandableItem.hideNow();
            else if (currentPosition == position && !expandableItem.isOpened() && !expandableItem.isClosedByUser())
                expandableItem.showNow();
        }

        @NonNull
        protected OnClickListener getOnExpandableClickListener(final int position) {
            return new OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentPosition = position;
                    for (int index = 0; index < layout.getChildCount(); ++index) {
                        if (index != (currentPosition - layout.findFirstVisibleItemPosition())) {
                            ExpandableItem currentExpandableItem = (ExpandableItem) layout.getChildAt(index).findViewWithTag(ExpandableItem.TAG);
                            currentExpandableItem.hide();
                        }
                    }
                    ExpandableItem expandableItem = (ExpandableItem) layout.getChildAt(currentPosition - layout.findFirstVisibleItemPosition()).findViewWithTag(ExpandableItem.TAG);
                    if (expandableItem.isOpened())
                        expandableItem.hide();
                    else
                        expandableItem.show();
                }
            };
        }
    }
}