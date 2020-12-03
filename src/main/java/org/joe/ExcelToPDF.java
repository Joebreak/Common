package org.joe;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;

public class ExcelToPDF {

    public static void main(String[] args) throws Exception {
        Path path = Paths.get("./data.xls");
        if (!Files.exists(path)) {
            path = Paths.get("./data.xlsx");
        }
        if (!Files.exists(path)) {
            String parent = Paths.get(System.getProperty("user.dir")).getRoot().toString();
            path = Paths.get(parent, "data.xls");
            if (!Files.exists(path)) {
                path = Paths.get(parent, "data.xlsx");
            }
        }
        if (!Files.exists(path)) {
            System.out.println("excel data not found!");
            return;
        }
        LicenseTools.loadAllLicenses();
        Workbook workbook = new Workbook(path.toString());
        Path targetPath = Paths.get(path.getParent().toString(), "output.pdf");
        workbook.save(Files.newOutputStream(targetPath), SaveFormat.PDF);
    }

}
