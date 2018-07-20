package com.matthewz.test6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private final String[] items = {"Enter & Exit Transition of Activity", "Share Elements", "Reveal View", "Reveal Activity", "Reveal Fragment", "Transition of View", "Transition of View from Transition xml"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        listView = findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.layout_menu_item, items));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, BActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, RevealActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, RevealActivity2.class));
                break;
            case 4:
                startActivity(new Intent(this, RevealActivity4.class));
                break;
            case 5:
                startActivity(new Intent(this, TransitionActivity.class));
                break;
            case 6:
                startActivity(new Intent(this, TransitionActivity2.class));
                break;
        }
    }
}
