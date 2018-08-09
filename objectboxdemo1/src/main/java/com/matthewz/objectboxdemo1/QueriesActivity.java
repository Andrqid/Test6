package com.matthewz.objectboxdemo1;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.matthewz.objectboxdemo1.entity.Teacher;
import com.matthewz.objectboxdemo1.entity.Teacher_;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.objectbox.Box;

public class QueriesActivity extends AppCompatActivity implements View.OnClickListener {

    //Query
    private Button mQueryBtn;
    private ListView mListView;
    private ArrayAdapter<Teacher> mAdapter;
    private Box<Teacher> mTeacherBox;
    private List<Teacher> mQueryList = new ArrayList<>();

    //Add
    private Random mRandom;
    private EditText mNameEt;
    private EditText mAgeEt;
    private EditText mCourseEt;
    private RadioGroup mGenderRg;
    private Button mAddBtn;

    //Delete
    private EditText mDeleteEt;
    private Button mDeleteBtn;

    //Modify
    private EditText mQueryIdEt;
    private Button mModifyBtn;
    private Teacher mQueryTeacher;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 101:
                    String id = mQueryIdEt.getText().toString();
                    if(!TextUtils.isEmpty(id)) {
                        mQueryTeacher = mTeacherBox.get(Long.parseLong(id));
                        if(null != mQueryTeacher) {
                            mNameEt.setText(mQueryTeacher.getName());
                            mAgeEt.setText(String.valueOf(mQueryTeacher.getAge()));
                            mGenderRg.check(mQueryTeacher.isMale() ? R.id.rb_male : R.id.rb_female);
                            mCourseEt.setText(mQueryTeacher.getCourse());
                        }
                    }
                    break;
            }

            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queries);

        mTeacherBox = BaseApplication.sApplication.mBoxStore.boxFor(Teacher.class);
        mQueryBtn = findViewById(R.id.btn_query);
        mQueryBtn.setOnClickListener(this);
        mListView = findViewById(R.id.listView_squery);
        mListView.setAdapter(mAdapter = new ArrayAdapter<Teacher>(this, R.layout.layout_menu_item, mQueryList));

        mRandom = new Random();
        mNameEt = findViewById(R.id.et_name);
        mAgeEt = findViewById(R.id.et_age);
        mCourseEt = findViewById(R.id.et_course);
        mGenderRg = findViewById(R.id.rg_gender);
        mAddBtn = findViewById(R.id.btn_add);
        mAddBtn.setOnClickListener(this);

        mDeleteEt = findViewById(R.id.et_delete);
        mDeleteBtn = findViewById(R.id.btn_delete);
        mDeleteBtn.setOnClickListener(this);

        mQueryIdEt = findViewById(R.id.et_query_id);
        mQueryIdEt.addTextChangedListener(new AfterTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                mHandler.removeMessages(101);
                mHandler.sendEmptyMessageDelayed(101, 500);
            }
        });
        mModifyBtn = findViewById(R.id.btn_modify);
        mModifyBtn.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_query:
//                mTeacherBox.query().build().property(Teacher_.age);
//                mTeacherBox.query().equal(Teacher_.name, "王二小")
//                        .greater(Teacher_.age, 30)
//                        .less(Teacher_.age, 60)
//                        .sort()
                List<Teacher> teachers = mTeacherBox.query().build().find();
                if(teachers.isEmpty()){
                    Toast.makeText(this, "Teachers is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    mQueryList.clear();
                    mQueryList.addAll(teachers);
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.btn_add:
                String name = mNameEt.getText().toString();
                int age = 0;
                try {
                    age = Integer.parseInt(mAgeEt.getText().toString());
                }catch (NumberFormatException e) {

                }
                boolean isMale = mGenderRg.getCheckedRadioButtonId() == R.id.rb_male;
                String course = mCourseEt.getText().toString();
                Teacher teacher = new Teacher(String.valueOf(mRandom.nextLong()), TextUtils.isEmpty(name) ? "缺省" : name, age, isMale, course, "吃饭睡觉打豆豆");
                mTeacherBox.put(teacher);
                Toast.makeText(this, "插入完毕", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_delete:
                long id = -1;
                try {
                    id = Long.parseLong(mDeleteEt.getText().toString());
                }catch (NumberFormatException e) {

                }
                if(-1 != id) {
                    mTeacherBox.remove(id);
                    Toast.makeText(this, "已删除", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "请输入有效id", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_modify:
                String name2 = mNameEt.getText().toString();
                int age2 = 0;
                try {
                    age2 = Integer.parseInt(mAgeEt.getText().toString());
                }catch (NumberFormatException e) {

                }
                boolean isMale2 = mGenderRg.getCheckedRadioButtonId() == R.id.rb_male;
                String course2 = mCourseEt.getText().toString();
                mQueryTeacher.setName(name2);
                mQueryTeacher.setAge(age2);
                mQueryTeacher.setMale(isMale2);
                mQueryTeacher.setCourse(course2);
                mQueryTeacher.setHobby("怎么又去打豆豆");
                mQueryTeacher.setServerId(String.valueOf(mRandom.nextLong()));
                mTeacherBox.put(mQueryTeacher);
                Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
