package com.matthewz.test6;


import android.animation.Animator;
import android.app.Fragment;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RevealFragment extends Fragment {

    private int x;
    private int y;
    private float endRadius;

    private boolean isAnimating = false;

    public RevealFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.fragment_reveal, container, false);
        inflate.setVisibility(View.INVISIBLE);
        inflate.post(new Runnable() {
            @Override
            public void run() {
                List<Double> radiusList = new ArrayList<>();
                radiusList.add(Math.hypot(x, y));
                radiusList.add(Math.hypot(inflate.getWidth() - x, y));
                radiusList.add(Math.hypot(x, inflate.getHeight() - y));
                radiusList.add(Math.hypot(inflate.getWidth() - x, inflate.getHeight() - y));
                Collections.sort(radiusList, new Comparator<Double>() {
                    @Override
                    public int compare(Double o1, Double o2) {
                        return (int) (o1 - o2);
                    }
                });
                endRadius = (float) (double) radiusList.get(radiusList.size() - 1);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Animator animator = ViewAnimationUtils.createCircularReveal(inflate, x, y, 0, endRadius);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.setDuration(MainActivity.TRANSITION_TIME);
                    animator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            inflate.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isAnimating = false;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    isAnimating = true;
                    animator.start();
                }
            }
        });
        return inflate;
    }

    public void setAnimXAndY(int x, int y) {
        this.x = x;
        this.y = y - getStatusBarHeight();
    }

    private int getStatusBarHeight() {
        int identifier = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
        return Resources.getSystem().getDimensionPixelSize(identifier);
    }

    /**
     * @return true表示Fragment响应了回退键
     */
    public void onBackPressed() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(isAnimating)
                return;

            Animator animator = ViewAnimationUtils.createCircularReveal(getView(), x, y, endRadius, 0);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(MainActivity.TRANSITION_TIME);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    isAnimating = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isAnimating = false;
                    getView().setVisibility(View.INVISIBLE);
                    getActivity().getFragmentManager().beginTransaction().remove(RevealFragment.this).commit();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.start();
        } else {
            getActivity().getFragmentManager().beginTransaction().remove(this).commit();
        }
    }
}
