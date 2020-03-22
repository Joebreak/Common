package org.joe.utils;

public class FileTool {

    public static String getFileExtension(String name) {
        return name.substring(name.lastIndexOf(".") + 1);
    }

    public static String getParentPath(String name) {
        return name.substring(0, name.lastIndexOf("\\") + 1);
    }

}
