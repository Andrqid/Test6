package com.matthewz.test6;

import android.animation.Animator;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

public class RevealActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal);

        imageView = findViewById(R.id.imageView2);
        imageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View parent = (View) imageView.getParent();
            final View tv2 = findViewById(R.id.tv2);
            DisplayMetrics dm = getResources().getDisplayMetrics();
            int centerX = imageView.getLeft() + imageView.getWidth() / 2;
            int centerY = imageView.getTop() + imageView.getHeight()/2;
            Animator animator = ViewAnimationUtils.createCircularReveal(tv2, centerX, centerY, 0, (float) Math.hypot(centerX, centerY));
            animator.setDuration(1500);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    tv2.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

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
            Toast.makeText(this, "your phone's OS version is under 5.0", Toast.LENGTH_SHORT).show();
        }
    }

    private int getStaturBarHeight() {
        int id = getResources().getIdentifier("status_bar_height", "dimen", getPackageName());
        return getResources().getDimensionPixelSize(id);
    }
}
