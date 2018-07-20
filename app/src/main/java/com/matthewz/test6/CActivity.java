package com.matthewz.test6;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.matthewz.test6.MainActivity.TRANSITION_TIME;

public class CActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String transition = getIntent().getStringExtra("transition");
            ViewGroup content = findViewById(android.R.id.content);
            View childAt = content.getChildAt(0);
//            ((TextView)findViewById(R.id.textView)).setText(transition);
            switch (transition) {
                case "explode":
                    childAt.setBackgroundResource(android.R.color.holo_blue_dark);
                    Explode explode = new Explode();
                    explode.setDuration(TRANSITION_TIME);
                    getWindow().setEnterTransition(explode);
                    break;
                case "slide":
                    childAt.setBackgroundResource(android.R.color.holo_green_dark);
                    Slide slide = new Slide(Gravity.BOTTOM);
                    slide.setDuration(TRANSITION_TIME);
                    getWindow().setEnterTransition(slide);
                    break;
                case "fade":
                    childAt.setBackgroundResource(android.R.color.holo_orange_dark);
                    Fade fade = new Fade();
                    fade.setDuration(TRANSITION_TIME);
                    getWindow().setEnterTransition(fade);
                    break;
            }
        }
    }
}
