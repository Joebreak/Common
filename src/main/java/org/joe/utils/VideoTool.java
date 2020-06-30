package org.joe.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VideoTool {

    public static void spileVideo(String source, String out) {
        if (!Files.exists(Paths.get(source))) {
            return;
        }
        CommandTools.execute(String.format("ffmpeg -i %s -y -vcodec copy -an %s", source, out));
    }

    public static void spileVideo(String source) {
        Path out = Paths.get(FileTool.getParentPath(source), "outVideo." + FileTool.getFileExtension(source));
        spileVideo(source, out.toString());
    }

    public static void spileAudio(String source, String out) {
        if (!Files.exists(Paths.get(source))) {
            return;
        }
        CommandTools.execute(String.format("ffmpeg -i %s -y -acodec libmp3lame -vn %s", source, out));
    }

    public static void spileAudio(String source) {
        Path out = Paths.get(FileTool.getParentPath(source), "outAudio.mp3");
        spileAudio(source, out.toString());
    }

    public static void mergeVideo(List<String> sources, String out) {
        int index = 0;
        List<String> fileNames = new ArrayList<>();
        for (String source : sources) {
            String name = FileTool.getBaseFileName(source).concat(String.valueOf(++index)).concat(".ts");
            System.out.println(String.format("ffmpeg -i %s -c copy -bsf:v h264_mp4toannexb -f mpegts %s", source, name));
            fileNames.add(name);
        }
        StringBuilder sb = new StringBuilder();
        for (Iterator<String> iterator = fileNames.iterator(); iterator.hasNext();) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append("|");
            }
        }
        System.out.println(String.format("ffmpeg -i \"concat:%s\" %s", sb.toString(), out));
    }

    public static void main(String[] args) {
        Path path = Paths.get("E:\\joe\\movid\\2.mp4");
        spileVideo(path.toString());
        // VideoTool.mergeVideo(Arrays.asList("1.mp4", "2.mp4"), "aaa.mp4");
    }
}
