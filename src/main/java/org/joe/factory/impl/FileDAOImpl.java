package org.joe.factory.impl;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.joe.factory.DAOObject;
import org.joe.model.DaoFile;

public class FileDAOImpl implements DAOObject {

    private List<DaoFile> list = null;
    private final Path path;
    private final String rootPath;

    public FileDAOImpl() {
        this(Paths.get(System.getProperty("user.dir")).getRoot().toString());
    }

    public FileDAOImpl(String rootPath) {
        this.rootPath = rootPath;
        this.path = Paths.get(rootPath, "db", "sys.oob");
        list = getAll();
    }

    @Override
    public void add(DaoFile data) {
        list.add(data);
    }

    @Override
    public void remove(int index) {
        list.remove(index);
    }

    @Override
    public List<DaoFile> getAll() {
        if (Files.notExists(path)) {
            creadFile();
        }
        try (FileInputStream fis = new FileInputStream(path.toString());
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            while (true) {
                list.add((DaoFile) ois.readObject());
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (EOFException e) {
        } catch (Exception e) {
            file_null();
            e.printStackTrace();
        }
        return list;
    }

    private void creadFile() {
        try {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        } catch (FileAlreadyExistsException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void file_null() {
        try (FileOutputStream fos = new FileOutputStream(path.toFile());
                ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            oos.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void save() {
        if (Files.notExists(path)) {
            creadFile();
        }
        try (FileOutputStream fos = new FileOutputStream(path.toFile());
                ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            for (Object cb : list) {
                oos.writeObject(cb);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void set(int index, DaoFile data) {
        // TODO Auto-generated method stub
        
    }

}
