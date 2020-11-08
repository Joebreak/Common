package org.joe;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.joe.utils.FileTool;

import com.aspose.words.Document;
import com.aspose.words.ImageSaveOptions;
import com.aspose.words.SaveFormat;

public class WordTest {

    public static void main(String[] args) throws Exception {
        Path path = Paths.get("./data.doc");
        if (!Files.exists(path)) {
            path = Paths.get("./data.docx");
        }
        if (!Files.exists(path)) {
            String parent = Paths.get(System.getProperty("user.dir")).getRoot().toString();
            path = Paths.get(parent, "data.docx");
        }
        if (!Files.exists(path)) {
            System.out.println("data.docx not found!");
            return;
        }
        LicenseTools.loadAllLicenses();
        Path targetParentPath = Paths.get(path.getParent().toString(), "image");
        String parentPath = FileTool.createDirectory(targetParentPath).toString();
        Document doc = new Document(Files.newInputStream(path));
        ImageSaveOptions saveOptions = new ImageSaveOptions(SaveFormat.PNG);
        for (int i = 0; i < doc.getPageCount(); i++) {
            saveOptions.setPageIndex(i);
            String fileName = String.format("%s.png", i+1);
            doc.save(Files.newOutputStream(Paths.get(parentPath, fileName)), saveOptions);
        }
    }

}
