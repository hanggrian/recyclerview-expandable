package com.hendraanggrian.recyclerview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ExpandableRecyclerView extends RecyclerView {

    public ExpandableRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public ExpandableRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableRecyclerView(
        @NonNull Context context,
        @Nullable AttributeSet attrs,
        int defStyle
    ) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        if (!(layout instanceof LinearLayoutManager)) {
            throw new IllegalArgumentException("layoutManager manager must be an instance of LinearLayoutManager!");
        }
        super.setLayoutManager(layout);
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (!(adapter instanceof Adapter)) {
            throw new IllegalArgumentException("adapter must be an instance of ExpandableRecyclerView.Adapter!");
        }
        super.setAdapter(adapter);
    }

    public static abstract class Adapter<VH extends ViewHolder> extends RecyclerView.Adapter<VH> {
        @NonNull private final LinearLayoutManager layoutManager;
        private int currentPosition = -1;

        public Adapter(@NonNull LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        @CallSuper
        public void onBindViewHolder(@NonNull final VH holder, int position) {
            final ExpandableItem expandableItem = holder.itemView.findViewWithTag(ExpandableItem.TAG);
            if (expandableItem == null) {
                throw new RuntimeException("Item of this adapter must contain ExpandableItem!");
            }
            expandableItem.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentPosition = holder.getLayoutPosition();
                    for (int index = 0; index < layoutManager.getChildCount(); ++index) {
                        if (index != (currentPosition - layoutManager.findFirstVisibleItemPosition())) {
                            final ExpandableItem currentExpandableItem =
                                layoutManager.getChildAt(index).findViewWithTag(ExpandableItem.TAG);
                            currentExpandableItem.hide();
                        }
                    }
                    final ExpandableItem expandableItem = layoutManager
                        .getChildAt(currentPosition - layoutManager.findFirstVisibleItemPosition())
                        .findViewWithTag(ExpandableItem.TAG);
                    if (expandableItem.isOpened()) {
                        expandableItem.hide();
                    } else {
                        expandableItem.show();
                    }
                }
            });
            if (currentPosition != position && expandableItem.isOpened()) {
                expandableItem.hideNow();
            } else if (currentPosition == position &&
                !expandableItem.isOpened() &&
                !expandableItem.isClosedByUser()) {
                expandableItem.showNow();
            }
        }
    }
}