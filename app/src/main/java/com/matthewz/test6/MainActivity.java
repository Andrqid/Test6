package com.matthewz.test6;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int TRANSITION_TIME = 1000;
    private Button btn;
    private TextView tv;
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.button);
        tv = findViewById(R.id.textview);
        rg = findViewById(R.id.radiogroup);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    TransitionManager.beginDelayedTransition((ViewGroup) findViewById(android.R.id.content), new AutoTransition());
                    ChangeBounds changeBounds = new ChangeBounds();
                    changeBounds.setDuration(2000);
                    TransitionManager.beginDelayedTransition((ViewGroup) findViewById(android.R.id.content), changeBounds);
                }

                if(30 == tv.getPaddingBottom()) {
                    tv.setPadding(300, 300, 300, 300);
                }else {
                    tv.setPadding(30, 30, 30, 30);
                }
            }
        });
    }

    public void jumpToA(View view)  {
        Intent intent = new Intent(this, AActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            switch (rg.getCheckedRadioButtonId()) {
                case R.id.rb_explode:
                    intent.putExtra("transition", "explode");
                    Explode explode = new Explode();
                    explode.setDuration(TRANSITION_TIME);
                    //让状态栏不参与转场动画
                    explode.excludeTarget(android.R.id.statusBarBackground, true);
                    getWindow().setExitTransition(explode);
                    break;
                case R.id.rb_slide:
                    intent.putExtra("transition", "slide");
                    Slide slide = new Slide();
                    slide.setDuration(TRANSITION_TIME);
                    slide.excludeTarget(android.R.id.statusBarBackground, true);
                    getWindow().setExitTransition(slide);
                    break;
                case R.id.rb_fade:
                    intent.putExtra("transition", "fade");
                    Fade fade = new Fade();
                    fade.setDuration(TRANSITION_TIME);
                    getWindow().setExitTransition(fade);

                    Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.my_test);
                    getWindow().setExitTransition(transition);
                    break;
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
//            }
        }
    }
}
