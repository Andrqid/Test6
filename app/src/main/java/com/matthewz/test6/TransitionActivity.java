package com.matthewz.test6;

import android.graphics.Rect;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeTransform;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class TransitionActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean isScene2 = false;
    private Scene scene1;
    private Scene scene2;

    private Button mChangeBtn;
    private Button mChangeTransformBtn;
    private Button mChangeClipBoundsBtn;
    private ImageView iv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        ViewGroup rootView = (ViewGroup) ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            scene1 = Scene.getSceneForLayout(rootView, R.layout.activity_transition, this);
            scene2 = Scene.getSceneForLayout(rootView, R.layout.activity_transition_alternative, this);
            TransitionManager.go(scene1);

            initViews();

        }
    }

    @Override
    public void onClick(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.go(isScene2 ? scene1 : scene2, new ChangeBounds());
            isScene2 = !isScene2;

            initViews();
        }
    }

    private void initViews() {
        mChangeBtn = findViewById(R.id.button3);
        mChangeBtn.setOnClickListener(this);
        iv2 = findViewById(R.id.iv2);
        mChangeTransformBtn = findViewById(R.id.button4);
        mChangeTransformBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    TransitionManager.beginDelayedTransition((ViewGroup) iv2.getParent(), new ChangeTransform());
                }

                if(90 == iv2.getRotation()) {
                    iv2.setRotation(0);
                    iv2.setRotationX(0);
                    iv2.setRotationY(0);
                    iv2.setScaleX(1f);
                    iv2.setScaleY(1f);
                }else {
                    iv2.setRotation(90);
                    iv2.setRotationX(30);
                    iv2.setRotationY(-30);
                    iv2.setScaleX(2f);
                    iv2.setScaleY(2f);
                }
            }
        });
        mChangeClipBoundsBtn = findViewById(R.id.button5);
        iv2.post(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    iv2.setClipBounds(null);
                }
            }
        });
        mChangeClipBoundsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    TransitionManager.beginDelayedTransition((ViewGroup) iv2.getParent(), new ChangeClipBounds());
                    Rect rect = iv2.getClipBounds();
                    Log.e("clipBound", null == rect ? "null" : rect.left + "-" + rect.top + "-" + rect.right + "-" + rect.bottom);
                    if(null == iv2.getClipBounds()) {
                        iv2.setClipBounds(new Rect(100, 100, 200, 200));
                    }else {
                        iv2.setClipBounds(null);
                    }
                }
            }
        });
    }
}
