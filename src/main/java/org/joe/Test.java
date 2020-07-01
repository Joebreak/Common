package org.joe;

import java.util.List;

import org.joe.factory.DAOObject;
import org.joe.factory.impl.DAOFactory;

public class Test {

    public void daoTest() {
        DAOObject dBook = DAOFactory.creat();

        //dBook.add(1226);
        List<Object> list = dBook.getAll();

        System.out.println(list); 
    }
    
    public static void main(String[] args) {
        new Test().daoTest();
    }

}
