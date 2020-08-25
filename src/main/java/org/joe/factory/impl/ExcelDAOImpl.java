package org.joe.factory.impl;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
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

    private Workbook readWorkbook(Path path) {
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
        return readWorkbook(path);
    }
    
    private void creadFile() {
        try {
            Files.createDirectories(path.getParent());
        } catch (Exception e) {
        }
        if (StringTool.isNullOrEmpty(path.getFileName().toString())) {
            return;
        }
        String extension = FileTool.getFileExtension(path.getFileName().toString());
        try {
            Workbook workbook = null;
            if ("xls".equalsIgnoreCase(extension)) {
                workbook = new HSSFWorkbook();
            } else if ("xlsx".equalsIgnoreCase(extension)) {
                workbook = new XSSFWorkbook();
            }
            if (workbook == null) {
                return;
            }
            workbook.createSheet("Sheet1");
            workbook.write(Files.newOutputStream(path));
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
                datas.add(getCellValue(cell));
            }
        }
        return datas;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        CellType cellType = cell.getCellType();
        if (cellType == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cellType == CellType.NUMERIC) {
            if (checkCellDateFormat(cell)) {
                return cell.toString();
            }
            return StringTool.toStringFromInt((int) cell.getNumericCellValue());
        } else if (cellType == CellType.BLANK) {
            return "";
        } else {
            return cell.toString();
        }
    }

    private boolean checkCellDateFormat(Cell cell) {
        CellStyle cellStyle = cell.getCellStyle();
        return DateUtil.isADateFormat(cellStyle.getDataFormat(), cellStyle.getDataFormatString());
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
        }
    }

}
