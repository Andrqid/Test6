package com.matthewz.behaviordemo1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class RecyclerViewBehavior extends CoordinatorLayout.Behavior<View> {
    private static final String TAG = "RecyclerViewBehavior";

    public RecyclerViewBehavior() {
    }

    public RecyclerViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency.getId() == R.id.tv_header;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        Log.e(TAG, "onDependentViewChanged : " + dependency.getY());
        float childY = dependency.getY() + dependency.getMeasuredHeight();
        child.setY(childY);
        ViewGroup.LayoutParams lp = child.getLayoutParams();
        lp.height = (int) (parent.getMeasuredHeight() - childY);
        child.setLayoutParams(lp);
        return true;
    }

//    @Override
//    public boolean onMeasureChild(@NonNull CoordinatorLayout parent, @NonNull View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
//        Log.e(TAG, "onMeasureChild : ");
//        DisplayMetrics dm = parent.getResources().getDisplayMetrics();
//        int headerHeight = (int) ((float) dm.densityDpi / DisplayMetrics.DENSITY_DEFAULT * 50);
//        ViewGroup.LayoutParams childLayoutParams = child.getLayoutParams();
//        childLayoutParams.height = View.MeasureSpec.getSize(parentHeightMeasureSpec) + parent.getScrollY() - headerHeight;
//        child.setLayoutParams(childLayoutParams);
//        parent.onMeasureChild(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, headerHeight - parent.getScrollY());
//        return true;
//    }
}
