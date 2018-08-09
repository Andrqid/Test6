package com.matthewz.mvvmdemo1;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.matthewz.mvvmdemo1.databinding.ActivityMainBinding;

import java.util.Observable;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private ActivityMainBinding mBinding;
    private UserModel mUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserModel = ViewModelProviders.of(this, new ViewModelProvider.NewInstanceFactory()).get(UserModel.class);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //使用LiveData作为UI双向交互的实体对象，为了LiveData中的数据改变时能及时更新到UI，需要给Binding设置LifecycleOwner
        mBinding.setLifecycleOwner(this);
        mBinding.setUserModel(mUserModel);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume() called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy() called");
    }
}
