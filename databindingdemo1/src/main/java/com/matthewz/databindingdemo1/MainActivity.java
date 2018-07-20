package com.matthewz.databindingdemo1;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.matthewz.databindingdemo1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ObservableField<String> name = new ObservableField<>("Jackey");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this ,R.layout.activity_main);
        binding.setExamer(new Examer());
        binding.setPersonName(name);
    }

    public class Examer {
        public boolean getBoolean() {
            return true;
        }

        public void getStringNameValue() {
            Toast.makeText(MainActivity.this, name.get(), Toast.LENGTH_SHORT).show();
        }
    }
}
