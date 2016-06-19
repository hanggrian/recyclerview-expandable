package io.github.hendraanggrian.expandablelayoutrecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class ExpandableLayoutRecyclerView extends RecyclerView {

    public ExpandableLayoutRecyclerView(Context context) {
        super(context);
    }

    public ExpandableLayoutRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandableLayoutRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public static abstract class Adapter<VH extends ViewHolder> extends RecyclerView.Adapter<VH> {
        private final LinearLayoutManager layoutManager;
        private int selected = -1;

        public Adapter(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        public LinearLayoutManager getLayoutManager() {
            return layoutManager;
        }

        @Override
        public void onBindViewHolder(final VH holder, int position) {
            if (selected != holder.getAdapterPosition() && holder.getItem().isOpened())
                holder.getItem().hideNow();
            else if (selected == holder.getAdapterPosition() && !holder.getItem().isOpened())
                holder.getItem().showNow();

            holder.getItem().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    performClick(holder);
                }
            });
        }

        private void performClick(ViewHolder holder) {
            selected = holder.getAdapterPosition();

            for (int i = 0; i < layoutManager.getChildCount(); ++i) {
                if (i != (selected - layoutManager.findFirstVisibleItemPosition())) {
                    ExpandableLayoutItem currentExpandableLayout = (ExpandableLayoutItem) layoutManager.getChildAt(i).findViewWithTag(ExpandableLayoutItem.class.getName());
                    currentExpandableLayout.hide();
                }
            }

            ExpandableLayoutItem expandableLayout = (ExpandableLayoutItem) layoutManager.getChildAt(selected - layoutManager.findFirstVisibleItemPosition()).findViewWithTag(ExpandableLayoutItem.class.getName());
            expandableLayout.showOrHide();
        }
    }

    public static abstract class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public abstract ExpandableLayoutItem getItem();
    }
}