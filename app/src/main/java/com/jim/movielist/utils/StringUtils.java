package com.jim.movielist.utils;


import android.text.TextUtils;

public class StringUtils {

    public static String truncate(String s, int length) {
        if (!TextUtils.isEmpty(s)) {
            int maxLen = s.length() >= length ? length : s.length();
            String result = s.substring(0, maxLen);
            if (maxLen > s.length()) {
                result = result + "...";
            }

            return result;
        } else {
            return "";
        }
    }
}
