package com.matthewz.totalpaydemo1;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.matthewz.totalpaydemo1.databinding.LItemMainBinding;
import com.matthewz.totalpaydemo1.databinding.LTailMainBinding;

import java.util.List;

import static com.matthewz.totalpaydemo1.Constant.MAIN_TYPE_CONTENT;
import static com.matthewz.totalpaydemo1.Constant.MAIN_TYPE_TAIL;
import static com.matthewz.totalpaydemo1.Constant.MAIN_TYPE_TITLE;

public class TestAdapter extends BaseMultiItemQuickAdapter<NewMutableLiveData, BaseViewHolder> {

    private AppCompatActivity mActivity;
    private MainViewModel mViewModel;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public TestAdapter(AppCompatActivity activity, List<NewMutableLiveData> data, MainViewModel vm) {
        super(data);

        mActivity = activity;
        mViewModel = vm;

        addItemType(MAIN_TYPE_TITLE, R.layout.l_title_main);
        addItemType(MAIN_TYPE_CONTENT, R.layout.l_item_main);
        addItemType(MAIN_TYPE_TAIL, R.layout.l_tail_main);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewMutableLiveData item) {
        switch (item.getItemType()) {
            case MAIN_TYPE_CONTENT:
                if(item.getValue() instanceof Good) {
                    LItemMainBinding binding = (LItemMainBinding) helper.itemView.getTag();
                    binding.setLifecycleOwner(mActivity);
                    binding.setGood(item);
                }
                break;
            case MAIN_TYPE_TAIL:
                LTailMainBinding binding = (LTailMainBinding) helper.itemView.getTag();
                binding.setLifecycleOwner(mActivity);
                binding.setVm(mViewModel);
                break;
        }
    }

//    public static class MainSetter {
//        @BindingAdapter("textInt")
//        public static void setInt(EditText et, int v) {
//            et.setText(String.valueOf(v));
//        }
//
//        @InverseBindingAdapter(attribute = "textInt", event = "textIntAttrChanged")
//        public static int inverseSetInt(EditText et) {
//            String s = et.getText().toString();
//            if(TextUtils.isEmpty(s))
//                return 0;
//            return Integer.parseInt(s);
//        }
//
//        @BindingAdapter(value = {"textIntAttrChanged"})
//        public static void setTextIntAttrChanged(EditText et, InverseBindingListener listener) {
//
//        }
//
//        @BindingAdapter("textFloat")
//        public static void setFloat(EditText et, float v) {
//            et.setText(String.valueOf(v));
//        }
//
//        @InverseBindingAdapter(attribute = "textFloat", event = "textFloatAttrChanged")
//        public static float inverseSetFloat(EditText et) {
//            String s = et.getText().toString();
//            if(TextUtils.isEmpty(s))
//                return 0;
//            return Float.parseFloat(s);
//        }
//
//        @BindingAdapter(value = {"textFloatAttrChanged"})
//        public static void setTextFloatAttrChanged(EditText et, InverseBindingListener listener) {
//
//        }
//    }
}
