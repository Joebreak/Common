package org.joe.utils;

import java.nio.file.Files;
import java.nio.file.Path;

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
        CommandTools.execute(String.format("tool/youtube-dl --abort-on-error --metadata-from-title --restrict-filenames -o %s/%%(title)s.%%(ext)s %s",
                parentPath, url));
    }
}
