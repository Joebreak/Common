package org.joe.utils;

import java.nio.file.Files;
import java.nio.file.Path;

public class YTDownload {

    public static String convertToAudio(String url, Path parentPath) {
        if (!Files.exists(parentPath)) {
            return "file not found";
        }
        return CommandTools.execute(
                String.format("youtube-dl -x --audio-format mp3 -o %s/%%(title)s.%%(ext)s %s",
                        parentPath, url));
    }

    public static String convertToVideo(String url, Path parentPath) {
        if (!Files.exists(parentPath)) {
            return "file not found";
        }
        return CommandTools.execute(String.format(
                "youtube-dl --abort-on-error --metadata-from-title --restrict-filenames -o %s/%%(title)s.%%(ext)s %s",
                parentPath, url));
    }
}
