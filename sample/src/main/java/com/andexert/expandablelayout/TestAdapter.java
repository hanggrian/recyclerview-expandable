package com.andexert.expandablelayout;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.github.hendraanggrian.expandablelayoutrecyclerview.ExpandableLayoutItem;

import java.util.ArrayList;
import java.util.List;

import io.github.hendraanggrian.expandablelayoutrecyclerview.ExpandableLayoutRecyclerView;

/**
 * Created by hendraanggrian on 6/13/16.
 */
public class TestAdapter extends ExpandableLayoutRecyclerView.Adapter<TestAdapter.ViewHolder> {

    private Context context;
    private List<String> list;
    private LinearLayoutManager lm;

    public TestAdapter(LinearLayoutManager lm) {
        this.lm = lm;
        list = new ArrayList<>();
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
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.view_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TestAdapter.ViewHolder holder, final int position) {
        final TextView header_text = (TextView) holder.row.getHeaderLayout().findViewById(R.id.header_text);

        header_text.setText(list.get(position));

        holder.row.setTag(ExpandableLayoutItem.class.getName());
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return lm;
    }

    public static class ViewHolder extends ExpandableLayoutRecyclerView.ViewHolder {
        public ExpandableLayoutItem row;

        public ViewHolder(View itemView) {
            super(itemView);
            row = (ExpandableLayoutItem) itemView.findViewById(R.id.row);
        }
    }
}