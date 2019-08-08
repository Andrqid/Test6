package com.matthewz.nestedscrolldemo1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewPagerNestedScrollView extends FrameLayout implements NestedScrollingParent2 {
    private static final String TAG = "ViewPagerNested";

    private int mBgHeight;

    private ImageView mBgIv;
    private TextView mTitleTv;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    public ViewPagerNestedScrollView(Context context) {
        this(context, null);
    }

    public ViewPagerNestedScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ViewPagerNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_nested_scroll_test, this, true);

        mBgIv = findViewById(R.id.iv_bg);
        mTitleTv = findViewById(R.id.tv_title);
        mTabLayout = findViewById(R.id.tab);
        mViewPager = findViewById(R.id.viewpager);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        mBgHeight = (int) ((float)dm.widthPixels / 1024 * 576);

        ViewGroup.LayoutParams lp = mBgIv.getLayoutParams();
        lp.height = mBgHeight;
        mBgIv.setLayoutParams(lp);

        String[] stringArray = getResources().getStringArray(R.array.tabsName);
        mViewPager.setAdapter(new TestFragmentPagerAdapter(((AppCompatActivity) getContext()).getSupportFragmentManager(), stringArray));
        mTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < stringArray.length; i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if(null != tab) {
                tab.setText(stringArray[i]);
            }
        }
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View view, @NonNull View view1, int i, int i1) {
        return i1 != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View view, @NonNull View view1, int i, int i1) {

    }

    @Override
    public void onStopNestedScroll(@NonNull View view, int i) {

    }

    @Override
    public void onNestedScroll(@NonNull View view, int i, int i1, int i2, int i3, int i4) {
        Log.e(TAG, "onNestedScroll: " + i3 + ", consumed :" + i1);
        if(i3 < 0) {    // 向下划
            if(mTabLayout.getTranslationY() < mBgHeight - mTitleTv.getHeight()) {
                int addTransitionY = mBgHeight - mTitleTv.getHeight() - mTabLayout.getTranslationY() + i3 > 0 ? -i3 : (int) (mBgHeight - mTitleTv.getHeight() - mTabLayout.getTranslationY());
                mTabLayout.setTranslationY(mTabLayout.getTranslationY() + addTransitionY);
                mViewPager.setTranslationY(mViewPager.getTranslationY() + addTransitionY);
            }
        }
    }

    @Override
    public void onNestedPreScroll(@NonNull View view, int i, int i1, @NonNull int[] ints, int i2) {

    }
}
