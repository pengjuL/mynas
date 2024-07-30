package com.pengju.nasSystem.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pengju
 */
public class FileTypeDeterminer {

    private static final Map<String,String> mineTypeMap = new HashMap<>();

    static {
        mineTypeMap.put("png","image");
        mineTypeMap.put("jpg","image");
        mineTypeMap.put("mp3","music");
        mineTypeMap.put("mp4","movie");
        mineTypeMap.put("txt","text");
        mineTypeMap.put("doc","word");
        mineTypeMap.put("docx","word");
        mineTypeMap.put("rar","zip");
        mineTypeMap.put("zip","zip");
        mineTypeMap.put("pdf","pdf");
        mineTypeMap.put("xls","xls");
        mineTypeMap.put("xlsx","xlsx");

    }

    public static String getFileType(File file){

        if (file != null && file.exists()){
            if (file.isDirectory()){
                return "folder";
            }
            String filename = file.getName();
            getFileType(filename);
        }
        return "unknown";
    }

    public static String getFileType(String fileName){

        int dotIndex = fileName.lastIndexOf('.');
        String fileType = "unknown";
        if (dotIndex > 0 && dotIndex < fileName.length()-1){
            String extension = fileName.substring(dotIndex+1).toLowerCase();
            fileType = mineTypeMap.get(extension);
        }
        return fileType == null ? "unknown" : fileType;
    }
}
