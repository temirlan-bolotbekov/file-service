package org.service;

import org.service.exception.ProviderNotFoundException;
import org.service.spi.FileWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class FileService {

    private static FileService fileService;
    private final ServiceLoader<FileWorker> loader;

    public FileService() {
        this.loader = ServiceLoader.load(FileWorker.class);
    }

    public static synchronized FileService getInstance() {
        if (fileService == null) {
            fileService = new FileService();
        }
        return fileService;
    }

    public static List<FileWorker> providers() {
        List<FileWorker> services = new ArrayList<>();
        ServiceLoader<FileWorker> loader = ServiceLoader.load(FileWorker.class);
        loader.forEach(services::add);
        return services;
    }

    public static FileWorker provider(String providerName) {
        ServiceLoader<FileWorker> loader = ServiceLoader.load(FileWorker.class);
        for (FileWorker provider : loader) {
            if (providerName.equals(provider.getClass().getName())) {
                return provider;
            }
        }
        throw new ProviderNotFoundException("Exchange Rate provider " + providerName + " not found");
    }



}
