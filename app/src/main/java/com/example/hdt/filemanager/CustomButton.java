package com.example.hdt.filemanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by hdt
 */

public class CustomButton extends android.support.v7.widget.AppCompatButton {
    private Paint mPaint;
    private String mNameButton;
    private Bitmap mImgButton;

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        initTools();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        @SuppressLint("Recycle") TypedArray typedArray
                = context.obtainStyledAttributes(attrs, R.styleable.CustomButton);

        mNameButton = typedArray.getString(R.styleable.CustomButton_nameButton);

        mImgButton = BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(
                R.styleable.CustomButton_imgButton,
                R.mipmap.ic_launcher
        ));
    }

    private void initTools() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(R.color.colorButtonText));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mImgButton, (getHeight() - mImgButton.getWidth()) / 2,
                (getHeight() - mImgButton.getHeight()) / 2, mPaint);

        mPaint.setTextSize(32);
        float heightText = mPaint.getFontMetrics().descent - mPaint.getFontMetrics().ascent;
        canvas.drawText(mNameButton,
                (getWidth() - mPaint.measureText(mNameButton)) / 2 + 35,
                (getHeight() + heightText / 2) / 2 + 4, mPaint);
    }
}
