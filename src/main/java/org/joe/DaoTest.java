package org.joe;

import java.util.List;

import org.joe.factory.DAOObject;
import org.joe.factory.impl.DAOFactory;
import org.joe.model.DaoFile;

public class DaoTest {

    public static void main(String[] args) {
        new DaoTest().daoTest();
    }
    public void daoTest() {
        DAOObject dBook = DAOFactory.creatExcelDAO();

        List<DaoFile> list = dBook.getAll();
        if (list.isEmpty()) {
            DaoFile daoFile = new DaoFile();
            daoFile.put(1226);
            dBook.set(5, daoFile);
        }
        list = dBook.getAll();
        System.out.println(list); 
        dBook.save();
    }

}
