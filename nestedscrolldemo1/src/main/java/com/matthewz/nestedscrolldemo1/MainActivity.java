package com.matthewz.nestedscrolldemo1;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView mBgIv;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBgIv = findViewById(R.id.iv_bg);
        mTabLayout = findViewById(R.id.tab);
        mViewPager = findViewById(R.id.viewpager);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int mBgHeight = (int) ((float)dm.widthPixels / 1024 * 576);
        ViewGroup.LayoutParams lp = mBgIv.getLayoutParams();
        lp.height = mBgHeight;
        mBgIv.setLayoutParams(lp);


        String[] stringArray = getResources().getStringArray(R.array.tabsName);
        mViewPager.setAdapter(new TestFragmentPagerAdapter(getSupportFragmentManager(), stringArray));
        mTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < stringArray.length; i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if(null != tab) {
                tab.setText(stringArray[i]);
            }
        }
    }
}
