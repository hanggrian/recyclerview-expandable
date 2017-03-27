package io.github.hendraanggrian.expandablerecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashSet;
import java.util.Set;

import io.github.hendraanggrian.expandablelayoutrecyclerview.R;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class ExpandableLayoutRecyclerView extends RecyclerView {

    private ExpandMode mode;

    public ExpandableLayoutRecyclerView(Context context) {
        super(context);
    }

    public ExpandableLayoutRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ExpandableLayoutRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableLayoutRecyclerView);
        final int modeId = typedArray.getInt(R.styleable.ExpandableLayoutRecyclerView_expandMode, 0);
        mode = ExpandMode.from(modeId);
        typedArray.recycle();
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        super.setAdapter(adapter);
        ((Adapter) getAdapter()).mode = mode;
    }

    public static abstract class Adapter<VH extends ViewHolder> extends RecyclerView.Adapter<VH> {
        private ExpandMode mode;

        private final LinearLayoutManager layoutManager;
        private Integer singleSelected = -1;
        private Set<Integer> anySelected = new HashSet<>();

        public Adapter(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        public LinearLayoutManager getLayoutManager() {
            return layoutManager;
        }

        @Override
        public void onBindViewHolder(final VH holder, int position) {
            switch (mode) {
                case SINGLE:
                    if (singleSelected != holder.getAdapterPosition() && holder.getItem().isOpened())
                        holder.getItem().hideNow();
                    else if (singleSelected == holder.getAdapterPosition() && !holder.getItem().isOpened())
                        holder.getItem().showNow();

                    holder.getItem().setOnCollapsedByUser(new ExpandableLayoutItem.OnCollapsedByUser() {
                        @Override
                        public void onCollapsed() {
                            singleSelected = -1;
                        }
                    });
                    holder.getItem().getHeaderLayout().setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            singleSelected = holder.getAdapterPosition();

                            for (int i = 0; i < layoutManager.getChildCount(); ++i) {
                                if (i != (singleSelected - layoutManager.findFirstVisibleItemPosition())) {
                                    ExpandableLayoutItem currentExpandableLayout = (ExpandableLayoutItem) layoutManager.getChildAt(i).findViewWithTag(ExpandableLayoutItem.class.getName());
                                    currentExpandableLayout.hide();
                                }
                            }

                            ExpandableLayoutItem expandableLayout = (ExpandableLayoutItem) layoutManager.getChildAt(singleSelected - layoutManager.findFirstVisibleItemPosition()).findViewWithTag(ExpandableLayoutItem.class.getName());
                            expandableLayout.showOrHide();
                        }
                    });
                    break;

                case ANY:
                    if (!anySelected.contains(holder.getAdapterPosition()) && holder.getItem().isOpened())
                        holder.getItem().hideNow();
                    else if (anySelected.contains(holder.getAdapterPosition()) && !holder.getItem().isOpened())
                        holder.getItem().showNow();

                    holder.getItem().setOnCollapsedByUser(new ExpandableLayoutItem.OnCollapsedByUser() {
                        @Override
                        public void onCollapsed() {
                            anySelected.remove(holder.getAdapterPosition());
                        }
                    });
                    holder.getItem().getHeaderLayout().setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            anySelected.add(holder.getAdapterPosition());

                            holder.getItem().showOrHide();
                        }
                    });
                    break;
            }
        }
    }

    public static abstract class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public abstract ExpandableLayoutItem getItem();
    }
}