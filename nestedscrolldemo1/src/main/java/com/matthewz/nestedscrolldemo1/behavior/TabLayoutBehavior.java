package com.matthewz.nestedscrolldemo1.behavior;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TabLayoutBehavior extends CoordinatorLayout.Behavior<View> {

    private int mImageViewHeight;
    private int mTitleHeight;

    public TabLayoutBehavior() {
    }

    public TabLayoutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof ImageView;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        if(dependency instanceof ImageView) {
            mImageViewHeight = dependency.getMeasuredHeight();
            child.setY(mImageViewHeight);

            mTitleHeight = (int) ((float) dependency.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT * 60);
        }
        return true;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return true;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        if(dyUnconsumed < 0 && child.getY() < mImageViewHeight) {
            float consumedY = mImageViewHeight - child.getY() > -dyUnconsumed ? -dyUnconsumed : mImageViewHeight - child.getY();
            child.setY(child.getY() + consumedY);
        }
//        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if(dy > 0 && child.getY() > mTitleHeight) {
            float consumedY = child.getY() - mTitleHeight > dy ? dy : child.getY() - mTitleHeight;
            child.setY(child.getY() - consumedY);
            consumed[1] = (int) consumedY;
        }
//        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    }
}
