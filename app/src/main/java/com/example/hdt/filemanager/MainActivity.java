package com.example.hdt.filemanager;

import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private int mCountImage;
    private int mCountVideo;
    private int mCountMusic;
    private int mCountCompressed;
    private int mCountApk;
    private int mCountDocument;
    private int mCountDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//          Lấy ra folder root bao gồm: bộ nhớ trong và thẻ nhớ
//        String pathRoot = Environment.getExternalStorageDirectory().getPath();
//        File fileRoot = new File(pathRoot);
//        getAllFilePath(fileRoot);

        getCountFileByType();
        getTotalFilesByType();
        getProgressData();
    }

    //    Lấy ra file và kiểm tra đuôi file để tăng biến đếm tương ứng
    //    Hiệu suất rất thấp => không làm theo cách này
    public void getAllFilePath(File file) {
        if (file == null) {
            return;
        }
        if (file.isDirectory()) {
            File files[] = file.listFiles();
            if (files == null) {
                return;
            }
            for (File file1 : files) {
                getAllFilePath(file1);
            }
        } else {
            String path = file.getPath();
            switch (ItemFile.getFileType(path)) {
                case "IMAGE": {
                    mCountImage++;
                }
                break;
                case "VIDEO": {
                    mCountVideo++;
                }
                break;
                case "MUSIC": {
                    mCountMusic++;
                }
                break;
                case "ZIP": {
                    mCountCompressed++;
                }
                break;
                case "APK": {
                    mCountApk++;
                }
                break;
                case "DOC": {
                    mCountDocument++;
                }
                break;
                default:
                    break;
            }
        }
    }

    //    Tìm database chứa file và đếm số file
    public void getCountFileByType() {
        //  Begin count images
        Uri mUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor mCursor = getContentResolver().query(mUri, null, null, null, null);
        if (null == mCursor) {
            Log.d(TAG, "Khong ton tai database cua Images");
            return;
        }
        mCountImage = mCursor.getCount();
        //  End count images

        //  Begin count video
        mUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        mCursor = getContentResolver().query(mUri, null, null, null, null);
        if (null == mCursor) {
            Log.d(TAG, "Khong ton tai database cua Video");
            return;
        }
        mCountVideo = mCursor.getCount();
        //  End count video

        //  Begin count music
        mUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        mCursor = getContentResolver().query(mUri, null, null, null, null);
        if (null == mCursor) {
            Log.d(TAG, "Khong ton tai database cua Music");
            return;
        }
        mCountMusic = mCursor.getCount();
        //  End count music

        //  Begin count compressed
        mUri = MediaStore.Files.getContentUri("external");
        mCursor = getContentResolver().query(mUri, null, MediaStore.Images.Media.DATA + " LIKE ?",
                new String[]{"%.zip"}, null);
        if (null == mCursor) {
            Log.d(TAG, "Khong ton tai database cua Compressed");
            return;
        }
        mCountCompressed = mCursor.getCount();
        //  End count compressed

        //  Begin count apk
        mUri = MediaStore.Files.getContentUri("external");
        mCursor = getContentResolver().query(mUri, null, MediaStore.Images.Media.DATA + " LIKE ?",
                new String[]{"%.apk"}, null);
        if (null == mCursor) {
            Log.d(TAG, "Khong ton tai database cua Apk");
            return;
        }
        mCountApk = mCursor.getCount();
        //  End count apk

        //  Begin count document
        mUri = MediaStore.Files.getContentUri("external");
        mCursor = getContentResolver().query(mUri, null, MediaStore.Images.Media.DATA + " LIKE ?",
                new String[]{"%.txt"}, null);
        if (null == mCursor) {
            Log.d(TAG, "Khong ton tai database cua Document");
            return;
        }
        mCountDocument = mCursor.getCount();
        //  End count document

        //  Đếm số file trong folder Download
        getDownloadFile(Environment.getDownloadCacheDirectory());

        mCursor.close();
    }

    //    Đếm số file
    private void getDownloadFile(File file) {
        if (file == null) {
            return;
        }
        if (file.isDirectory()) {
            File files[] = file.listFiles();
            if (files == null) {
                return;
            }
            for (File file1 : files) {
                getDownloadFile(file1);
            }
        } else {
            mCountDownload++;
        }
    }

    //    Hiện số lượng file lên màn hình
    private void getTotalFilesByType() {
        CategoryCustomView categoryCustomView = (CategoryCustomView) findViewById(R.id.category_images);
        categoryCustomView.setmTotalFiles(mCountImage);

        categoryCustomView = (CategoryCustomView) findViewById(R.id.category_videos);
        categoryCustomView.setmTotalFiles(mCountVideo);

        categoryCustomView = (CategoryCustomView) findViewById(R.id.category_music);
        categoryCustomView.setmTotalFiles(mCountMusic);

        categoryCustomView = (CategoryCustomView) findViewById(R.id.category_zip);
        categoryCustomView.setmTotalFiles(mCountCompressed);

        categoryCustomView = (CategoryCustomView) findViewById(R.id.category_apps);
        categoryCustomView.setmTotalFiles(mCountApk);

        categoryCustomView = (CategoryCustomView) findViewById(R.id.category_documents);
        categoryCustomView.setmTotalFiles(mCountDocument);

        categoryCustomView = (CategoryCustomView) findViewById(R.id.category_download);
        categoryCustomView.setmTotalFiles(mCountDownload);
    }

    //    Hiển thị phần trăm dung lượng bộ nhớ đã sử dụng
    public void getProgressData() {
        MemoryInfo memoryInfo = MemoryInfo.getInternalStorageInfo();

        StorageCustomView storageCustomView = (StorageCustomView) findViewById(R.id.storage_internal);
        if (memoryInfo != null) {
            storageCustomView.setmMaxData((float) Math.round(memoryInfo.getmTotalSize() * 100) / 100);
            storageCustomView.setmProgressData((float) Math.round(memoryInfo.getmUsedSize() * 100) / 100, true);
        }

        memoryInfo = MemoryInfo.getSdCardInfo();

        storageCustomView = (StorageCustomView) findViewById(R.id.storage_microsd);
        if (memoryInfo != null) {
            storageCustomView.setmMaxData((float) Math.round(memoryInfo.getmTotalSize() * 100) / 100);
            if (memoryInfo.getmUsedSize() <= 0.01) {
                storageCustomView.setmProgressData((float) Math.round(memoryInfo.getmUsedSize() * 1024 * 100) / 100, false);
            } else {
                storageCustomView.setmProgressData((float) Math.round(memoryInfo.getmUsedSize() * 100) / 100, true);
            }
        } else {
            storageCustomView.setVisibility(View.INVISIBLE);
            findViewById(R.id.txt_microsd).setVisibility(View.INVISIBLE);
            findViewById(R.id.txt_line_r).setVisibility(View.INVISIBLE);
        }
//        VISIBLE: Hiển thị layout
//        INVISIBLE: Ẩn layout, layout vẫn còn ở đó, các thuộc tính: id, padding, margin,...vẫn có tác dụng
//        GONE: Xóa layout, layout biến mất,  các thuộc tính: id, padding, margin,...mất tác dụng
    }
}