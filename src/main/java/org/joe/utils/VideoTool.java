package org.joe.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class VideoTool {

    public static String spileVideo(String source, String out) {
        return String.format("ffmpeg -i %s -vcodec copy -an %s", source, out);
    }
    
    public static String spileAudio(String source, String out) {
        return String.format("ffmpeg -i %s -acodec copy -vn %s", source, out);
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
        VideoTool.mergeVideo(Arrays.asList("1.mp4", "2.mp4"), "aaa.mp4");
    }
}
