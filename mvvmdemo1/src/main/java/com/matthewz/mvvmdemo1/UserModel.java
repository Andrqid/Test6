package com.matthewz.mvvmdemo1;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import static com.matthewz.mvvmdemo1.MainActivity.TAG;

public class UserModel extends ViewModel {


    public final MutableLiveData<String> mName;
    public final MutableLiveData<String> mPassphrase;

    public UserModel() {
        mName = new MutableLiveData<String>() {
            @Override
            protected void onActive() {
                super.onActive();
                Log.e(TAG, "onActive() called");
            }

            @Override
            protected void onInactive() {
                super.onInactive();
                Log.e(TAG, "onInactive() called");
            }
        };
        mName.setValue("");
        mPassphrase = new MutableLiveData<>();
        mPassphrase.setValue("");
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.e(TAG, "onCleared() called");
    }

    public void login() {
        final MyApplication instance = MyApplication.sInstance;
        if (TextUtils.isEmpty(mName.getValue())) {
            Toast.makeText(instance, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mPassphrase.getValue())) {
            Toast.makeText(instance, "请输入登录密码", Toast.LENGTH_SHORT).show();
            return;
        }
//        Observable
//                .create(new ObservableOnSubscribe<String>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//
//                    }
//                })
//                .subscribeOn()
        Toast.makeText(instance, "登录成功", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "name : " + mName.getValue() + ", passphrase : " + mPassphrase.getValue());
        // TODO 如何启动一个新的Activity？？
        AlertDialog alertDialog = new AlertDialog.Builder(instance, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setMessage("启动Activity吗？")
                .setPositiveButton("启动", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        instance.startActivity(new Intent(instance, SecondActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                })
                .setNegativeButton("取消", null)
                .create();
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.show();
    }

    public void generateRandomNameAndPass() {
        Random random = new Random();
        char[] nameC = new char[5];
        for (int i = 0; i < nameC.length; i++) {
            while (97 > (nameC[i] = (char) random.nextInt(122))) {

            }
        }
        mName.setValue(String.valueOf(nameC));

        char[] passC = new char[5];
        for (int i = 0; i < nameC.length; i++) {
            while (97 > (passC[i] = (char) random.nextInt(122))) {

            }
        }
        mPassphrase.setValue(String.valueOf(passC));
    }

    public void onClic(View view) {
        Toast.makeText(MyApplication.sInstance, "skr", Toast.LENGTH_SHORT).show();
    }
}
