package io.github.hendraanggrian.expandablelayoutrecyclerviewsample;

import io.github.hendraanggrian.expandablelayoutrecyclerview.Expandable;

/**
 * Created by hendraanggrian on 14/06/16.
 */
public class Item implements Expandable {

    public int drawable;
    public String title;
    public boolean expanded;

    public Item() {
    }

    public Item(int drawable, String title) {
        this.drawable = drawable;
        this.title = title;
    }

    @Override
    public boolean isExpanded() {
        return expanded;
    }

    @Override
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}