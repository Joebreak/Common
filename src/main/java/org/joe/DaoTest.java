package org.joe;

import java.util.List;

import org.joe.factory.DAOObject;
import org.joe.factory.impl.DAOFactory;

public class DaoTest {

    public static void main(String[] args) {
        new DaoTest().daoTest();
    }
    public void daoTest() {
        DAOObject dBook = DAOFactory.creatExcelDAO();

        List<Object> list = dBook.getAll();
        if (list.isEmpty()) {
            dBook.add(1226);
        }
        list = dBook.getAll();
        System.out.println(list); 
        dBook.save();
    }

}
