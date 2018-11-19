//package com.matthewz.totalpaydemo1;
//
//import android.databinding.BindingAdapter;
//import android.databinding.InverseBindingAdapter;
//import android.text.TextUtils;
//import android.widget.EditText;
//
//import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//import com.matthewz.totalpaydemo1.databinding.LItemMainBinding;
//
//import java.util.List;
//
//import static com.matthewz.totalpaydemo1.Constant.*;
//
//public class MainItemAdapter extends BaseMultiItemQuickAdapter<NewMutableLiveData, BaseViewHolder> {
//    /**
//     * Same as QuickAdapter#QuickAdapter(Context,int) but with
//     * some initialization data.
//     *
//     * @param data A new list is created out of this one to avoid mutable list
//     */
//    public MainItemAdapter(List<NewMutableLiveData> data) {
//        super(data);
//
//        addItemType(MAIN_TYPE_TITLE, R.layout.l_title_main);
//        addItemType(MAIN_TYPE_CONTENT, R.layout.l_item_main);
//        addItemType(MAIN_TYPE_TAIL, R.layout.l_tail_main);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, NewMutableLiveData item) {
//        switch (item.getItemType()) {
//            case MAIN_TYPE_CONTENT:
//                LItemMainBinding binding = (LItemMainBinding) helper.itemView.getTag();
//                binding.setGood();
//                break;
//            case MAIN_TYPE_TAIL:
//
//                break;
//        }
//    }
//}
