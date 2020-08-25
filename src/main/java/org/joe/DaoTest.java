package org.joe;

import java.util.List;

import org.joe.factory.DAOObject;
import org.joe.factory.impl.DAOFactory;

public class DaoTest {

    public static void main(String[] args) {
        new DaoTest().daoTest();
    }
    public void daoTest() {
        DAOObject dBook = DAOFactory.creatFileDAO();

        //dBook.add(1226);
        List<Object> list = dBook.getAll();

        System.out.println(list); 
    }

}
