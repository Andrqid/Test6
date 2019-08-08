package com.matthewz.nestedscrolldemo1.behavior;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TitleBehavior extends CoordinatorLayout.Behavior<TextView> {

    private int mImageViewHeight;   // 获取背景图片的高度
    private ArgbEvaluator mArgbEvaluator = new ArgbEvaluator();

    public TitleBehavior() {
    }

    public TitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull TextView child, @NonNull View dependency) {
        return dependency instanceof TabLayout || dependency instanceof ImageView;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull TextView child, @NonNull View dependency) {
        if(dependency instanceof ImageView) {
            mImageViewHeight = dependency.getMeasuredHeight();
        } else {
            float fraction = (mImageViewHeight - dependency.getY()) / (mImageViewHeight - child.getMeasuredHeight());
            int textColor = (int) mArgbEvaluator.evaluate(fraction, Color.parseColor("#FFFFFFFF"), Color.parseColor("#FF000000"));
            child.setTextColor(textColor);

            int backgroundColor = (int) mArgbEvaluator.evaluate(fraction, Color.parseColor("#00FFFFFF"), Color.parseColor("#FFFFFFFF"));
            child.setBackgroundColor(backgroundColor);
        }
        return true;
    }
}
