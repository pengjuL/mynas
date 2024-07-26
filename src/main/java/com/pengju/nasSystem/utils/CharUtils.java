package com.pengju.nasSystem.utils;

/**
 * @author pengju
 */
public class CharUtils {

    public static boolean isChinese(char c) {
        return Character.UnicodeScript.of(c) == Character.UnicodeScript.HAN;
    }
    public static boolean hasChinese(String  str) {
        for (int i = 0; i < str.length(); i++) {
            if(isChinese(str.charAt(i))){
                return true;
            };
        }
        return false;
    }
}
