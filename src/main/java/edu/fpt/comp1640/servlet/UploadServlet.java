package edu.fpt.comp1640.servlet;

import edu.fpt.comp1640.config.Config;
import edu.fpt.comp1640.config.Ini;
import edu.fpt.comp1640.database.Database;
import edu.fpt.comp1640.model.user.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

/**
 * Servlet to handle File upload request from Client
 */
public class UploadServlet extends HttpServlet {
    private static final String ADD_NEW_SUBMISSION = "INSERT INTO Submissions (name, student_id, publish_year, chosen) VALUES (?, ?, ?, false)";
    private static final String ADD_NEW_FILE = "INSERT INTO Files (submission_id, disk_location) VALUES (?, ?)";
    private static final String UPLOAD_DIRECTORY;
    private static Ini ini;

    static {
        ini = Config.getConfig();
        UPLOAD_DIRECTORY = ini.get("GENERAL", "upload_dir");
    }

    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        request.getRequestDispatcher("/upload.jsp").forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        if (ServletFileUpload.isMultipartContent(request)) {
            try (Database db = new Database()) {
                User user = (User) request.getSession().getAttribute("user");
                if (user.getRole() != User.ROLE_STUDENT) return;
                int studentId = user.getRoleId();

                // name, student_id, publish_year
                String name = request.getParameter("name");
                Object[] params = {name, studentId, 1};
                addNewSubmission(request, db, params);

                //File uploaded successfully
                request.setAttribute("message", "File Uploaded Successfully");
            } catch (Exception ex) {
                request.setAttribute("message", "File Upload Failed due to " + ex.getMessage());
            }
        } else {
            request.setAttribute("message", "Sorry this Servlet only handles file upload request");
        }

        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }

    private void addNewSubmission(HttpServletRequest request, Database db, Object[] params) throws Exception {
        ResultSet inserted = db.insert(ADD_NEW_SUBMISSION, params);
        if (!inserted.next()) return;
        long submissionID = inserted.getLong(1);

        long time = new Date().getTime();
        List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

        for (int i = 0; i < multiparts.size(); i++) {
            FileItem item = multiparts.get(i);
            if (!item.isFormField()) {
                String[] split = item.getName().split(".");
                String extension = split[split.length - 1];
                String fileName = String.format("%s_%d_%d.%s", params[1], time, i, extension);
                File file = new File(UPLOAD_DIRECTORY, fileName);
                item.write(file);

                Object[] param = {submissionID, file.getAbsolutePath()};
                db.update(ADD_NEW_FILE, param);
            }
        }


    }
}
