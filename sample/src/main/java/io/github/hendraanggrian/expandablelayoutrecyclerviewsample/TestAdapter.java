package io.github.hendraanggrian.expandablelayoutrecyclerviewsample;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private List<String> list;

    public TestAdapter(LinearLayoutManager lm) {
        super(lm);
        list = new ArrayList<>();
        list.add("Loren");
        list.add("Ipsum");
        list.add("Hello");
        list.add("World");
        list.add("Android");
        list.add("Totally");
        list.add("Rocks");
        list.add("Loren");
        list.add("Ipsum");
        list.add("Hello");
        list.add("World");
        list.add("Android");
        list.add("Totally");
        list.add("Rocks");
    }

    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TestAdapter.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);

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

        final TextView header_text = (TextView) holder.expandableLayoutItem.getHeaderLayout().findViewById(R.id.header_text);
        header_text.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends ExpandableLayoutRecyclerView.ViewHolder {
        public ExpandableCardItem expandableLayoutItem;

        public ViewHolder(View itemView) {
            super(itemView);
            expandableLayoutItem = (ExpandableCardItem) itemView.findViewById(R.id.row);
        }

        @Override
        public ExpandableBaseItem getItem() {
            return expandableLayoutItem;
        }
    }
}