package com.matthewz.totalpaydemo1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class ManageActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayList<String> mAllGoods;
    private ArrayList<String> mChosenGoods;
    private ArrayAdapter<String> mAdapter;

    private AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        mAllGoods = getIntent().getStringArrayListExtra("all_good");
        mChosenGoods = getIntent().getStringArrayListExtra("chosen_good");
        mListView = findViewById(R.id.lv_manage);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mAllGoods);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (mChosenGoods.contains(mAllGoods.get(position))) {
                    Toast.makeText(ManageActivity.this, "主列表中已存在该条目，请先清除它", Toast.LENGTH_LONG).show();
                } else {
                    new AlertDialog
                            .Builder(ManageActivity.this)
                            .setMessage("要删除该条目？")
                            .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mAllGoods.remove(position);
                                    setResult(RESULT_OK);
                                    mAdapter.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .create()
                            .show();
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manage_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (null == mDialog) {
            final View inflate = getLayoutInflater().inflate(R.layout.dialog_add, null);
            mDialog = new AlertDialog.Builder(this)
                    .setView(inflate)
                    .setPositiveButton("添加", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText et = inflate.findViewById(R.id.et_add);
                            String input = et.getText().toString();
                            if (TextUtils.isEmpty(input)) {
                                Toast.makeText(ManageActivity.this, "请输入有效内容", Toast.LENGTH_SHORT).show();
                            } else if (mAllGoods.contains(input)) {
                                Toast.makeText(ManageActivity.this, "该商品已存在", Toast.LENGTH_SHORT).show();
                            } else {
                                mAllGoods.add(input);
                                setResult(RESULT_OK);
                                et.setText("");

                                mDialog.dismiss();
                            }
                        }
                    })
                    .setNegativeButton("取消", null)
                    .create();
        }
        mDialog.show();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        LinkedHashSet<String> set = new LinkedHashSet<>(mAllGoods);
        ShareUtil.getInstance().saveGoods(set);
        Toast.makeText(this, "保存完毕", Toast.LENGTH_SHORT).show();

        super.onBackPressed();
    }
}
