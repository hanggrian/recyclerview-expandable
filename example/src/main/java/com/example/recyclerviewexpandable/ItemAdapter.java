package com.example.recyclerviewexpandable;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hendraanggrian.widget.ExpandableItem;
import com.hendraanggrian.widget.ExpandableRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by hendraanggrian on 6/13/16.
 */
public class ItemAdapter extends ExpandableRecyclerView.Adapter<ItemAdapter.ViewHolder> {

    @NonNull private final Context context;
    @NonNull private final List<Item> items;

    public ItemAdapter(@NonNull Context context, @NonNull LinearLayoutManager layoutManager) {
        super(layoutManager);
        this.context = context;
        this.items = new ArrayList<>();
        this.items.add(new Item(R.drawable.ic_test1, "14 Easy Weekend Getaways"));
        this.items.add(new Item(R.drawable.ic_test2, "Why We Travel"));
        this.items.add(new Item(R.drawable.ic_test3, "A Paris Farewell"));
        this.items.add(new Item(R.drawable.ic_test1, "14 Easy Weekend Getaways"));
        this.items.add(new Item(R.drawable.ic_test2, "Why We Travel"));
        this.items.add(new Item(R.drawable.ic_test3, "A Paris Farewell"));
        this.items.add(new Item(R.drawable.ic_test1, "14 Easy Weekend Getaways"));
        this.items.add(new Item(R.drawable.ic_test2, "Why We Travel"));
        this.items.add(new Item(R.drawable.ic_test3, "A Paris Farewell"));
        this.items.add(new Item(R.drawable.ic_test1, "14 Easy Weekend Getaways"));
        this.items.add(new Item(R.drawable.ic_test2, "Why We Travel"));
        this.items.add(new Item(R.drawable.ic_test3, "A Paris Farewell"));
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final ItemAdapter.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        final Item item = items.get(position);
        holder.imageView.setImageDrawable(ContextCompat.getDrawable(context, item.drawable));
        holder.textView.setText(item.title);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked!", Toast.LENGTH_SHORT).show();
            }
        });
        /*holder.item.setOnExpandListener(new ExpandableLayoutDep.OnExpandListener() {
            @Override
            public void onExpanding() {
                Log.d("ExpandableLayout", holder.getAdapterPosition() + " Expading");
            }

            @Override
            public void onCollapsing() {
                Log.d("ExpandableLayout", holder.getAdapterPosition() + " Collapsing");
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ExpandableItem item;
        public ImageView imageView;
        public TextView textView;
        public Button button;

        public ViewHolder(View itemView) {
            super(itemView);
            item = ButterKnife.findById(itemView, R.id.row);
            imageView = ButterKnife.findById(item.getHeaderLayout(), R.id.imageView);
            textView = ButterKnife.findById(item.getHeaderLayout(), R.id.textView);
            button = ButterKnife.findById(item.getContentLayout(), R.id.button);
        }
    }
}