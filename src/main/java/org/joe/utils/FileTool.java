package org.joe.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileTool {

    public static String getFileExtension(String path) {
        if (StringTool.isNullOrEmpty(path)) {
            return "";
        }
        return path.substring(path.lastIndexOf(".") + 1);
    }

    public static String getBaseFileName(String path) {
        if (StringTool.isNullOrEmpty(path)) {
            return "";
        }
        int lastIndex = path.lastIndexOf(".");
        if (lastIndex < 0) {
            lastIndex = path.length();
        }
        return path.substring(path.lastIndexOf("\\") + 1, lastIndex);
    }

    public static String getParentPath(String path) {
        return path.substring(0, path.lastIndexOf("\\") + 1);
    }

    private boolean isPathExists(Path path) {
        return Files.exists(path);
    }

    private void creadFile(Path path) {
        try {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        } catch (FileAlreadyExistsException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveText(Path path, List<String> strings) {
        try (FileWriter fw = new FileWriter(path.toFile());
                PrintWriter outs = new PrintWriter(fw);) {
            for (String string : strings) {
                outs.print(string + ";");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> loadText(Path path) {
        if (isPathExists(path)) {
            creadFile(path);
        }
        List<String> contents = new ArrayList<>();
        try (FileReader fw = new FileReader(path.toFile());
                BufferedReader in = new BufferedReader(fw);) {
            String s[], outstring;
            if ((outstring = in.readLine()) != null) {
                s = outstring.split(";");
                for (int i = 0; i < s.length; i++) {
                    contents.add(String.valueOf(s[i].trim()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }

}