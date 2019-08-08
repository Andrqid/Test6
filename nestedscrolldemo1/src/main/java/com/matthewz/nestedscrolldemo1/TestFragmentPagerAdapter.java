package com.matthewz.nestedscrolldemo1;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Map;
import java.util.TreeMap;

public class TestFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTabsName;
    private Map<Integer, VpFragment> mMap = new TreeMap<>();

    public TestFragmentPagerAdapter(FragmentManager fm, String[] tabsName) {
        super(fm);

        mTabsName = tabsName;
    }

    @Override
    public Fragment getItem(int i) {
        VpFragment vpFragment = mMap.get(i);
        if(null == vpFragment) {
            vpFragment = VpFragment.newInstance(mTabsName[i]);
            mMap.put(i, vpFragment);
        }
        return vpFragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        VpFragment vpFragment = mMap.get(position);
        if(null != vpFragment) {
            return vpFragment.getTagName();
        }
        return super.getPageTitle(position);
    }
}
