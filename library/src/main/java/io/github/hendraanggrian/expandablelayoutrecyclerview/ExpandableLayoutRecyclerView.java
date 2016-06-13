package io.github.hendraanggrian.expandablelayoutrecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by hendraanggrian on 6/13/16.
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
        public Integer position = -1;

        public abstract LinearLayoutManager getLayoutManager();

        public void performClick(int position) {
            this.position = position;

            for (int index = 0; index < getItemCount(); ++index) {
                if (index != (position - getLayoutManager().findFirstVisibleItemPosition())) {
                    ExpandableLayoutItem currentExpandableLayout = (ExpandableLayoutItem) getLayoutManager().findViewByPosition(index).findViewWithTag(ExpandableLayoutItem.class.getName());
                    currentExpandableLayout.hide();
                }
            }

            ExpandableLayoutItem expandableLayout = (ExpandableLayoutItem) getLayoutManager().findViewByPosition(position - getLayoutManager().findFirstVisibleItemPosition()).findViewWithTag(ExpandableLayoutItem.class.getName());

            if (expandableLayout.isOpened())
                expandableLayout.hide();
            else
                expandableLayout.show();
        }
    }
}