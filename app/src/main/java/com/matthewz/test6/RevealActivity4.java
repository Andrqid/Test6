package com.matthewz.test6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class RevealActivity4 extends AppCompatActivity {


    private ListView listView;

    private int clickX;
    private int clickY;

    private RevealFragment revealFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal2);

        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            list.add("条目" + i);
        }

        listView = findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.layout_menu_item, list));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(null == revealFragment) {
                    revealFragment = new RevealFragment();
                }
                revealFragment.setAnimXAndY(clickX, clickY);

                getFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, revealFragment, RevealFragment.class.getSimpleName())
                        .commit();

            }
        });
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    clickX = (int) event.getRawX();
                    clickY = (int) event.getRawY();
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (null != revealFragment && null != getFragmentManager().findFragmentByTag(RevealFragment.class.getSimpleName())) {
            revealFragment.onBackPressed();
        }else {
            super.onBackPressed();
        }
    }
}
