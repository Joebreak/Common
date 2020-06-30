package org.joe.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class VideoTool {

    public static void spileVideo(Path source, Path out) {
        if (!Files.exists(source)) {
            return;
        }
        CommandTools.execute(String.format("ffmpeg -i %s -y -vcodec copy -an %s", source, out));
    }

    public static void spileVideo(Path source) {
        Path out = Paths.get(source.getParent().toString(), "outVideo." + FileTool.getFileExtension(source.toString()));
        spileVideo(source, out);
    }

    public static void spileAudio(Path source, Path out) {
        if (!Files.exists(source)) {
            return;
        }
        CommandTools.execute(String.format("ffmpeg -i %s -y -acodec libmp3lame -vn %s", source, out));
    }

    public static void spileAudio(Path source) {
        Path out = Paths.get(source.getParent().toString(), "outAudio.mp3");
        spileAudio(source, out);
    }

    public static void mergeVideo(List<Path> sources, Path out) {
        int index = 0;
        List<String> fileNames = new ArrayList<>();
        for (Path source : sources) {
            if (!Files.exists(source)) {
                continue;
            }
            String name = String.format("%s\\%s_%d%s", source.getParent(),
                    FileTool.getBaseFileName(source.getName(source.getNameCount() - 1).toString()), ++index, ".ts");
            CommandTools.execute(String.format("ffmpeg -i %s -y -c copy -bsf:v h264_mp4toannexb -f mpegts %s", source, name));
            fileNames.add(name);
        }
        StringBuilder sb = new StringBuilder();
        for (Iterator<String> iterator = fileNames.iterator(); iterator.hasNext();) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append("|");
            }
        }
        CommandTools.execute(String.format("ffmpeg -i  \"concat:%s\" -y %s", sb.toString(), out));
    }
    
    public static void mergeVideo(List<Path> sources) {
        if (CollectionTool.isNullOrEmpty(sources)) {
            return;
        }
        Path out = Paths.get(sources.get(0).getParent().toString(), "out.mp4");
        mergeVideo(sources, out);
    }

    public static void main(String[] args) {
        mergeVideo(Arrays.asList(Paths.get("E:\\joe\\movid\\2.mp4"), Paths.get("E:\\joe\\movid\\1.mp4")));
    }
}
