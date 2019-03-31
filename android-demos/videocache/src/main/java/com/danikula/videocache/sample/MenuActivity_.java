package com.danikula.videocache.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MenuActivity_ extends FragmentActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    ListView listView;
    Button btnClear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findView();
    }

    private void findView() {
        listView = (ListView) findViewById(R.id.listView);
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, buildListData());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

        btnClear = (Button) findViewById(R.id.cleanCacheButton);
        btnClear.setOnClickListener(this);
    }


    @NonNull
    private List<ListEntry> buildListData() {
        return Arrays.asList(
                new ListEntry("Single Video", SingleVideoActivity_.class),
                new ListEntry("Multiple Videos", MultipleVideosActivity_.class),
                new ListEntry("Video Gallery with pre-caching", VideoGalleryActivity_.class),
                new ListEntry("Shared Cache", SharedCacheActivity_.class)
        );
    }


    @Override
    public void onClick(View view) {
        try {
            //存放的目录：/sdcard/Android/data/com.danikula.videocache.sample/cache/video-cache
            Utils.cleanVideoCacheDir(this);
        } catch (IOException e) {
            Log.e(null, "AA Error cleaning cache", e);
            Toast.makeText(this, "Error cleaning cache", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        ListEntry item = (ListEntry) listView.getAdapter().getItem(position);
        startActivity(new Intent(this, item.activityClass));
    }

    private static final class ListEntry {

        private final String title;
        private final Class activityClass;

        public ListEntry(String title, Class activityClass) {
            this.title = title;
            this.activityClass = activityClass;
        }

        @Override
        public String toString() {
            return title;
        }
    }

}
