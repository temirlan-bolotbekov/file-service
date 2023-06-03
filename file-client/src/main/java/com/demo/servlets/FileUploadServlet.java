package com.demo.servlets;

import com.demo.config.ConfigProvider;
import com.demo.utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.service.spi.FileWorker;

import java.io.*;

@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig(
        fileSizeThreshold=0,
        maxFileSize=102400,     // 100 KB
        maxRequestSize=1048576  // 1 MB
)
public class FileUploadServlet extends HttpServlet {
    private final FileWorker fileWorker = ConfigProvider.provider;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        PrintWriter writer = response.getWriter();
        OutputStream outputStream = null;
        InputStream filecontent = null;

        try {
            String fileName = request.getPart("file").getSubmittedFileName();
            File file = new File("docs/" + request.getPart("file").getSubmittedFileName());
            outputStream = new FileOutputStream(file);
            filecontent = request.getPart("file").getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            writer.print(Utils.getGson().toJson(
                    fileWorker.saveFile(
                            fileWorker.createFile(
                                       request.getPart("file").getSize(),
                                       fileName))));
            writer.flush();
        } catch (FileNotFoundException fne) {
            writer.println(fne.getMessage());
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
            if (writer != null) {
                writer.close();
            }
        }


    }

}
