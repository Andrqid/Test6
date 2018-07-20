package com.matthewz.test6;

import android.animation.Animator;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RevealActivity3 extends AppCompatActivity {

    private View rootView;
    private int x;
    private int y;
    private float endRadius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal3);

        ViewGroup content = findViewById(android.R.id.content);
        rootView = content.getChildAt(0);
        rootView.post(new Runnable() {
            @Override
            public void run() {
                x = getIntent().getIntExtra("x", 0);
                int yy = getIntent().getIntExtra("y", 0);
                y = yy - getStatusBarHeight();
                List<Double> radiusList = new ArrayList<>();
                radiusList.add(Math.hypot(x, y));
                radiusList.add(Math.hypot(rootView.getWidth() - x, y));
                radiusList.add(Math.hypot(x, rootView.getHeight() - y));
                radiusList.add(Math.hypot(rootView.getWidth() - x, rootView.getHeight() - y));
                Collections.sort(radiusList, new Comparator<Double>() {
                    @Override
                    public int compare(Double o1, Double o2) {
                        return (int) (o1 - o2);
                    }
                });
                endRadius = (float) (double) radiusList.get(radiusList.size() - 1);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Animator animator = ViewAnimationUtils.createCircularReveal(rootView, x, y, 0, endRadius);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.setDuration(MainActivity.TRANSITION_TIME);
                    animator.start();
                }
            }
        });
    }

    private int getStatusBarHeight() {
        int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
        return getResources().getDimensionPixelSize(identifier);
    }


    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator animator = ViewAnimationUtils.createCircularReveal(rootView, x, y, endRadius, 0);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(MainActivity.TRANSITION_TIME);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    rootView.setVisibility(View.INVISIBLE);
                    finish();
                    overridePendingTransition(0, 0);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.start();
        }else {
            super.onBackPressed();
        }
    }
}
