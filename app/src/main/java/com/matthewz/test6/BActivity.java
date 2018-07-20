package com.matthewz.test6;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class BActivity extends AppCompatActivity {

    public static final int TRANSITION_TIME = 1000;
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        rg = findViewById(R.id.radiogroup);
    }

    public void jumpToC(View view)  {
        Intent intent = new Intent(this, CActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            switch (rg.getCheckedRadioButtonId()) {
                case R.id.rb_explode:
                    intent.putExtra("transition", "explode");
                    Explode explode = new Explode();
                    explode.setDuration(TRANSITION_TIME);
                    getWindow().setExitTransition(explode);
                    break;
                case R.id.rb_slide:
                    intent.putExtra("transition", "slide");
                    Slide slide = new Slide();
                    slide.setDuration(TRANSITION_TIME);
                    getWindow().setExitTransition(slide);
                    break;
                case R.id.rb_fade:
                    intent.putExtra("transition", "fade");
                    Fade fade = new Fade();
                    fade.setDuration(TRANSITION_TIME);
                    getWindow().setExitTransition(fade);
                    break;
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityOptionsCompat aoc = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    Pair.create(findViewById(R.id.button2), "testTN"),
                    Pair.create(findViewById(R.id.imageView), "share"));
            startActivity(intent, aoc.toBundle());
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
//            }

        }
    }
}
