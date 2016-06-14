package io.github.hendraanggrian.expandablelayoutrecyclerviewsample;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.github.hendraanggrian.expandablelayoutrecyclerview.ExpandableBaseItem;
import io.github.hendraanggrian.expandablelayoutrecyclerview.ExpandableCardItem;
import io.github.hendraanggrian.expandablelayoutrecyclerview.ExpandableLayoutRecyclerView;

/**
 * Created by hendraanggrian on 6/13/16.
 */
public class TestAdapter extends ExpandableLayoutRecyclerView.Adapter<TestAdapter.ViewHolder> {

    private Context context;
    private List<Item> items;

    public TestAdapter(LinearLayoutManager lm, List<Item> items) {
        super(lm);
        this.items = items;
    }

    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.view_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TestAdapter.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final Item item = items.get(position);

        //holder.header.

        holder.expandableLayoutItem.setOnExpandListener(new ExpandableBaseItem.OnExpandListener() {
            @Override
            public void onExpanding() {
                Log.d("TAG", "EXPANDING");
            }

            @Override
            public void onCollapsing() {
                Log.d("TAG", "COLLAPSING");
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends ExpandableLayoutRecyclerView.ViewHolder {
        public ExpandableCardItem expandableLayoutItem;
        public ImageView imageView;
        public TextView textView;

        public ViewHolder(View rowView, View headerView, View contentView) {
            super(rowView, headerView, contentView);

            expandableLayoutItem = (ExpandableCardItem) rowView.findViewById(R.id.row);
            imageView = (ImageView) ((ExpandableBaseItem) headerView).getHeaderLayout().findViewById(R.id.imageView);
            textView = (TextView) ((ExpandableBaseItem) headerView).getHeaderLayout().findViewById(R.id.textView);
        }

        @Override
        public ExpandableBaseItem getItem() {
            return expandableLayoutItem;
        }
    }
}