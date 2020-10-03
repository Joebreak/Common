package org.joe.factory.impl;

import java.nio.file.Files;
import java.nio.file.Path;

import org.joe.model.VideoData;
import org.joe.utils.CommandTools;
import org.joe.utils.JSONTool;
import org.joe.utils.StringTool;

public class VideoFactory {

    private static String ffprobePath = "ffprobe";
    private static String ffmpegPath = "ffmpeg";

    public static VideoData getVideoDate(Path source) {
        if (!Files.exists(source)) {
            return null;
        }
        String code = String.format(
                "%s -v quiet -print_format json -show_format -show_streams %s", ffprobePath,
                source);
        System.out.println(code);
        String message = CommandTools.execute(code);
        return JSONTool.readJSON(message, VideoData.class);
    }

    public static void snapshot(Path source, String time, Path out) {
        if (!Files.exists(source)) {
            return;
        }
        String code = String.format("%s -i %s -ss %s -y -vframes:v 1 %s", ffmpegPath, source,
                time, out);
        System.out.println(code);
        CommandTools.execute(code);
    }

    public static void adjustmentVolumeDB(Path source, int dB, Path out) {
        if (!Files.exists(source)) {
            return;
        }
        String code = String.format("%s -i %s -y -vcodec copy -af \"volume=%sdB\" %s",
                ffmpegPath, source, dB, out);
        System.out.println(code);
        CommandTools.execute(code);
    }

    public static void spileTime(Path source, String fromTime, String totalTime, Path out) {
        if (!Files.exists(source)) {
            return;
        }
        StringBuilder code = new StringBuilder();
        code.append(ffmpegPath);
        code.append(" -i ");
        code.append(source);
        code.append(" -y -c copy ");
        if (!StringTool.isNullOrEmpty(fromTime)) {
            code.append("-ss ");
            code.append(fromTime);
            code.append(" ");
        }
        if (!StringTool.isNullOrEmpty(totalTime)) {
            code.append("-t ");
            code.append(totalTime);
            code.append(" ");
        }
        code.append(out);
        System.out.println(code);
        CommandTools.execute(code.toString());
    }

}
