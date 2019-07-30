package com.matthewz.toastutildemo1;

import android.app.Application;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.StringRes;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtil {
    private static final int CODE_SHOW_TOAST = 806;
    private static final int CODE_DISMISS = 807;

    private static Application sApplication;
    private static Handler sHandler;
    private static NotificationManagerCompat sNotificationManagerCompat;

    // Toast
    private static Toast sToast;

    // PopupWindow
    private static WindowManager.LayoutParams sLayoutParams;
    private static WindowManager sWindowManager;
    private static TextView sShowTv;
    private static boolean sIsShowInWm;

    public static void init(Application application) {
        sApplication = application;
        sNotificationManagerCompat = NotificationManagerCompat.from(sApplication);
        initShowTv();

//        sApplication.registerActivityLifecycleCallbacks(new SimpleActivityLifecycleCallbacks() {
//            @Override
//            public void onActivityResumed(Activity activity) {
//                super.onActivityResumed(activity);
//            }
//        });

        sHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case CODE_SHOW_TOAST:
                        String text = (String) msg.obj;
                        if (isNotificationPermissionEnabled()) { // 通知权限可用，用普通的 Toast 即可
                            Log.e("ToastUtil", "toast");
                            if (null == sToast) {
                                sToast = new Toast(sApplication);
                                sToast.setView(sShowTv);
                                sToast.setGravity(Gravity.CENTER, 0, 0);
                            }
                            sShowTv.setText(text);
                            sToast.show();
                        } else {
                            Log.e("ToastUtil", "popup window");
                            showPopupWindow(text);
                        }
                        break;
                    case CODE_DISMISS:
                        if(null != sWindowManager && sIsShowInWm) {
                            sWindowManager.removeView(sShowTv);
                            sIsShowInWm = false;
                        }
                        break;
                }

                return true;
            }
        });
    }

    public static void show(String text) {
        sHandler.obtainMessage(CODE_SHOW_TOAST, text).sendToTarget();
    }

    public static void show(@StringRes int resId) {
        sHandler.obtainMessage(CODE_SHOW_TOAST, sApplication.getString(resId)).sendToTarget();
    }

    private static void initShowTv() {
        if(null == sShowTv) {
            sShowTv = (TextView) LayoutInflater.from(sApplication).inflate(R.layout.layout_toast, null);
        }
    }

    /*
    初始化 PopupWindow 所需的参数
     */
    private static void initLayoutParams() {
        if(null == sLayoutParams) {
            sLayoutParams = new WindowManager.LayoutParams();
            sLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            sLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            sLayoutParams.format = PixelFormat.TRANSLUCENT;
            // 找不到 com.android.internal.R.style.Animation_Toast
//             sLayoutParams.windowAnimations = com.android.internal.R.style.Animation_Toast;
            sLayoutParams.windowAnimations = R.style.ToastPopupWindowStyle;
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
                sLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                sLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            }
            sLayoutParams.setTitle("Toast");
            sLayoutParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        }
    }

    private static void showPopupWindow(String text) {
        initLayoutParams();
        if(null == sWindowManager) {
//                sWindowManager = (WindowManager) sCurrentShowActivity.getSystemService(Context.WINDOW_SERVICE);
            sWindowManager = (WindowManager) sApplication.getSystemService(Context.WINDOW_SERVICE);
        }

        sShowTv.setText(text);
        if(!sIsShowInWm) {
            sWindowManager.addView(sShowTv, sLayoutParams);
            sIsShowInWm = true;
        } else {
            // 如果已经添加到 WindowManager 中，那么不用再调用 updateViewLayout 方法
//            sWindowManager.updateViewLayout(sS);
        }

        sHandler.sendMessageDelayed(sHandler.obtainMessage(CODE_DISMISS, false), 3000);
    }

    /*
    判断当前应用的通知权限是否可用
     */
    private static boolean isNotificationPermissionEnabled() {
        return sNotificationManagerCompat.areNotificationsEnabled();
    }

    private static class InnerHolder {
        private static final ToastUtil INSTANCE = new ToastUtil();
    }
}