package io.github.hendraanggrian.expandablelayoutrecyclerviewsample;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.github.hendraanggrian.expandablelayoutrecyclerview.ExpandableLayoutItem;
import io.github.hendraanggrian.expandablelayoutrecyclerview.ExpandableLayoutRecyclerView;

/**
 * Created by hendraanggrian on 6/13/16.
 */
public class ItemAdapter extends ExpandableLayoutRecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context context;
    private List<Item> items;

    public ItemAdapter(LinearLayoutManager lm) {
        super(lm);
        items = new ArrayList<>();
        items.add(new Item(R.drawable.ic_test1, "14 Easy Weekend Getaways"));
        items.add(new Item(R.drawable.ic_test2, "Why We Travel"));
        items.add(new Item(R.drawable.ic_test3, "A Paris Farewell"));
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
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

        holder.item.setOnExpandListener(new ExpandableLayoutItem.OnExpandListener() {
            @Override
            public void onExpanding() {
                Log.d("ExpandableLayout", holder.getAdapterPosition() + " Expading");
            }

            @Override
            public void onCollapsing() {
                Log.d("ExpandableLayout", holder.getAdapterPosition() + " Collapsing");
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends ExpandableLayoutRecyclerView.ViewHolder {
        public ExpandableLayoutItem item;
        public ImageView imageView;
        public TextView textView;
        public Button button;

        public ViewHolder(View itemView) {
            super(itemView);
            item = (ExpandableLayoutItem) itemView.findViewById(R.id.row);
            imageView = (ImageView) item.getHeaderLayout().findViewById(R.id.imageView);
            textView = (TextView) item.getHeaderLayout().findViewById(R.id.textView);
            button = (Button) item.getContentLayout().findViewById(R.id.button);
        }

        @Override
        public ExpandableLayoutItem getItem() {
            return item;
        }
    }
}