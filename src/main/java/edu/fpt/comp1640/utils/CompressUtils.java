package edu.fpt.comp1640.utils;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CompressUtils {
    public static void main(String[] args) {
        try {
            List<String> fileList = Arrays.asList(
                    "D:\\Documents\\New folder\\1.PNG",
                    "D:\\Documents\\New folder\\2.PNG",
                    "D:\\Documents\\New folder\\1640 coursework.docx"
            );
            String out = "D:\\Documents\\New folder\\compress.zip";
            zipFile(fileList, new File(out));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void zipFile(final List<String> files, final File targetZipFile) throws IOException {
        try (
                FileOutputStream fos = new FileOutputStream(targetZipFile);
                ZipOutputStream zos = new ZipOutputStream(fos)
        ) {
            for (String file : files) {
                putToZip(file, zos);
            }
        }
    }

    public static void zipFile(final String file, final File targetZipFile) throws IOException {
        try (
                FileOutputStream fos = new FileOutputStream(targetZipFile);
                ZipOutputStream zos = new ZipOutputStream(fos)
        ) {
            putToZip(file, zos);
        }
    }

    private static void putToZip(String file, ZipOutputStream zos) throws IOException {
        byte[] buffer = new byte[1024];
        File currentFile = new File(file);
        if (currentFile.isDirectory()) return;

        try (FileInputStream fis = new FileInputStream(currentFile)) {
            ZipEntry entry = new ZipEntry(currentFile.getName());
            zos.putNextEntry(entry);
            int read = 0;
            while ((read = fis.read(buffer)) != -1) {
                zos.write(buffer, 0, read);
            }
            zos.closeEntry();
        }
    }
}
