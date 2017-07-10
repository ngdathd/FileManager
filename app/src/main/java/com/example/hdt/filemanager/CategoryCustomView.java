package com.example.hdt.filemanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hdt
 */

public class CategoryCustomView extends View {
    private Paint mPaint;
    private int mViewColor;
    private Bitmap mViewTypeFiles;
    private Bitmap mSpaceTotalFiles;
    private int mTotalFiles = 0;
    private String mTypeFiles;

    public CategoryCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        initTools();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        @SuppressLint("Recycle") TypedArray typedArray
                = context.obtainStyledAttributes(attrs, R.styleable.CategoryCustomView);

        mViewColor
                = typedArray.getColor(R.styleable.CategoryCustomView_viewColor, Color.BLACK);
        mViewTypeFiles
                = BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(
                R.styleable.CategoryCustomView_viewTypeFiles,
                R.mipmap.ic_launcher));

        mSpaceTotalFiles
                = BitmapFactory.decodeResource(getResources(), R.drawable.asus_ic_space_bg);

        mTypeFiles = typedArray.getString(R.styleable.CategoryCustomView_typeFiles);
    }

    private void initTools() {
        mPaint = new Paint();
        mPaint.setColor(mViewColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        @SuppressLint("DrawAllocation") RectF rectF
                = new RectF(0, 0, getWidth(), getWidth());
        mPaint.setColor(mViewColor);

        canvas.drawOval(rectF, mPaint);
        canvas.drawBitmap(mViewTypeFiles, (getWidth() - mViewTypeFiles.getWidth()) / 2,
                (getWidth() - mViewTypeFiles.getWidth()) / 2, null);

        canvas.drawBitmap(mSpaceTotalFiles, (getWidth() - mSpaceTotalFiles.getWidth()) / 2,
                getWidth() - mSpaceTotalFiles.getWidth() / 4, null);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(22);
        canvas.drawText(mTotalFiles + "", (getWidth() - mPaint.measureText(mTotalFiles + "")) / 2,
                getWidth() + 3, mPaint);

        mPaint.setColor(Color.DKGRAY);
        mPaint.setTextSize(25);
        canvas.drawText(mTypeFiles, (getWidth() - mPaint.measureText(mTypeFiles)) / 2,
                getHeight() - 10, mPaint);
    }

    public void setmTotalFiles(int totalFiles) {
        mTotalFiles = totalFiles;
    }
}
