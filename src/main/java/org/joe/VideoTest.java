package org.joe;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

import org.joe.utils.FileTool;
import org.joe.utils.VideoTool;
import org.joe.utils.YTDownload;

public class VideoTest {

    public static void main(String[] args) {
//        downloadYT();

        toMp3(Paths.get("E:\\joe\\metadata"));

//        VideoTool.getVideoDate(Paths.get("E:\\joe\\metadata\\3.mkv"), true);

//        System.out.println(VideoTool.getDuration(Paths.get("E:\\joe\\metadata\\3.mkv")));

//        VideoTool.getImages(Paths.get("E:\\joe\\metadata\\1.rmvb"), "", "1", "30", Paths.get("E:\\joe\\metadata\\123\\"));

//        VideoTool.getGif(Paths.get("E:\\joe\\metadata\\out1027.mp4"), null, "10", "30", Paths.get("E:\\joe\\metadata\\123\\"));

//        VideoTool.convertToMp4(Paths.get("E:\\joe\\metadata\\out1027.rmvb"),
//                Paths.get("E:\\joe\\metadata\\out1027.mp4"));

//        VideoTool.mergeVideo(Arrays.asList(Paths.get("E:\\joe\\movid\\1.mp4"), Paths.get("E:\\joe\\movid\\2.mp4")),
//                Paths.get("E:\\joe\\movid\\out1021.mp4"), null);
    }

    public static void downloadYT() {
        try (Scanner scanner = new Scanner(System.in);) {
            System.out.println("in");
            String uri = scanner.next();
            YTDownload.convertToVideo(uri, Paths.get("./"));
            System.out.println("done");
        } catch (Exception e) {
        }
    }

    public static void toMp3(Path path) {
        if (!Files.exists(path)) {
            return;
        }
        String parentPath = Paths.get(path.toString(), "music").toString();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path item : stream) {
                if (Files.isDirectory(item)
                        || !"bat".equalsIgnoreCase(FileTool.getFileExtension(item.getFileName().toString()))) {
                    continue;
                }
                Path file = Paths.get(parentPath, FileTool.getBaseFileName(item.getFileName().toString()) + ".mp3");
                VideoTool.maskVideo(item, file); 
            }
        } catch (IOException e) {
        }
    }

}
