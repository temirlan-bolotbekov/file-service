package com.demo.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(urlPatterns = "/upload")
public class UploadFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException
    {
        PrintWriter out = response.getWriter();
        Part part = ((HttpServletRequest) request).getPart("file");
        if ( isValidFile(part) ) {
            filterChain.doFilter(request, response);
        } else
            out.print("Incorrect files, files may be 'txt' and 'csv' extension and no more 100kb");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private boolean isValidFile(Part part) {
        String extension = part
                .getSubmittedFileName()
                .substring(
                        part.getSubmittedFileName()
                                .lastIndexOf(".") + 1);

        return part.getSize() < 102400 && (extension.equals("csv") || extension.equals("txt"));
    }

}
