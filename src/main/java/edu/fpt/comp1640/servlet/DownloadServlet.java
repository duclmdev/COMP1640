package edu.fpt.comp1640.servlet;

import edu.fpt.comp1640.model.user.User;
import edu.fpt.comp1640.utils.CompressUtils;
import edu.fpt.comp1640.utils.DatabaseUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@WebServlet(name = "DownloadServlet", urlPatterns = {"/download"})
public class DownloadServlet extends HttpServlet {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> map = request.getParameterMap();

        if (map.containsKey("id")) {
            downloadSingleFile(map, response);
        } else if (map.containsKey("submission")) {
            downloadSubmissions(map, request, response);
        }
    }

    private void downloadSubmissions(Map<String, String[]> map, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //language=SQL
            String sql = "SELECT disk_location FROM Submissions S JOIN Files F ON S.id = F.submission_id WHERE S.id = ?";
            User user = (User) request.getSession().getAttribute("user");
            String username = user.getUsername();

            String submissions = map.get("submission")[0];
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String now = sdf.format(new Date());
            File fileOut = new File(String.format("%s-%s.zip", username, now));

            DatabaseUtils.getResult(sql, new Object[]{submissions}, rs -> {
                while (rs.next()) {
                    CompressUtils.zipFile(rs.getString(1), fileOut);
                }
                sendFile(response, fileOut);
            });
        } catch (Exception e) {
            response.setContentType("application/json");
            response.getWriter().print("{\"error\": \"You don't have privilege to download this resource!\"}");
        }
    }

    private void downloadSingleFile(Map<String, String[]> map, HttpServletResponse response) {
        String fileSql = "SELECT disk_location FROM Files WHERE id = ?";
        try {
            String id = map.get("id")[0];
            DatabaseUtils.each(fileSql, new Object[]{id}, rs -> {
                String address = rs.getString("disk_location");
                sendFile(response, new File(address));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendFile(HttpServletResponse response, File file) throws IOException {
        response.setHeader("Content-Disposition", String.format("attachment; filename=%s", file.getName()));
        try (OutputStream out = response.getOutputStream(); InputStream in = new FileInputStream(file)) {
            byte[] buffer = new byte[4096];
            int length;
            while ((length = in.read(buffer)) > -1) {
                out.write(buffer, 0, length);
            }
            out.flush();
        }
    }

    private int[] convertArray(String[] array) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            try {
                result[i] = Integer.parseInt(array[i]);
            } catch (Exception ignored) {
            }
        }
        return result;
    }

}
