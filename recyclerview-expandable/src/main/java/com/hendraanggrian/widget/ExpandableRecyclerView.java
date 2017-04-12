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
        if (isInEditMode())
            return;
        addOnScrollListener(new OnExpandableScrollListener());
    }

    @Override
    public void addOnScrollListener(OnScrollListener listener) {
        if (!(listener instanceof OnExpandableScrollListener))
            throw new IllegalArgumentException("OnScrollListener must be an OnExpandableScrollListener!");
        super.addOnScrollListener(listener);
    }

    public static class OnExpandableScrollListener extends OnScrollListener {
        private int currentState = 0;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            this.currentState = newState;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (recyclerView.getAdapter() != null || recyclerView.getLayoutManager() != null)
                return;
            if (!(recyclerView.getAdapter() instanceof ExpandableRecyclerView.Adapter))
                throw new RuntimeException("Adapter must be an ExpandableRecyclerView.Adapter");
            if (!(recyclerView.getLayoutManager() instanceof LinearLayoutManager))
                throw new RuntimeException("LayoutManager must be a LinearLayoutManager");

            if (currentState != SCROLL_STATE_IDLE) {
                final ExpandableRecyclerView.Adapter adapter = (Adapter) recyclerView.getAdapter();
                final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                for (int index = 0; index < layoutManager.getChildCount(); ++index) {
                    ExpandableItem currentExpandable = (ExpandableItem) layoutManager.getChildAt(index).findViewWithTag(ExpandableItem.TAG);
                    if (currentExpandable.isOpened() && index != (adapter.currentPosition - layoutManager.findFirstVisibleItemPosition()))
                        currentExpandable.hideNow();
                    else if (!currentExpandable.isClosedByUser() && !currentExpandable.isOpened() && index == (adapter.currentPosition - layoutManager.findFirstVisibleItemPosition()))
                        currentExpandable.showNow();
                }
            }
        }
    }

    public static abstract class Adapter<VH extends ViewHolder> extends RecyclerView.Adapter<VH> {
        @NonNull private final LinearLayoutManager layoutManager;
        private int currentPosition = -1;

        public Adapter(@NonNull LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        @CallSuper
        public void onBindViewHolder(VH holder, int position) {
            ExpandableItem expandableItem = (ExpandableItem) holder.itemView.findViewWithTag(ExpandableItem.TAG);
            expandableItem.setOnClickListener(getOnExpandableClickListener(holder.getLayoutPosition()));
            if (currentPosition != position && expandableItem.isOpened())
                expandableItem.hideNow();
            else if (currentPosition == position && !expandableItem.isOpened())
                expandableItem.showNow();
        }

        @NonNull
        protected OnClickListener getOnExpandableClickListener(final int position) {
            return new OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentPosition = position;

                    for (int index = 0; index < layoutManager.getChildCount(); ++index) {
                        if (index != (currentPosition - layoutManager.findFirstVisibleItemPosition())) {
                            ExpandableItem currentExpandableItem = (ExpandableItem) layoutManager.getChildAt(index).findViewWithTag(ExpandableItem.TAG);
                            currentExpandableItem.hide();
                        }
                    }

                    ExpandableItem expandableItem = (ExpandableItem) layoutManager.getChildAt(currentPosition - layoutManager.findFirstVisibleItemPosition()).findViewWithTag(ExpandableItem.TAG);
                    if (expandableItem.isOpened())
                        expandableItem.hide();
                    else
                        expandableItem.show();
                }
            };
        }
    }
}