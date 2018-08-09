package com.matthewz.mvvmdemo1;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.paging.PositionalDataSource;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.common.base.Optional;
import com.matthewz.mvvmdemo1.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";

    private ActivitySecondBinding binding;
    private MutableLiveData<String> content1 = new MutableLiveData<>();
    private MutableLiveData<String> content2 = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second);
        binding.setLifecycleOwner(this);
        binding.setContent1(content1);
        binding.setContent2(content2);
//        content1.observeForever(new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                myOnChanged(s);
//            }
//        });
//        content2.observeForever(new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                myOnChanged(s);
//            }
//        });

//        mListView = findViewById(R.id.listView);

        SampleData sd = null;
        Optional<SampleData> optional = Optional.fromNullable(sd);
        if(optional.isPresent()) {

        }
        Optional<SampleData> absent = Optional.absent();

        if(absent.isPresent()) {

        }


    }

    public void logContents(View view) {
        Log.e("logContents", content1.getValue() + " : " + content2.getValue());
    }

    public void myOnChanged(String s) {
        Log.e(TAG, "onChanged() called with: s = [" + s + "]");
        boolean enable = content1.getValue().equals(content2.getValue()) && content1.getValue().length() >=3 && content1.getValue().length() <= 10;
        binding.btnTest.setEnabled(enable);
    }

    public static class MyDataSource extends PositionalDataSource<SampleData> {

        @Override
        public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<SampleData> callback) {

        }

        @Override
        public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<SampleData> callback) {

        }
    }

    public static class SampleData {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
