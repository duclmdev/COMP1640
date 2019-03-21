package edu.fpt.comp1640.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(name = "DownloadServlet", urlPatterns = {"/download"})
public class DownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String id = request.getParameter("id");
        System.out.println(id);
        String fileName = "D:\\Documents\\[Japanese]\\Minna No Nihongo Audio & Textbook MondaiChoukai\\Minna No Nihongo II - Choukai.pdf";
        String fileType = "application/pdf";

        // Find this file id in database to get file name, and file type
        // You must tell the browser the file type you are going to send
        // for example application/pdf, text/plain, text/html, image/jpg
        response.setContentType(fileType);

        // Make sure to show the download dialog
        response.setHeader("Content-Disposition", "attachment; filename=yourcustomfilename.pdf");

        // Assume file name is retrieved from database
        // For example D:\\file\\test.pdf
        File file = new File(fileName);

        // This should send the file to browser
        OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > -1) {
            out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
    }
}

