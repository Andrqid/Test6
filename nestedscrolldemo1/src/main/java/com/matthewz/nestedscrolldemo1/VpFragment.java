package com.matthewz.nestedscrolldemo1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class VpFragment extends Fragment {

    private String mTag = "缺省";

    public String getTagName() {
        return mTag;
    }

    private RecyclerView mRecyclerView;
    private JustAAdapter mAdapter;



    public static VpFragment newInstance(@NonNull String name) {
        Bundle bundle = new Bundle();
        bundle.putString("tag", name);
        VpFragment vpFragment = new VpFragment();
        vpFragment.setArguments(bundle);
        return vpFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(null != getArguments()) {
            mTag = getArguments().getString("tag");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.layout_vp_fragment, null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(mTag + " " + i);
        }
        mAdapter = new JustAAdapter(list);
        mAdapter.bindToRecyclerView(mRecyclerView);
        return mRecyclerView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private static class JustAAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public JustAAdapter(@Nullable List<String> data) {
            super(android.R.layout.simple_list_item_1, data);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, String item) {
            ((TextView) helper.itemView).setText(item);
        }
    }
}
