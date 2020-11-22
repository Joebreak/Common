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
        Path parentPath = Paths.get("D:\\joe\\movid");
        Path folderPath = null;
        Path filePath = null;
        Path filePath1 = null;
//        downloadYT();

//        toMp3(parentPath);

        filePath = Paths.get(parentPath.toString(), "data.mkv");
        VideoTool.getVideoDate(filePath, true);

//        filePath = Paths.get(parentPath.toString(), "3.mkv");
//        System.out.println(VideoTool.getDuration(filePath));

//        filePath = Paths.get(parentPath.toString(), "1.rmvb");
//        folderPath = Paths.get(parentPath.toString(), "123");
//        VideoTool.getImages(filePath, "", "1", "30", folderPath);

//        filePath = Paths.get(parentPath.toString(), "out1027.mp4");
//        folderPath = Paths.get(parentPath.toString(), "123");
//        VideoTool.getGif(filePath, null, "10", "30", folderPath);

//        filePath = Paths.get(parentPath.toString(), "out1027.rmvb");
//        filePath1 = Paths.get(parentPath.toString(), "out1027.mp4");
//        VideoTool.convertToMp4(filePath, filePath1);

//        filePath = Paths.get(parentPath.toString(), "data1.mp4");
//        filePath1 = Paths.get(parentPath.toString(), "music.mp3");
//        VideoTool.maskVideo(filePath, filePath1);

//        filePath = Paths.get(parentPath.toString(), "movid.mp4");
//        filePath1 = Paths.get(parentPath.toString(), "music.mp3");
//        VideoTool.mergeVideoAudio(filePath, filePath1, "01:17:05", "10:00");

//        filePath = Paths.get(parentPath.toString(), "movid.mp4");
//        filePath1 = Paths.get(parentPath.toString(), "music.mp3");
//        VideoTool.mergeVideo(Arrays.asList(filePath, filePath1),
//                Paths.get(parentPath.toString(), "out1114.mp4"), null);
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
                if (Files.isDirectory(item) || !"bat".equalsIgnoreCase(
                        FileTool.getFileExtension(item.getFileName().toString()))) {
                    continue;
                }
                Path file = Paths.get(parentPath,
                        FileTool.getBaseFileName(item.getFileName().toString()) + ".mp3");
                VideoTool.maskVideo(item, file);
            }
        } catch (IOException e) {
        }
    }

}
