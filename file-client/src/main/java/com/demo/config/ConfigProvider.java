package com.demo.config;

import org.service.FileService;
import org.service.spi.FileWorker;

public class ConfigProvider {
    public static final FileWorker provider = FileService.provider("org.file.document.FileDocument");

}
