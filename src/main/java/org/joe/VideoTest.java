package org.joe;

import java.nio.file.Paths;
import java.util.Scanner;

import org.joe.utils.YTDownload;

public class VideoTest {

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("in");
        String uri = scanner.next();
        YTDownload.convertToVideo(uri, Paths.get("./"));
        System.out.println("done");
    }

}
