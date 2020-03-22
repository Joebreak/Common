package org.joe.factory.impl;

import org.joe.factory.DAOObject;

public class DAOFactory {

	private DAOFactory() {
		super();
	}

	public static DAOObject creat() {
		return new DAOFileImpl();
	}

}
