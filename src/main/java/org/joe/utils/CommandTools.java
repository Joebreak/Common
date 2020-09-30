package org.joe.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CommandTools {

    public static String execute(String code, boolean show) {
        StringBuilder message = new StringBuilder();
        try {
            ProcessBuilder builder = new ProcessBuilder(code.split(" "));
            builder.redirectErrorStream(true);

            Process process = builder.start();
            InputStream in = process.getInputStream();
            BufferedReader out = new BufferedReader(new InputStreamReader(in));

            String line = null;
            while ((line = out.readLine()) != null) {
                if (show) {
                    System.out.println(show);
                }
                message.append(line);
                message.append("\n");
            }
            process.waitFor();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return message.toString();
    }

    public static String execute(String code) {
        return execute(code, false);
    }
}
