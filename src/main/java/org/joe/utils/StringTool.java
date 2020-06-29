package org.joe.utils;

public class StringTool {

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean equalsIgnoreCase(String s1, String s2) {
        return s1 == null ? false : s1.equalsIgnoreCase(s2);
    }

}
