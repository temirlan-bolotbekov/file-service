package com.demo.utils;

import com.google.gson.Gson;

public class Utils {

    public static final String CONTENT_TYPE = "application/octet-stream";
    public static final String ATTACHMENT = "attachment;filename=";
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String FOLDER = "docs/";
    public static final String APPLICATION_JSON = "application/json";
    public static final String UTF_8 = "UTF-8";


    public static Gson getGson() {
        return new Gson();
    }

}
