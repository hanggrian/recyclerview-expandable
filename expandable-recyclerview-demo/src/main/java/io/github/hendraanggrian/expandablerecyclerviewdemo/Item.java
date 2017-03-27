package io.github.hendraanggrian.expandablerecyclerviewdemo;

/**
 * Created by hendraanggrian on 14/06/16.
 */
public class Item {

    public int drawable;
    public String title;
    public boolean expanded;

    public Item() {
    }

    public Item(int drawable, String title) {
        this.drawable = drawable;
        this.title = title;
    }
}