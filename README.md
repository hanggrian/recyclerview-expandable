ExpandableRecyclerView
======================
RecyclerView implementation of [traex's ExpandableLayout](https://github.com/traex/ExpandableLayout).

![demo](/art/demo.gif)

Download
--------
Library are hosted in [jCenter](https://bintray.com/hendraanggrian/maven/expandable-recyclerview).
```gradle
dependencies {
    compile 'com.hendraanggrian:recyclerview-expandable:0.3.2'
}
```

Usage
-----
Create a row of your RecyclerView:
```xml
<com.hendraanggrian.widget.ExpandableItem
    android:id="@+id/row"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:duration="500"
    app:layoutHeader="@layout/view_content"
    app:layoutContent="@layout/view_header"/>
```

Create your adapter, which must extend ExpandableRecyclerView.Adapter:
```java
public class MyAdapter extends ExpandableRecyclerView.Adapter<MyAdapter.ViewHolder> {

    public MyAdapter(LinearLayoutManager layout) {
        super(layout);
        ...
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        ...
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ...
    }
}
```

Have an RecyclerView somewhere in your app.
```xml
<android.support.v7.widget.RecyclerView
    android:id="@+id/recyclerview"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```

Then pass LinearLayoutManager to the adapter:
```java
LinearLayoutManager layout = new LinearLayoutManager(this);
MyAdapter adapter = new MyAdapter(layout);

RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
recyclerView.setLayoutManager(layout);
recyclerView.setAdapter(adapter);
```