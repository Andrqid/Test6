package com.matthewz.totalpaydemo1;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainViewModel extends ViewModel {

    private final ArrayList<String> mAllGoodsList = new ArrayList<>();
    private final ArrayList<String> mChosenList = new ArrayList<>();
    public final List<NewMutableLiveData> mGoodInChosen = new ArrayList<>();

    public MainViewModel() {
        Good title = new Good(Constant.MAIN_TYPE_TITLE);
        NewMutableLiveData<Good> titleData = new NewMutableLiveData<Good>();
        titleData.setValue(title);
        Good tail = new Good(Constant.MAIN_TYPE_TAIL);
        NewMutableLiveData<Good> tailData = new NewMutableLiveData<>();
        tailData.setValue(tail);
        mGoodInChosen.add(titleData);
        mGoodInChosen.add(tailData);

        getAllGoods();
    }

    private void getAllGoods() {
        List<String> goods = ShareUtil.getInstance().getGoods();
        if(null != goods) {
            mAllGoodsList.clear();
            mAllGoodsList.addAll(goods);
        }
    }

    public String[] chooseGoodToAdd() {
        if(mAllGoodsList.isEmpty()) {
            makeToast("您还未添加商品！");
        } else {
            if(mChosenList.size() >= mAllGoodsList.size()) {
                makeToast("已无可选商品！");
            } else {
                ArrayList<String> list = new ArrayList<>();
                for (String s : mAllGoodsList) {
                    if(!mChosenList.contains(s)) {
                        list.add(s);
                    }
                }
                return list.toArray(new String[list.size()]);
            }
        }
        return null;
    }

    public void addItem(String goodName) {
        Good good = new Good();
        good.setName(goodName);
        NewMutableLiveData<Good> data = new NewMutableLiveData<>();
        data.setValue(good);
        mGoodInChosen.add(mGoodInChosen.size() - 1, data);

        mChosenList.add(goodName);
    }

    public void removeGood(int position) {
        NewMutableLiveData remove = mGoodInChosen.remove(position);
        mChosenList.remove(((Good)remove.getValue()).getName());
    }

    public void clearAllChosen() {
        NewMutableLiveData data = mGoodInChosen.get(0);
        NewMutableLiveData data2 = mGoodInChosen.get(mGoodInChosen.size() - 1);
        mGoodInChosen.clear();
        mGoodInChosen.add(data);
        mGoodInChosen.add(data2);

        mChosenList.clear();
    }

    public Intent generateIntent() {
        return new Intent()
                .putStringArrayListExtra("all_good", mAllGoodsList)
                .putStringArrayListExtra("chosen_good", mChosenList);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            getAllGoods();
        }
    }

    public String calculateTotal() {
        float total = 0;
        for (NewMutableLiveData<Good> liveData : mGoodInChosen) {
            Good good = liveData.getValue();
            assert null != good;
            if(good.getType() == Constant.MAIN_TYPE_CONTENT) {
                int amou = TextUtils.isEmpty(good.getAmount()) ? 0 : Integer.parseInt(good.getAmount());
                float price = TextUtils.isEmpty(good.getUnitPrice()) ? 0 : Float.parseFloat(good.getUnitPrice());
                total +=  amou * price;
            }
        }
        return String.valueOf(total);
    }

    public String calculateSingle(Good good) {
        int amou = TextUtils.isEmpty(good.getAmount()) ? 0 : Integer.parseInt(good.getAmount());
        float price = TextUtils.isEmpty(good.getUnitPrice()) ? 0 : Float.parseFloat(good.getUnitPrice());
        return String.valueOf(amou * price);
    }



    private void makeToast(String content) {
        Toast.makeText(BaseApplication.getInstance(), content, Toast.LENGTH_LONG).show();
    }
}
