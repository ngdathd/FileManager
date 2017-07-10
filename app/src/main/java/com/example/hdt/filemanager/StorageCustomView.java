package com.example.hdt.filemanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hdt
 */

public class StorageCustomView extends View {
    private Paint mPaintProgress;
    private Paint mPaintText;
    private float mMaxData;
    private float mProgressData;
    private boolean mFlag;
    private float swipeAngle;
    private Bitmap mViewTypeStorage;

    private RectF mRectF;

    public StorageCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        initTools();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        @SuppressLint("Recycle") TypedArray typedArray
                = context.obtainStyledAttributes(attrs, R.styleable.StorageCustomView);

        mViewTypeStorage
                = BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(
                R.styleable.StorageCustomView_viewTypeStorage,
                R.mipmap.ic_launcher));
    }

    private void initTools() {
        mPaintProgress = new Paint();
        mPaintProgress.setAntiAlias(true);
        mPaintProgress.setStyle(Paint.Style.STROKE);

        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setStyle(Paint.Style.STROKE);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        int viewHeight = MeasureSpec.getSize(heightMeasureSpec);

        float radius = (Math.min(viewWidth, viewHeight) - UiUtils.convertDpToPixel(getContext(), 14)) / 2;

        mRectF = new RectF(viewWidth / 2 - radius, viewHeight / 2 - radius,
                viewWidth / 2 + radius, viewHeight / 2 + radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mViewTypeStorage, (getWidth() - mViewTypeStorage.getWidth()) / 2,
                (getHeight() - mViewTypeStorage.getHeight() * 2) / 2, null);

        mPaintText.setTextSize(32);
        mPaintText.setColor(getResources().getColor(R.color.colorDataStorageText));
        float heightText1 = mPaintText.getFontMetrics().descent - mPaintText.getFontMetrics().ascent;
        if (mFlag) {
            canvas.drawText(mProgressData + "GB",
                    (getWidth() - mPaintText.measureText(mProgressData + "GB")) / 2,
                    getHeight() / 2 + heightText1 - 4, mPaintText);
        } else {
            canvas.drawText(mProgressData + "MB",
                    (getWidth() - mPaintText.measureText(mProgressData + "GB")) / 2,
                    getHeight() / 2 + heightText1 - 4, mPaintText);
        }

        mPaintText.setTextSize(23);
        mPaintText.setColor(getResources().getColor(R.color.colorDataStorageText));
        float heightText2 = mPaintText.getFontMetrics().descent - mPaintText.getFontMetrics().ascent;
        canvas.drawText(mMaxData + "GB",
                (getWidth() - mPaintText.measureText(mMaxData + "GB")) / 2,
                getHeight() - 2 * heightText2, mPaintText);

        mPaintProgress.setStrokeWidth(22);
        mPaintProgress.setColor(getResources().getColor(R.color.colorProgressStorageBg));
        canvas.drawArc(mRectF, 270, 360, false, mPaintProgress);

        mPaintProgress.setColor(getResources().getColor(R.color.colorProgressStorageEnd));
        canvas.drawArc(mRectF, 270, swipeAngle, false, mPaintProgress);

        mPaintProgress.setColor(getResources().getColor(R.color.colorProgressStorage));
        canvas.drawArc(mRectF, 270, swipeAngle - 5, false, mPaintProgress);
    }

    public void setmMaxData(float maxData) {
        mMaxData = maxData;
    }

    public void setmProgressData(float progressData, boolean flag) {
        mProgressData = progressData;
        mFlag = flag;
        float percentage;
        if (flag) {
            percentage = mProgressData * 100 / mMaxData;

        } else {
            percentage = mProgressData * 100 / (mMaxData * 1024);

        }
        swipeAngle = percentage * 360 / 100;
    }
}
