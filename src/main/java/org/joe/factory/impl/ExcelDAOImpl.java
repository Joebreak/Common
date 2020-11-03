package org.joe.factory.impl;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import org.joe.model.DaoFile;
import org.joe.utils.DateTool;
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

    public ExcelDAOImpl(Path path) {
        if (Files.exists(path)) {
            this.path = path;
        } else {
            creadFile();
            String rootPath = Paths.get(System.getProperty("user.dir")).getRoot().toString();
            this.path = Paths.get(rootPath, "db", "sys_data.xls");
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
        Workbook workbook = readWorkbook(path);
        if (workbook == null) {
            creadFile();
            workbook = readWorkbook(path);
        }
        return workbook;
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
    public void add(DaoFile data) {
        Sheet sheet = workbook.getSheetAt(0);
        if (sheet == null) {
            return;
        }
        Iterator<Row> rowIterator = sheet.iterator();
        int rowCount = rowIterator.hasNext() ? sheet.getLastRowNum() + 1 : sheet.getLastRowNum();
        Row row = sheet.createRow(rowCount);
        for (Entry<Integer, Object> entry : data.getDataMap().entrySet()) {
            Cell cell = row.createCell(entry.getKey());
            Object value = entry.getValue();
            if (value == null) {
                cell.setCellValue((Date) null);
            } else if (value instanceof Date) {
                cell.setCellValue((Date) value);
            } else if (value instanceof Integer) {
                cell.setCellValue((Integer) value);
            } else {
                cell.setCellValue(value.toString());
            }
        }
    }

    @Override
    public void set(int index, DaoFile data) {
        Sheet sheet = workbook.getSheetAt(0);
        if (sheet == null) {
            return;
        }
        Row row = sheet.createRow(index);
        for (Entry<Integer, Object> entry : data.getDataMap().entrySet()) {
            Cell cell = row.createCell(entry.getKey());
            Object value = entry.getValue();
            if (value == null) {
                cell.setCellValue((Date) null);
            } else if (value instanceof Date) {
                cell.setCellValue((Date) value);
            } else if (value instanceof Integer) {
                cell.setCellValue((Integer) value);
            } else {
                cell.setCellValue(value.toString());
            }
        }
    }

    @Override
    public DaoFile get(int index) {
        DaoFile data = new DaoFile();
        Sheet sheet = workbook.getSheetAt(0);
        if (sheet == null) {
            return data;
        }
        Row row = sheet.getRow(index);
        if (row == null) {
            return data;
        }
        data.setIndex(row.getRowNum());
        Iterator<Cell> cellIterator = row.cellIterator();
        Map<Integer, Object> dataMap = data.getDataMap();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            dataMap.put(cell.getColumnIndex(), getCellValue(cell));
        }
        return data;
    }

    @Override
    public void remove(int index) {
        Sheet sheet = workbook.getSheetAt(0);
        if (sheet == null) {
            return;
        }
        Row row = sheet.createRow(index);
        sheet.removeRow(row);
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

    @Override
    public List<DaoFile> getAll() {
        List<DaoFile> datas = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(0);
        if (sheet == null) {
            return datas;
        }
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            DaoFile data = new DaoFile();
            data.setIndex(row.getRowNum());
            Map<Integer, Object> dataMap = data.getDataMap();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                dataMap.put(cell.getColumnIndex(), getCellValue(cell));
            }
            datas.add(data);
        }
        return datas;
    }

    private Object getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        CellType cellType = cell.getCellType();
        if (cellType == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cellType == CellType.NUMERIC) {
            if (checkCellDateFormat(cell)) {
                return getCellDateAndTimeFormatString(cell);
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

    private String getCellDateAndTimeFormatString(Cell cell) {
        CellStyle cellStyle = cell.getCellStyle();
        String dataFormat = cellStyle.getDataFormatString();
        if (StringTool.isNullOrEmpty(dataFormat)) {
            return cell.toString();
        }
        if (dataFormat.contains("m/d/yy") || dataFormat.contains("yyyy/mm/dd")) {
            dataFormat = "yyyy/MM/dd";
        } else if (dataFormat.contains("yyyy\\-mm\\-dd")) {
            dataFormat = "yyyy-MM-dd";
        } else if (dataFormat.contains("h:mm")) {
            dataFormat = "HH:mm";
        } else if (dataFormat.contains("m-d")) {
            dataFormat = "M-d";
        }
        String date = DateTool.toFormat(dataFormat, cell.getDateCellValue());
        return date == null ? cell.toString() : date;
    }

}
