package org.joe.factory;

import java.util.List;

import org.joe.model.DaoFile;

public interface DAOObject {

    public void add(DaoFile data);
    
    public void set(int index, DaoFile data);

    public List<DaoFile> getAll();

    public void remove(int index);

    public void save();

}
