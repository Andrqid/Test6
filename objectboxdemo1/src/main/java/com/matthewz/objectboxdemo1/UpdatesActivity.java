package com.matthewz.objectboxdemo1;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.matthewz.objectboxdemo1.databinding.ActivityUpdatesBinding;
import com.matthewz.objectboxdemo1.entity.Fruitage;

import java.util.List;
import java.util.Random;

import io.objectbox.Box;

public class UpdatesActivity extends AppCompatActivity {

    private ObservableField<String> mFruitageName = new ObservableField<>("");
    private QueryUtil mQueryUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mQueryUtil = new QueryUtil();
        ActivityUpdatesBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_updates);
        binding.setFruitName(mFruitageName);
        binding.setUtil(mQueryUtil);
    }

    public static class QueryUtil {

        private static final String TAG = "QueryUtil";

        private Random mRandom = new Random();
        private Box<Fruitage> mFruitageBox = BaseApplication.sApplication.mBoxStore.boxFor(Fruitage.class);
        
        public void add(String type) {
            Fruitage fruit = new Fruitage(type, String.valueOf(mRandom.nextInt(5)), mRandom.nextInt(10));
            mFruitageBox.put(fruit);
            Toast.makeText(BaseApplication.sApplication, "插入成功", Toast.LENGTH_SHORT).show();
        }

        public void query() {
            List<Fruitage> all = mFruitageBox.getAll();
            if(!all.isEmpty()) {
                for (Fruitage fruit : all) {
                    Log.e(TAG, fruit.toString());
                }
            }
        }
    }
}
