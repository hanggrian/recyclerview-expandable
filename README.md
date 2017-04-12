![logo](/art/logo.png) Expandable RecyclerView
==============================================
RecyclerView implementation of [traex's ExpandableLayout](https://github.com/traex/ExpandableLayout).

![demo](/art/demo.gif)

Download
--------
Library are hosted in [jCenter](https://bintray.com/hendraanggrian/maven/expandable-recyclerview).

```gradle
dependencies {
    compile 'com.hendraanggrian:recyclerview-expandable:0.3.0'
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

Create your ExpandableRecyclerView.Adapter:

```java
public class MyAdapter extends ExpandableRecyclerView.Adapter<MyAdapter.ViewHolder> {

    public MyAdapter(LinearLayoutManager lm) {
        super(lm);
        ...
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position); // make sure to call this line
        ...
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ...
    }
}
```

Have an ExpandableRecyclerView somewhere in your app.

```xml
<com.hendraanggrian.widget.ExpandableRecyclerView
    android:id="@+id/recyclerView"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```

Then pass LinearLayoutManager to the adapter:

```java
LinearLayoutManager layoutManager = new LinearLayoutManager(this);
TestAdapter adapter = new TestAdapter(layoutManager);

ExpandableLayoutRecyclerView expandableRecyclerView = (ExpandableLayoutRecyclerView) findViewById(R.id.recyclerView);
expandableRecyclerView.setLayoutManager(layoutManager);
expandableRecyclerView.setAdapter(adapter);
```

Using stock `RecyclerView`?

```java
LinearLayoutManager layoutManager = new LinearLayoutManager(this);
TestAdapter adapter = new TestAdapter(layoutManager);

RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
recyclerView.addOnScrollListener(new ExpandableRecyclerView.OnExpandableScrollListener());
recyclerView.setLayoutManager(layoutManager);
recyclerView.setAdapter(adapter);
```