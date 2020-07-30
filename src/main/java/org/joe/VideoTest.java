package org.joe;

import java.nio.file.Paths;
import java.util.Scanner;

import org.joe.utils.YTDownload;

public class VideoTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String uri = scanner.next();
        YTDownload.convertToVideo(uri, Paths.get("./"));
        System.out.println("done");

    }

}
