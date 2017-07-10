package com.example.hdt.filemanager;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;

/**
 * Created by hdt
 */

public class MemoryInfo {
    private static final String TAG = MemoryInfo.class.getSimpleName();
    private double mTotalSize;
    private double mUsedSize;

    public MemoryInfo(double totalSize, double usedSize) {
        mTotalSize = totalSize;
        mUsedSize = usedSize;
    }

    //    Tìm dung lượng bộ nhớ trong của điện thoại
    public static MemoryInfo getInternalStorageInfo() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File fileRootExternal = Environment.getExternalStorageDirectory();
            StatFs statFs = new StatFs(fileRootExternal.getPath());
            Log.d(TAG, "InternalStorageInfo: " + fileRootExternal.getPath());

            long totalBlocks;
            long sizeBlocks;
            long availableBlocks;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                totalBlocks = statFs.getBlockCountLong();
                sizeBlocks = statFs.getBlockSizeLong();
                availableBlocks = statFs.getAvailableBlocksLong();
            } else {
                totalBlocks = statFs.getBlockCount();
                sizeBlocks = statFs.getBlockSize();
                availableBlocks = statFs.getAvailableBlocks();
            }

            return new MemoryInfo(totalBlocks * sizeBlocks / Math.pow(2.0, 30.0),
                    (totalBlocks * sizeBlocks - availableBlocks * sizeBlocks) / Math.pow(2.0, 30.0));
        }
        return null;
    }

    //    Tìm dung lượng thẻ SD của điện thoại
    public static MemoryInfo getSdCardInfo() {
        File storage = new File("/storage");
        String external_storage_path = "";
        File external_storage;

        if (storage.exists()) {
            File[] files = storage.listFiles();
            for (File file : files) {
                if (file.exists()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (Environment.getExternalStorageState(file).equals(Environment.MEDIA_MOUNTED)) {
                            external_storage_path = file.getAbsolutePath();
                            Log.i(TAG, "SdCardInfo: " + external_storage_path);
                            break;
                        }
                    }
                }
            }
        }

        if (external_storage_path.equals("")) {
            return null;
        } else {
            external_storage = new File(external_storage_path);
        }

        StatFs statFs = new StatFs(external_storage.getPath());

        long totalBlocks;
        long sizeBlocks;
        long availableBlocks;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            totalBlocks = statFs.getBlockCountLong();
            sizeBlocks = statFs.getBlockSizeLong();
            availableBlocks = statFs.getAvailableBlocksLong();
        } else {
            totalBlocks = statFs.getBlockCount();
            sizeBlocks = statFs.getBlockSize();
            availableBlocks = statFs.getAvailableBlocks();
        }

        return new MemoryInfo(totalBlocks * sizeBlocks / Math.pow(2.0, 30.0),
                (totalBlocks * sizeBlocks - availableBlocks * sizeBlocks) / Math.pow(2.0, 30.0));
    }

    public double getmTotalSize() {
        return mTotalSize;
    }

    public double getmUsedSize() {
        return mUsedSize;
    }
}
