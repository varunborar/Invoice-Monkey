package org.invoice.monkey.utils;

import java.io.*;

public class Copy {

    public static void copyFile(String sourcePath, String destPath)
    {
        try (InputStream logoOriginal = new FileInputStream(new File(sourcePath)); OutputStream logoCopy = new FileOutputStream(new File(destPath))) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = logoOriginal.read(buffer)) > 0) {
                logoCopy.write(buffer, 0, length);
            }
        }catch(Exception E)
        {
            System.out.println(E.getClass().getName() + ": " + E.getMessage() +"(" + E.getCause() +")");
        }
    }
}
