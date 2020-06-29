package org.joe.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CommandTools {
	

    public static void execute(String code) {
        try {
            ProcessBuilder builder = new ProcessBuilder(code.split(" "));
            builder.redirectErrorStream(true);

            Process process = builder.start();
            InputStream in = process.getInputStream();
            BufferedReader out = new BufferedReader(new InputStreamReader(in));

            String line = null;
            while ((line = out.readLine()) != null) {
                System.out.println(line);
            }
            process.waitFor();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
