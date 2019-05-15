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
import java.util.Map;

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
        Map<String, String[]> map = request.getParameterMap();

        User user = (User) request.getSession().getAttribute("user");
        if (user.getRole() != User.ROLE_STUDENT) return;
        int studentId = user.getRoleId();

        if (map.containsKey("name") && map.containsKey("publish")) {
            createNewSubmission(request, response, studentId);
        } else {
            try {
                handleFiles(request, studentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void createNewSubmission(HttpServletRequest request, HttpServletResponse response, int studentId) {
        try (Database db = new Database()) {
            String name = request.getParameter("name");
            int publish = Integer.parseInt(request.getParameter("publish"));

            Object[] params = {name, studentId, publish};
            ResultSet inserted = db.insert(ADD_NEW_SUBMISSION, params);
            if (!inserted.next()) return;

            long id = inserted.getLong(1);
            response.setContentType("application/json");
            response.getWriter().write(String.format("{\"id\": %d}", id));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void handleFiles(HttpServletRequest request, int studentId) throws Exception {
        if (ServletFileUpload.isMultipartContent(request)) {
            List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            try (Database db = new Database()) {
                String filePath = "";
                int id = 1;
                for (int i = 0; i < multiparts.size(); i++) {
                    FileItem item = multiparts.get(i);
                    if (!item.isFormField()) {
                        String name = item.getName();
                        String[] split = name.split("\\.");
                        String extension = split[split.length - 1];
                        long time = new Date().getTime();
                        String fileName = String.format("%d_%s_%d_%d.%s", studentId, name, time, i, extension);
                        File file = new File(UPLOAD_DIRECTORY, fileName);
                        item.write(file);

                        filePath = file.getAbsolutePath();
                    } else {
                        id = Integer.parseInt(new String(item.get()));
                    }
                }
                Object[] param = {id, filePath};
                db.update(ADD_NEW_FILE, param);
            }
        }
    }
}
