package org.joe;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

import org.joe.utils.VideoTool;
import org.joe.utils.YTDownload;

public class VideoTest {

    public static void main(String[] args) {
        VideoTool.mergeVideo(Arrays.asList(Paths.get("D:\\out104.mp4"), Paths.get("D:\\out.mp4")), Paths.get("D:\\out123.mp4"),
                null);
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

}
