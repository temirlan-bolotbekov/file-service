package com.demo.servlets;

import com.demo.config.ConfigProvider;
import com.demo.exception.GlobalException;
import com.demo.utils.Utils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.service.spi.File;
import org.service.spi.FileWorker;

import java.io.FileInputStream;

import static com.demo.utils.Utils.ATTACHMENT;
import static com.demo.utils.Utils.CONTENT_DISPOSITION;

@WebServlet(name = "FileDownloadServlet", urlPatterns = {"/download"})
public class FileDownloadServlet extends HttpServlet {
    private final FileWorker provider = ConfigProvider.provider;
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        long id  = Long.parseLong(req.getParameter("id"));

        try {
            String name = provider.getFiles().stream()
                    .filter(file -> file.getId() == id)
                    .findAny()
                    .map(File::getName)
                    .orElse(null);

            FileInputStream fis = new FileInputStream("docs/" + name);
            ServletOutputStream sos = resp.getOutputStream();
            resp.setContentType(Utils.CONTENT_TYPE);
            resp.setHeader(CONTENT_DISPOSITION, ATTACHMENT + name);
            byte[] buffer = new byte[1024];

            while((fis.read(buffer, 0, 1024)) != -1){
                sos.write(buffer, 0, 1024);
            }
            fis.close();
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }

    }

}
