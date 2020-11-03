package org.joe;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.joe.factory.impl.ExcelDAOImpl;
import org.joe.model.DaoFile;
import org.joe.utils.Numbertool;

public class ExcelText {

    public static void main(String[] args) {
        Path path = Paths.get("data.xls");
        if (!Files.exists(path)) {
            path = Paths.get("data.xlsx");
        }
        if (!Files.exists(path)) {
            String parent = Paths.get(System.getProperty("user.dir")).getRoot().toString();
            path = Paths.get(parent, "data.xlsx");
        }
        ExcelDAOImpl dao = new ExcelDAOImpl(path);
        showCmd();
        boolean edit = false;
        DaoFile item = null;
        int dataIndex = 0;
        try (Scanner scanner = new Scanner(System.in);) {
            while (true) {
                String code = scanner.next();
                if (edit) {
                    if ("-1".equalsIgnoreCase(code)) {
                        edit = false;
                        if (dataIndex == -1) {
                            dao.add(item);
                            dao.save();
                        } else {
                            save(dao, item, dataIndex);
                        }
                    } else if (item == null) {
                        continue;
                    }
                    int index = Numbertool.parseIntNotNull(code, -1);
                    if (index == -1) {
                        continue;
                    }
                    System.out.print("input vale:");
                    code = scanner.next();
                    item.put(code, index);
                    System.out.print("input number:");
                } else if ("-1".equalsIgnoreCase(code)) {
                    break;
                } else {
                    if ("1".equalsIgnoreCase(code)) {
                        show(dao);
                    } else if ("0".equalsIgnoreCase(code)) {
                        showCmd();
                    } else if ("5".equalsIgnoreCase(code)) {
                        edit = true;
                        item = new DaoFile();
                        dataIndex = -1;
                        System.out.print("input number:");
                    } else {
                        System.out.print("input number:");
                        int index = Numbertool.parseIntNotNull(scanner.next(), -1);
                        if (index == -1) {
                            continue;
                        }
                        if ("2".equalsIgnoreCase(code)) {
                            show(dao, index);
                        } else if ("4".equalsIgnoreCase(code)) {
                            dao.remove(index);
                            dao.save();
                        } else if ("3".equalsIgnoreCase(code)) {
                            edit = true;
                            item = dao.get(index);
                            dataIndex = index;
                            System.out.print("input number:");
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void showCmd() {
        System.out.println("0 : showCmd");
        System.out.println("1 : show all");
        System.out.println("2 : show for index");
        System.out.println("3 : edit for index(edit mode)");
        System.out.println("4 : remove for index");
        System.out.println("5 : input for index(edit mode)");
        System.out.println("-1 : exit");
    }

    public static void show(ExcelDAOImpl dao, int index) {
        DaoFile item = dao.get(index);
        print(item);
    }

    public static void show(ExcelDAOImpl dao) {
        for (DaoFile item : dao.getAll()) {
            print(item);
        }
    }

    public static void print(DaoFile item) {
        System.out.println(item);
    }

    public static void save(ExcelDAOImpl dao, DaoFile item, int index) {
        dao.set(index, item);
        dao.save();
    }

}
