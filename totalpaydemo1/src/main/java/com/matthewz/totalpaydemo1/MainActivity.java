package com.matthewz.totalpaydemo1;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.matthewz.totalpaydemo1.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mViewModel;
    private RecyclerView mRv;
    private TestAdapter2 mAdapter;
    private ActivityMainBinding mBinding;

    private Paint mPaint = new Paint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPaint.setColor(Color.parseColor("#dddddd"));

        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setLifecycleOwner(this);

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mRv = findViewById(R.id.rv_main);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TestAdapter2(this, mViewModel.mGoodInChosen, mViewModel);
        mAdapter.bindToRecyclerView(mRv);
        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                if(0 == position || mAdapter.getData().size() - 1 == position) {
                    return false;
                }

                new AlertDialog
                        .Builder(MainActivity.this)
                        .setMessage("要删除该条目？")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mViewModel.removeGood(position);
                                mAdapter.notifyItemRemoved(position);
                                mAdapter.notifyItemChanged(mAdapter.getData().size() - 1);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create()
                        .show();
                return true;
            }
        });
        mRv.addItemDecoration(new RecyclerView.ItemDecoration() {

            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(c, parent, state);

                LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();
                int first = manager.findFirstVisibleItemPosition();
                int last = manager.findLastVisibleItemPosition();
                for(int i = first; i <= last; i++) {
                    if(i == 0)
                        continue;

                    View child = manager.getChildAt(i);
                    int top = child.getTop();
                    if(top < 0) {
                        continue;
                    } else {
                        c.drawRect(0, top - 10 < 0 ? 0 : top - 10, parent.getWidth(), top, mPaint);
                    }
                }
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if(0 != parent.getChildAdapterPosition(view)) {
                    outRect.top = 10;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_main:
                final String[] strings = mViewModel.chooseGoodToAdd();
                if (null != strings) {
                    new AlertDialog
                            .Builder(this)
                            .setItems(strings, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mViewModel.addItem(strings[which]);
                                    mAdapter.notifyDataSetChanged();
                                }
                            })
                            .create()
                            .show();
                }
                break;
            case R.id.manage_main:
                Intent intent = mViewModel.generateIntent();
                intent.setClass(this, ManageActivity.class);
                startActivityForResult(intent, 101);
                break;
            case R.id.clear_main:
                new AlertDialog
                        .Builder(MainActivity.this)
                        .setMessage("要清除所有条目？")
                        .setPositiveButton("清除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mViewModel.clearAllChosen();
                                mAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create()
                        .show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mViewModel.onActivityResult(requestCode, resultCode, data);
    }
}
