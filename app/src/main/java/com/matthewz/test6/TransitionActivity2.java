package com.matthewz.test6;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class TransitionActivity2 extends AppCompatActivity {

    private ImageView iv3;
    private ImageView iv4;
    private ImageView iv5;
    private ImageView iv6;

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition2);

        iv3 = findViewById(R.id.imageView3);
        iv4 = findViewById(R.id.imageView4);
        iv5 = findViewById(R.id.imageView5);
        iv6 = findViewById(R.id.imageView6);

        btn = findViewById(R.id.button6);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Transition transition = TransitionInflater.from(TransitionActivity2.this).inflateTransition(R.transition.my_test);
                    TransitionManager.beginDelayedTransition((ViewGroup) iv3.getParent(), transition);
                }
                if(iv4.getVisibility() == View.GONE) {
                    iv3.setPadding(0, 0, 0, 0);
                    iv4.setVisibility(View.VISIBLE);
                    iv5.setVisibility(View.VISIBLE);
                    iv6.setVisibility(View.VISIBLE);
                }else {
                    iv3.setPadding(100, 100, 100, 100);
                    iv4.setVisibility(View.GONE);
                    iv5.setVisibility(View.GONE);
                    iv6.setVisibility(View.GONE);
                }
            }
        });
    }
}
