package com.example.hdt.filemanager;

import java.io.File;

/**
 * Created by hdt
 */

public class ItemFile {
    public static String getFileType(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            return "FOLDER";
        }
        switch (path.substring(path.lastIndexOf(".") + 1)) {
            case "apk": {
                return "APK";
            }
            case "jpg":
            case "JPG":
            case "png":
            case "PNG":
            case "jpeg":
            case "JPEG": {
                return "IMAGE";
            }
            case "mp4":
            case "MP4":
            case "3gp":
            case "3GP":
            case "webm":
            case "WEBM": {
                return "VIDEO";
            }
            case "zip":
            case "ZIP":
            case "rar":
            case "RAR":
            case "jar":
            case "JAR":
            case "gz":
            case "GZ": {
                return "ZIP";
            }
            case "mp3":
            case "MP3":
            case "m4a":
            case "M4A":
            case "wav":
            case "WAV":
            case "ogg":
            case "OGG": {
                return "MUSIC";
            }
            case "doc":
            case "DOC":
            case "txt":
            case "TXT":
            case "pdf":
            case "PDF":
            case "xml":
            case "XML":
            case "docx":
            case "DOCX":
            case "csv":
            case "CSV": {
                return "DOC";
            }
            default: {
                return "UNKNOWN";
            }
        }
    }
}