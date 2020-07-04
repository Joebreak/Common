package org.joe;

import java.nio.file.Paths;
import java.util.List;

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
    
    public static void main(String[] args) {
        YTDownload.convertToVideo("https://www.youtube.com/watch?v=_-iE388q7Xo",
                Paths.get("E:\\joe\\movid"));

        
        new Test().daoTest();
    }

}
