package com.demo.servlets;

import com.demo.config.ConfigProvider;
import com.demo.utils.Utils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.service.spi.FileWorker;

import java.io.IOException;
import java.io.PrintWriter;

import static com.demo.utils.Utils.APPLICATION_JSON;
import static com.demo.utils.Utils.UTF_8;

@WebServlet(name = "FileGetServlet", urlPatterns = {"/files"})
public class FilesGetServlet extends HttpServlet {
    private final FileWorker fileWorker = ConfigProvider.provider;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fileJsonString = Utils.getGson().toJson(fileWorker.getFiles());
        PrintWriter out = resp.getWriter();
        resp.setContentType(APPLICATION_JSON);
        resp.setCharacterEncoding(UTF_8);
        out.print(fileJsonString);
        out.flush();
    }
}
