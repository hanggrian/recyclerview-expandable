package io.github.hendraanggrian.expandablelayoutrecyclerviewsample;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
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

    public TestAdapter(LinearLayoutManager lm) {
        super(lm);
        items = new ArrayList<>();
        items.add(new Item(R.drawable.ic_test1, "14 Easy Weekend Getaways"));
        items.add(new Item(R.drawable.ic_test2, "Why We Travel"));
        items.add(new Item(R.drawable.ic_test3, "A Paris Farewell"));
    }

    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_row, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(TestAdapter.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final Item item = items.get(position);

        holder.imageView.setImageDrawable(ContextCompat.getDrawable(context, item.drawable));
        holder.textView.setText(item.title);

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

        public ViewHolder(View itemView) {
            super(itemView);
            expandableLayoutItem = (ExpandableCardItem) itemView.findViewById(R.id.row);
            imageView = (ImageView) ((ExpandableBaseItem) itemView).getHeaderLayout().findViewById(R.id.imageView);
            textView = (TextView) ((ExpandableBaseItem) itemView).getHeaderLayout().findViewById(R.id.textView);
        }

        @Override
        public ExpandableBaseItem getItem() {
            return expandableLayoutItem;
        }
    }
}