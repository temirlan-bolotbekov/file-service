package org.service.spi;

import java.util.List;

public interface FileWorker {

    List<File> getFiles();

    File createFile(long size, String name);

    File saveFile(File file);

}
