package com.matthewz.totalpaydemo1;

import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.matthewz.totalpaydemo1.databinding.LItemMain2Binding;
import com.matthewz.totalpaydemo1.databinding.LItemMainBinding;
import com.matthewz.totalpaydemo1.databinding.LTailMain2Binding;
import com.matthewz.totalpaydemo1.databinding.LTailMainBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.matthewz.totalpaydemo1.Constant.MAIN_TYPE_CONTENT;
import static com.matthewz.totalpaydemo1.Constant.MAIN_TYPE_TAIL;
import static com.matthewz.totalpaydemo1.Constant.MAIN_TYPE_TITLE;

public class TestAdapter2 extends BaseMultiItemQuickAdapter<NewMutableLiveData, BaseViewHolder> {

    private AppCompatActivity mActivity;
    private MainViewModel mViewModel;

    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy年 MM月 dd日");
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public TestAdapter2(AppCompatActivity activity, List<NewMutableLiveData> data, MainViewModel vm) {
        super(data);

        mActivity = activity;
        mViewModel = vm;

        addItemType(MAIN_TYPE_TITLE, R.layout.l_title_main);
        addItemType(MAIN_TYPE_CONTENT, R.layout.l_item_main_2);
        addItemType(MAIN_TYPE_TAIL, R.layout.l_tail_main_2);

        String[][][] sArr = new String[4][][];
//        sArr[0] = new int[3];
    }

    @Override
    protected void convert(final BaseViewHolder helper, final NewMutableLiveData item) {
        switch (item.getItemType()) {
            case MAIN_TYPE_CONTENT:
                if(item.getValue() instanceof Good) {
                    LItemMain2Binding binding = (LItemMain2Binding) helper.itemView.getTag();
                    binding.setLifecycleOwner(mActivity);
                    binding.setGood(item);

                    EditText et1 = helper.getView(R.id.et_amount);
                    EditText et2 = helper.getView(R.id.et_price);
                    TextWatcher textWatcher = new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            helper.setText(R.id.tv_subtotal, mViewModel.calculateSingle((Good) item.getValue()));
                            notifyItemChanged(mData.size() - 1);
                        }
                    };
                    et1.addTextChangedListener(textWatcher);
                    et2.addTextChangedListener(textWatcher);
                }
                break;
            case MAIN_TYPE_TAIL:
                helper.setText(R.id.tv_total, "总计：" + mViewModel.calculateTotal());
                helper.setText(R.id.tv_time, "当前日期：" + mFormat.format(new Date()));
                break;
        }
    }

}
