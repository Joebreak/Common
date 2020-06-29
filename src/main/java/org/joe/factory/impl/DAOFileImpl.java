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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joe.factory.DAOObject;

class DAOFileImpl implements DAOObject {

    private List<Object> list = null;
    private final Path path;
    private final String rootPath;

    public DAOFileImpl() {
        this(Paths.get(System.getProperty("user.dir")).getRoot().toString());
    }

    public DAOFileImpl(String rootPath) {
        this.rootPath = rootPath;
        this.path = Paths.get(rootPath, "db", "sys.oob");
    }

    @Override
    public void add(Object item) {
        add(Arrays.asList(item));
    }

    @Override
    public void add(List<Object> comicList) {
        list = getAll();
        list.addAll(comicList);
        save(list);
    }

    @Override
    public void remove(int index) {
        list = getAll();
        list.remove(index);
        save(list);
    }

    private void save(List<Object> list) {
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
    public List<Object> getAll() {
        if (Files.notExists(path)) {
            creadFile();
        }
        list = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(path.toString());
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            while (true) {
                list.add((Object) ois.readObject());
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

}
