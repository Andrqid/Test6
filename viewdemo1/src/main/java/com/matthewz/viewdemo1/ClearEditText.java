package com.matthewz.viewdemo1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ClearEditText extends android.support.v7.widget.AppCompatEditText {
    private Drawable mClearDrawable;
    private Drawable mClearDrawablePressed;
    private GestureDetector mDetector;
    private List<OnFocusChangeListenerOuter> mFocusChangeListenerOuters = new ArrayList<>();

    public ClearEditText(Context context) {
        super(context);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDetector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                float x = e.getX();
                if(x > getWidth() - getTotalPaddingRight() && x < getWidth() - getPaddingRight()) {
                    setText("");
                    return true;
                }
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
//        mDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
//            @Override
//            public boolean onSingleTapConfirmed(MotionEvent e) {
//                float x = e.getX();
//                if(x > getWidth() - getTotalPaddingRight() && x < getWidth() - getPaddingRight()) {
//                    setText("");
//                    return true;
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onDoubleTap(MotionEvent e) {
//                return false;
//            }
//
//            @Override
//            public boolean onDoubleTapEvent(MotionEvent e) {
//                return false;
//            }
//        });
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(hasFocus()) {
                    setClearDrawable(TextUtils.isEmpty(getText().toString()) ? null : mClearDrawable);
                }
            }
        });
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setClearDrawable(hasFocus && !TextUtils.isEmpty(getText().toString()) ? mClearDrawable : null);
                if(!mFocusChangeListenerOuters.isEmpty()) {
                    for (OnFocusChangeListenerOuter listenerOuter : mFocusChangeListenerOuters) {
                        listenerOuter.onFocusChange(hasFocus);
                    }
                }
            }
        });
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        if(getOnFocusChangeListener() == null) {
            super.setOnFocusChangeListener(l);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        if(hasFocus()) {
            boolean drawablePressed = x > getWidth() - getTotalPaddingRight() && x < getWidth() - getPaddingRight() && event.getAction() != MotionEvent.ACTION_UP;
            Drawable drawableToShow = drawablePressed ? mClearDrawablePressed : mClearDrawable;
            setClearDrawable(drawableToShow);

            if (drawablePressed) {
                setLongClickable(false);
            } else if (MotionEvent.ACTION_UP == event.getAction()) {
                setLongClickable(true);
            }

            if(mDetector.onTouchEvent(event)) {
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if(null == mClearDrawable) {
            mClearDrawable = getClearDrawable2(false);
            setClearDrawable(mClearDrawable);

            mClearDrawablePressed = getClearDrawable2(true);
        }

    }

    @Override
    public void setCompoundDrawables(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        if(null == right || mClearDrawable == right || mClearDrawablePressed == right) {
            super.setCompoundDrawables(left, top, right, bottom);
        }
    }

    public void addOnFocusChangeListener(OnFocusChangeListenerOuter listenerOuter) {
        if(null != listenerOuter) {
            mFocusChangeListenerOuters.add(listenerOuter);
        }
    }

    public void removeOnFoucsChangeListener(OnFocusChangeListenerOuter listenerOuter) {
        if(null != listenerOuter && !mFocusChangeListenerOuters.isEmpty()) {
            mFocusChangeListenerOuters.remove(listenerOuter);
        }
    }

    /**
     * 获取清除图标
     * @param statePressed
     * @return
     */
    private Drawable getClearDrawable(boolean statePressed) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor(statePressed ? "#AAAAAA" : "#DDDDDD"));

        float height = getTextSize() * 1.2f;
        Bitmap bitmap = Bitmap.createBitmap((int) height, (int) height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawCircle(height / 2, height / 2, height / 3, paint);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(height / 12);
        float i = height / 3 / 2;
        float d = height / 3 * 2;
        canvas.drawLine(i + d / 4, i + d / 4, i + d / 4 * 3, i + d / 4 *3, paint);
        canvas.drawLine(i + d / 4, i + d / 4 * 3, i + d / 4 *3, i + d / 4, paint);
        BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        return drawable;
    }

    private Drawable getClearDrawable2(boolean statePressed) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor(statePressed ? "#66000000" : "#33000000"));

        float height = getTextSize() * 1.2f;
        Bitmap bitmap = Bitmap.createBitmap((int) height, (int) height, Bitmap.Config.ALPHA_8);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.parseColor("#00000000"));
        canvas.drawCircle(height / 2, height / 2, height / 3, paint);
        paint.setColor(Color.parseColor("#00000000"));
        paint.setStrokeWidth(height / 12);
        float i = height / 3 / 2;
        float d = height / 3 * 2;
        canvas.drawLine(i + d / 4, i + d / 4, i + d / 4 * 3, i + d / 4 *3, paint);
        canvas.drawLine(i + d / 4, i + d / 4 * 3, i + d / 4 *3, i + d / 4, paint);
        BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        return drawable;
    }

    /**
     * 设置清除图标的显隐或者样式
     * @param drawable
     */
    private void setClearDrawable(Drawable drawable) {
        Drawable[] drawables = getCompoundDrawables();
        if(drawable != drawables[2]) {
            setCompoundDrawables(drawables[0], drawables[1], drawable, drawables[3]);
        }
    }

    interface OnFocusChangeListenerOuter {
        void onFocusChange(boolean hasFocus);
    }
}
