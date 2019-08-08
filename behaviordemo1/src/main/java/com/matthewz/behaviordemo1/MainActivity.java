package com.matthewz.behaviordemo1;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("测试 " + i);
        }
        new TestAdapter(list).bindToRecyclerView(mRecyclerView);
//        TransitionManager.beginDelayedTransition((ViewGroup) findViewById(android.R.id.content), new ChangeBounds());
    }

    private static class TestAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public TestAdapter(@Nullable List<String> data) {
            super(android.R.layout.simple_list_item_1, data);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, String item) {
            ((TextView) helper.itemView).setText(item);
        }
    }
}
