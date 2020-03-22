package org.joe.factory;

import java.util.List;

public interface DAOObject {

	public void add(Object object);

	public void add(List<Object> object);

	public List<Object> getAll();

	public void remove(int index);

}
