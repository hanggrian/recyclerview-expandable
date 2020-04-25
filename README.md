[![download](https://api.bintray.com/packages/hendraanggrian/recyclerview/recyclerview-expandable/images/download.svg)](https://bintray.com/hendraanggrian/recyclerview/recyclerview-expandable/_latestVersion)
[![build](https://travis-ci.com/hendraanggrian/recyclerview-expandable.svg)](https://travis-ci.com/hendraanggrian/recyclerview-expandable)
[![license](https://img.shields.io/github/license/hendraanggrian/recyclerview-paginated)](http://www.apache.org/licenses/LICENSE-2.0)

Expandable RecyclerView
=======================
RecyclerView implementation of [traex's ExpandableLayout](https://github.com/traex/ExpandableLayout).

Download
--------
```gradle
repositories {
    google()
    jcenter()
}

dependencies {
    compile "com.hendraanggrian.recyclerview:recyclerview-expandable:$version"
}
```

Usage
-----
![demo][demo]

Create a row of your RecyclerView:
```xml
<com.hendraanggrian.recyclerview.widget.ExpandableItem
    android:id="@+id/row"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:duration="500"
    app:layoutHeader="@layout/view_content"
    app:layoutContent="@layout/view_header"/>
```

Create your adapter, which must extend `ExpandableRecyclerView.Adapter`:
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

Have an `ExpandableRecyclerView` somewhere in your app, regular `RecyclerView` works too.
```xml
<com.hendraanggrian.recyclerview.widget.ExpandableRecyclerView
    android:id="@+id/recyclerView"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```

Then pass LinearLayoutManager to the adapter:
```java
LinearLayoutManager layout = new LinearLayoutManager(this);
RecyclerView.Adapter adapter = new MyAdapter(layout);

recyclerView.setLayoutManager(layout);
recyclerView.setAdapter(adapter);
```

[demo]: /art/demo.gif
