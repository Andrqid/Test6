package com.matthewz.nestedscrolldemo1.behavior;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerBehavior extends CoordinatorLayout.Behavior<View> {

    public ViewPagerBehavior() {
    }

    public ViewPagerBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof TabLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        child.setY(dependency.getY() + dependency.getMeasuredHeight());
        ViewGroup.LayoutParams lp = child.getLayoutParams();
        lp.height = (int) (parent.getMeasuredHeight() - dependency.getY() + dependency.getMeasuredHeight());
        child.setLayoutParams(lp);
        return true;
    }
}
