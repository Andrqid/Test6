package com.matthewz.toastutildemo1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.et_text);
    }

    public void showToast(View view) {
        ToastUtil.show(mEditText.getText().toString());
    }

    public void goToSecond(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }
}
