package org.joe;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.aspose.words.Document;
import com.aspose.words.ImageSaveOptions;
import com.aspose.words.SaveFormat;

public class WordTest {

    public static void main(String[] args) throws Exception {
        Path pathIn = Paths.get("E://123.docx");
        Path pathOut = Paths.get("E://1.png");
        Document doc = new Document(Files.newInputStream(pathIn));
        
        ImageSaveOptions saveOptions = new ImageSaveOptions(SaveFormat.PNG);
        saveOptions.setPageCount(0);
        
        doc.save(Files.newOutputStream(pathOut), saveOptions);
    }

}
