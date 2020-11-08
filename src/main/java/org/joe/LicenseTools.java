package org.joe;

import java.io.InputStream;

public class LicenseTools {

    private static boolean isLicenseLoaded = false;
    private static final String licPath = "/aspose.lic";

    public static synchronized boolean loadAllLicenses() {
        if (isLicenseLoaded) {
            return true;
        }
        try {
            loadAsposeWordLicense();
            isLicenseLoaded = true;
        } catch (Exception ex) { // ignore error
        }
        return isLicenseLoaded;
    }

    private static void loadAsposeWordLicense() throws Exception {
        try (InputStream licStream = LicenseTools.class.getResourceAsStream(licPath)) {
            com.aspose.words.License wordsLicense = new com.aspose.words.License();
            wordsLicense.setLicense(licStream);
        } catch (Exception ex) {
            System.out.println("License not found!");
        } finally {
        }
    }

}
