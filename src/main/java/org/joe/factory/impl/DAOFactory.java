package org.joe.factory.impl;

import org.joe.factory.DAOObject;

public class DAOFactory {

	private DAOFactory() {
		super();
	}

    public static DAOObject creatFileDAO() {
        return new FileDAOImpl();
    }

    public static DAOObject creatExcelDAO() {
        return new ExcelDAOImpl();
    }

}
