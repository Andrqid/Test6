package com.matthewz.behaviordemo1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

public class HeaderBehavior extends CoordinatorLayout.Behavior<View> {

    public HeaderBehavior() {
    }

    public HeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return true;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        if(dyUnconsumed < 0 && child.getY() < 0) {
            float finalY = child.getY() > dyUnconsumed ? 0 : child.getY() - dyUnconsumed;
            child.setY(finalY);
        }

        //        if(dyUnconsumed < 0 && coordinatorLayout.getScrollY() > 0) {
//            int consumedY = coordinatorLayout.getScrollY() + dyUnconsumed > 0 ? dyUnconsumed : -coordinatorLayout.getScrollY();
//            coordinatorLayout.scrollBy(0, consumedY);
//        }

//        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if(dy > 0 && child.getY() > - child.getMeasuredHeight()) {
            int consumedY = child.getMeasuredHeight() + child.getY() > dy ? dy : (int) (child.getMeasuredHeight() + child.getY());
            child.setY(child.getY() - consumedY);
            consumed[1] = consumedY;
        }

//        if(dy > 0 && coordinatorLayout.getScrollY() < child.getMeasuredHeight()) {
//            int consumedY = child.getMeasuredHeight() - coordinatorLayout.getScrollY() > dy ? dy : child.getMeasuredHeight() - coordinatorLayout.getScrollY();
//            coordinatorLayout.scrollBy(0, consumedY);
//            consumed[1] = consumedY;
//        }
//        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    }

    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    @Override
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }
}
