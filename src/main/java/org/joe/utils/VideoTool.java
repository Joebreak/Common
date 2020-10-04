package org.joe.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.joe.model.VideoData;

public class VideoTool {

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

    public static void maskAudio(Path source, Path out) {
        if (!Files.exists(source)) {
            return;
        }
        String code = String.format("%s -i %s -y -vcodec copy -an %s", ffmpegPath, source,
                out);
        System.out.println(code);
        CommandTools.execute(code);
    }

    public static void maskVideo(Path source, Path out) {
        if (!Files.exists(source)) {
            return;
        }
        String code = String.format("%s -i %s -y -acodec libmp3lame -vn %s", ffmpegPath,
                source, out);
        System.out.println(code);
        CommandTools.execute(code);
    }

    public static void convertToMp4(Path source, Path out) {
        if (!Files.exists(source)) {
            return;
        }
        String code = String.format("%s -i %s -y -vcodec libx264 -acodec aac %s", ffmpegPath,
                source, out);
        System.out.println(code);
        CommandTools.execute(code);
    }

    public static void mergeVideo(List<Path> sources, Path out, String subtitle) {
        int index = 0;
        List<String> fileNames = new ArrayList<>();
        for (Path source : sources) {
            if (!Files.exists(source)) {
                continue;
            }
            String name = FileTool.getParentPath(source.toString())
                    .concat(String.format("file_%02d.ts", ++index));
            String code = String.format(
                    "%s -i %s -y -c copy -bsf:v h264_mp4toannexb -f mpegts %s", ffmpegPath,
                    source, name);
            System.out.println(code);
            CommandTools.execute(code);
            fileNames.add(name);
        }
        StringBuilder sb = new StringBuilder();
        for (Iterator<String> iterator = fileNames.iterator(); iterator.hasNext();) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append("|");
            }
        }
        StringBuilder code = new StringBuilder();
        code.append(ffmpegPath);
        code.append(" -i ");
        code.append("\"concat:");
        code.append(sb);
        code.append("\" -y -c copy ");
        if (!StringTool.isNullOrEmpty(subtitle)) {
            code.append(" -i ");
            code.append(subtitle);
            code.append(" -vf \"subtitles=");
            code.append(subtitle);
            code.append("\" ");
        }
        code.append(out);
        System.out.println(code);
        CommandTools.execute(code.toString());
    }

    // merge srt -vf "subtitles=sourceSubtitle.srt"
    // ffmpeg -i input.wav -filter_complex afade=t=in:ss=0:d=5 output.wav
    // ffmpeg -i input.wav -filter_complex afade=t=out:ss=200:d=5 output.wav
    // ffmpeg -i input1.wav -i input2.wav -filter_complex
    // amix=inputs=2:duration=shortest output.wav
    // ffmpeg -i input.wav -filter_complex atempo=0.5 output.wav

}
