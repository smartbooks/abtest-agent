package com.suiyueyule.abtest.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class FileUtils {

    private final static Logger logger = LogManager.getLogger(FileUtils.class);

    public static String readFile(File file) {
        StringBuilder textContent = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                textContent.append(line);
            }
            br.close();
        } catch (Exception e) {
            logger.error(e);
        }
        return textContent.toString();
    }

    public static void writeFile(File file, String text) {
        try {

            if (file.exists()) {
                file.delete();
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));

            bw.write(text);

            bw.flush();

            bw.close();
        } catch (Exception e) {
            logger.error(e);
        }
    }

}
