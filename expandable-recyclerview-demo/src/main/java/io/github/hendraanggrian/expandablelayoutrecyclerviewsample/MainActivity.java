package io.github.hendraanggrian.expandablelayoutrecyclerviewsample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import io.github.hendraanggrian.expandablelayoutrecyclerview.ExpandableLayoutRecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ItemAdapter adapter = new ItemAdapter(new LinearLayoutManager(this));

        final ExpandableLayoutRecyclerView expandableLayoutRecyclerView = (ExpandableLayoutRecyclerView) findViewById(R.id.recyclerView);
        expandableLayoutRecyclerView.setAdapter(adapter);
        expandableLayoutRecyclerView.setLayoutManager(adapter.getLayoutManager());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_View:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://github.com/hendraanggrian/ExpandableLayoutRecyclerView"));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}