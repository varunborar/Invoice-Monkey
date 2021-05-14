package org.invoice.monkey.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class File {

    public static void copyFile(String sourcePath, String destPath)
    {
        try (InputStream logoOriginal = new FileInputStream(new java.io.File(sourcePath)); OutputStream logoCopy = new FileOutputStream(new java.io.File(destPath))) {
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

    public static void deleteFile(String sourcePath)
    {
        try{
            java.io.File file = new java.io.File(sourcePath);
            file.delete();
        }catch(Exception E)
        {
            System.out.println(E.getClass().getName() + ": " + E.getMessage() +"(" + E.getCause() +")");
        }
    }

    public static Boolean createDir(String path, String folderName)
    {
        try {
            java.io.File folder = new java.io.File(path + "\\" + folderName);
            return folder.mkdir();
        }
        catch(Exception e)
        {
            System.out.println("Exception in File:" + e.getClass().getName() + ": " + e.getMessage());
        }
        return false;
    }

    // Standard Base64 encoding for sensitive data

    public static String encryptString64(String str)
    {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

    public static String decryptString64(String str)
    {
        Base64.Decoder decoder = Base64.getDecoder();
        return Arrays.toString(decoder.decode(str));
    }
}
