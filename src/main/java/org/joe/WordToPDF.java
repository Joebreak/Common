package org.joe;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.aspose.words.Document;
import com.aspose.words.PdfSaveOptions;

public class WordToPDF {

    public static void main(String[] args) throws Exception {
        Path path = Paths.get("./data.doc");
        if (!Files.exists(path)) {
            path = Paths.get("./data.docx");
        }
        if (!Files.exists(path)) {
            String parent = Paths.get(System.getProperty("user.dir")).getRoot().toString();
            path = Paths.get(parent, "data.doc");
            if (!Files.exists(path)) {
                path = Paths.get(parent, "data.docx");
            }
        }
        if (!Files.exists(path)) {
            System.out.println("word data not found!");
            return;
        }
        LicenseTools.loadAllLicenses();
        Document doc = new Document(Files.newInputStream(path));
        PdfSaveOptions saveOptions = new PdfSaveOptions();
        saveOptions.setPageIndex(0);
        Path targetPath = Paths.get(path.getParent().toString(), "output.pdf");
        doc.save(Files.newOutputStream(targetPath), saveOptions);
    }

}
