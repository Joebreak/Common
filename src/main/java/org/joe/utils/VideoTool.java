package org.joe.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VideoTool {


    public static void convertToMp4(Path source) {
        if (!Files.exists(source)) {
            return;
        }
        Path out = Paths.get(source.getParent().toString(), "out.mp4");
        CommandTools.execute(String.format("tools/ffmpeg -i %s -y -vcodec libx264 -acodec aac %s", source, out));
    }

    public static void spileVideo(Path source, Path out) {
        if (!Files.exists(source)) {
            return;
        }
        CommandTools.execute(String.format("tools/ffmpeg -i %s -y -vcodec copy -an %s", source, out));
    }

    public static void spileAudio(Path source, Path out) {
        if (!Files.exists(source)) {
            return;
        }
        CommandTools.execute(String.format("tools/ffmpeg -i %s -y -acodec libmp3lame -vn %s", source, out));
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
            CommandTools
                    .execute(String.format("tools/ffmpeg -i %s -y -c copy -bsf:v h264_mp4toannexb -f mpegts %s", source, name));
            fileNames.add(name);
        }
        StringBuilder sb = new StringBuilder();
        for (Iterator<String> iterator = fileNames.iterator(); iterator.hasNext();) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append("|");
            }
        }
        CommandTools.execute(String.format("tools/ffmpeg -i  \"concat:%s\" -y %s", sb.toString(), out));
    }


    
    // merge srt -vf "subtitles=sourceSubtitle.srt"
    //ffmpeg -i input.wav -filter_complex afade=t=in:ss=0:d=5 output.wav
    //ffmpeg -i input.wav -filter_complex afade=t=out:ss=200:d=5 output.wav
    //ffmpeg -i input1.wav -i input2.wav -filter_complex amix=inputs=2:duration=shortest output.wav
    //ffmpeg -i input.wav -filter_complex atempo=0.5 output.wav
    
    
}
