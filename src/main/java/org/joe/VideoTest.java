package org.joe;

import java.nio.file.Paths;
import java.util.Scanner;

import org.joe.factory.impl.VideoFactory;
import org.joe.utils.YTDownload;

public class VideoTest {

    

    public static void main(String[] args) {
        VideoFactory.spileTime(Paths.get("D:\\joe\\movid\\編輯影片\\out104.mp4"), "", "50",
                Paths.get("D:\\joe\\movid\\編輯影片\\out.mp4"));
    }

    @SuppressWarnings("resource")
    public static void downloadYT() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("in");
        String uri = scanner.next();
        YTDownload.convertToVideo(uri, Paths.get("./"));
        System.out.println("done");
    }

}
