package org.joe.factory;

import java.util.List;

public interface DAOObject {

    public void add(Object object);

    public List<Object> getAll();

    public void remove(int index);

    public void save();

}
