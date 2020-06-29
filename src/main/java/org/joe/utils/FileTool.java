package org.joe.utils;

public class FileTool {

    public static String getFileExtension(String name) {
        if (StringTool.isNullOrEmpty(name)) {
            return "";
        }
        return name.substring(name.lastIndexOf(".") + 1);
    }

    public static String getBaseFileName(String name) {
        if (StringTool.isNullOrEmpty(name)) {
            return "";
        }
        int lastIndex = name.lastIndexOf(".");
        if (lastIndex < 0) {
            lastIndex = name.length();
        }
        return name.substring(name.lastIndexOf("\\") + 1, lastIndex);
    }

}
