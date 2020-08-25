package org.joe.factory.impl;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joe.factory.DAOObject;
import org.joe.utils.FileTool;
import org.joe.utils.StringTool;

public class ExcelDAOImpl implements DAOObject {

    private Path path;
    private Workbook workbook;

    public ExcelDAOImpl() {
        this(Paths.get(System.getProperty("user.dir")).getRoot().toString());
    }

    public ExcelDAOImpl(String rootPath) {
        this.path = Paths.get(rootPath, "db", "sys_data.xls");
        if (Files.notExists(path)) {
            creadFile();
        }
        this.workbook = readWorkbookFromExcel();
    }

    private Workbook readWorkbookFromXlsx(Path path) {
        try (XSSFWorkbook workbook = new XSSFWorkbook(Files.newInputStream(path))) {
            return workbook;
        } catch (Exception e) {
        }
        return null;
    }

    private Workbook readWorkbookFromXls(Path path) {
        try {
            return WorkbookFactory.create(Files.newInputStream(path));
        } catch (Exception e) {
        }
        return null;
    }

    private Workbook readWorkbookFromExcel() {
        if (path == null || StringTool.isNullOrEmpty(path.getFileName().toString())) {
            return null;
        }
        String extension = FileTool.getFileExtension(path.getFileName().toString());
        if ("xls".equalsIgnoreCase(extension)) {
            return readWorkbookFromXls(path);
        } else if ("xlsx".equalsIgnoreCase(extension)) {
            return readWorkbookFromXlsx(path);
        }
        return null;
    }
    
    private void creadFile() {
        try (Workbook workbook = new HSSFWorkbook()) {
            Files.createDirectories(path.getParent());
            workbook.createSheet("Sheet1");
            OutputStream out = Files.newOutputStream(path);
            workbook.write(out);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void add(Object object) {
        Sheet sheet = workbook.getSheetAt(0);
        if (sheet == null) {
            return;
        }
        Iterator<Row> rowIterator = sheet.iterator();
        int rowCount = rowIterator.hasNext() ? sheet.getLastRowNum() + 1 : sheet.getLastRowNum();
        Row row = sheet.createRow(rowCount);
        Cell cell = row.createCell(0);
        cell.setCellValue(object.toString());
    }

    @Override
    public List<Object> getAll() {
        List<Object> datas = new ArrayList<>();
        Sheet sheet =  workbook.getSheetAt(0);
        if (sheet == null) {
            return datas;
        }
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            if (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                datas.add(cell.getStringCellValue());
            }
        }
        return datas;
    }

    @Override
    public void remove(int index) {
        // TODO Auto-generated method stub

    }

    @Override
    public void save() {
        if (!Files.exists(path)) {
            return;
        }
        try (FileOutputStream out = new FileOutputStream(path.toFile());) {
            workbook.write(out);
            workbook.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
