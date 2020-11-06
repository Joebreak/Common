package org.joe.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
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

    public static String getParentBasePath(String path) {
        return path.substring(0, path.lastIndexOf("."));
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
        try (FileWriter fw = new FileWriter(path.toFile()); PrintWriter outs = new PrintWriter(fw);) {
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
        try (FileReader fw = new FileReader(path.toFile()); BufferedReader in = new BufferedReader(fw);) {
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

    public static Path createTempDirectory(String folderName) {
        return createDirectory(Paths.get(System.getProperty("java.io.tmpdir"), folderName));
    }

    public static Path createDirectory(Path path) {
        if (Files.exists(path)) {
            return path;
        }
        try {
            return Files.createDirectory(path);
        } catch (IOException e) {
        }
        return null;
    }

    public static void delete(Path path) {
        try {
            Files.delete(path);
        } catch (Exception ex) {
        }
    }

    public static byte[] readAllBytes(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException ioe) {
        }
        return null;
    }

    public static String getSHA256(Path path) {
        if (!Files.exists(path)) {
            return null;
        } 
        try {
            byte[] b = Files.readAllBytes(path);
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(b);
            return byte2Hex(messageDigest.digest());
        } catch (Exception e) {
        }
        return null;
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

}