package com.matthewz.objectboxdemo1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import io.objectbox.android.ObjectBoxLiveData;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private final String[] ITEMS = {"增删改查", "To One And To Many", "Updates"};
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.listView);
        mListView.setAdapter(new ArrayAdapter<String>(this, R.layout.layout_menu_item, ITEMS));
        mListView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        switch (position) {
            case 0:
                intent.setClass(this, QueriesActivity.class);
                break;
            case 1:
                intent.setClass(this, RelationsActivity.class);
                break;
            case 2:
                intent.setClass(this, UpdatesActivity.class);
                break;
        }

        if(null != intent.getComponent()){
            startActivity(intent);
        }
    }
}
