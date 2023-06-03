package org.file.document;

import org.service.spi.File;
import org.service.spi.FileWorker;

import java.util.ArrayList;
import java.util.List;

public class FileDocument implements FileWorker {
    private static long id = 0;
    private final List<File> docFiles = new ArrayList<>();

    @Override
    public List<File> getFiles() {
        return docFiles;
    }

    @Override
    public File createFile(long size, String name) {
        return new File(
                createID(),
                formatSize(size),
                name);
    }
    @Override
    public File saveFile(File file) {
        docFiles.add(file);
        return file;
    }

    public static synchronized long createID()
    {
        return ++id;
    }

    public static String formatSize(long v) {
        if (v < 1024) return v + " Bytes";
        int z = (63 - Long.numberOfLeadingZeros(v)) / 10;
        return String.format("%.1f %sB", (double)v / (1L << (z*10)), " KMGTPE".charAt(z));
    }
}
