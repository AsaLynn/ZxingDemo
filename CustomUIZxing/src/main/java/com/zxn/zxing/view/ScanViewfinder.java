package com.zxn.zxing.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.zxn.zxing.DisplayUtil;
import com.zxn.zxing.R;
import com.zxn.zxing.camera.CameraManager;

/**
 * 扫描框的取景器.
 * Created by zxn on 2019/6/14.
 */
public class ScanViewfinder extends RelativeLayout {


    protected View ivCaptureScanLine;
    protected RelativeLayout rlCaptureCrop;
    private int mScanSpeed = 15;

    public ScanViewfinder(Context context) {
        this(context, null);
    }

    public ScanViewfinder(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScanViewfinder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initAttributeSet(attrs);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_scan_viewfinder, this, true);
        rlCaptureCrop = (RelativeLayout) findViewById(R.id.rl_capture_crop);
        if (getBackground() == null) {
            rlCaptureCrop.setBackgroundResource(R.drawable.capture);
        }
        ivCaptureScanLine = findViewById(R.id.iv_capture_scan_line);

    }

    private void initAttributeSet(AttributeSet attrs) {

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ScanViewfinder);
        if (null != typedArray) {
//            Drawable scanBackground = typedArray.getDrawable(R.styleable.ScanViewfinder_scanBackground);
//            if (scanBackground != null) {
//                rlCaptureCrop.setBackgroundDrawable(scanBackground);
//            }

            Drawable scanDrawable = typedArray.getDrawable(R.styleable.ScanViewfinder_scanBitmap);
            if (scanDrawable != null) {
                scanDrawable.setBounds(0, 0, scanDrawable.getMinimumWidth(), scanDrawable.getMinimumHeight());
                ivCaptureScanLine.setBackgroundDrawable(scanDrawable);
            } else {
                ivCaptureScanLine.setBackgroundResource(R.drawable.scan_line);
            }

            mScanSpeed = typedArray.getInt(R.styleable.ScanViewfinder_scanSpeed, 15);

            typedArray.recycle();
        }

        ivCaptureScanLine.setAnimation(initAnimation());

//        CameraManager.FRAME_WIDTH = getWidth() == 0 ? DisplayUtil.screenWidthPx / 2 : getWidth();
//        CameraManager.FRAME_HEIGHT = getHeight() == 0 ? DisplayUtil.screenWidthPx / 2 : getHeight();
        Log.i("ScanViewfinder", "getWidth: " + getWidth());
        Log.i("ScanViewfinder", "getWidth: " + getWidth());
        ViewGroup.LayoutParams params = getLayoutParams();
        if (params instanceof MarginLayoutParams) {
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) params;
            CameraManager.FRAME_MARGINTOP = marginLayoutParams.topMargin;
        }
    }

    private Animation initAnimation() {
        TranslateAnimation animation = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0.9f);
        animation.setDuration(mScanSpeed * 100);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setInterpolator(new LinearInterpolator());
        return animation;
    }

//    public void startScan() {
//        ivCaptureScanLine.setAnimation(initAnimation());
//    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            CameraManager.FRAME_WIDTH = width;
        } else {
            CameraManager.FRAME_WIDTH = DisplayUtil.screenWidthPx / 2;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            CameraManager.FRAME_HEIGHT = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            CameraManager.FRAME_WIDTH = DisplayUtil.screenWidthPx / 2;
        }
    }

    private String TAG = "ScanViewfinder";

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.i("ScanViewfinder", "onLayout--l: " + l);
        Log.i("ScanViewfinder", "onLayout--t: " + t);
        Log.i("ScanViewfinder", "onLayout--r: " + r);
        Log.i("ScanViewfinder", "onLayout--b: " + b);
        CameraManager.FRAME_MARGINTOP = t;
    }
}
