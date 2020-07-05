package org.joe;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import org.joe.factory.DAOObject;
import org.joe.factory.impl.DAOFactory;
import org.joe.utils.YTDownload;

public class Test {

    public void daoTest() {
        DAOObject dBook = DAOFactory.creat();

        //dBook.add(1226);
        List<Object> list = dBook.getAll();

        System.out.println(list); 
    }
    
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String uri = scanner.next();
        YTDownload.convertToVideo(uri, Paths.get("E:"));
        System.out.println("done");
    }

}
