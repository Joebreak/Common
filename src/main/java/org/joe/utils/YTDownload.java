package org.joe.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class YTDownload {
    
    public static void convertToAudio(String url, Path parentPath) {
        if (!Files.exists(parentPath)) {
            return;
        }
        CommandTools
                .execute(String.format("tool/youtube-dl -x --audio-format mp3 -o %s/%%(title)s.%%(ext)s %s", parentPath, url));
    }

    public static void convertToVideo(String url, Path parentPath) {
        if (!Files.exists(parentPath)) {
            return;
        }
        CommandTools.execute(String.format("tool/youtube-dl --abort-on-error --restrict-filenames -o %s/%%(title)s.%%(ext)s %s",
                parentPath, url));
    }
    
    public static void main(String[] args) {
        convertToVideo("https://youtu.be/NacWC1hZuhg", Paths.get("E:\\joe\\movid"));
  }
}
