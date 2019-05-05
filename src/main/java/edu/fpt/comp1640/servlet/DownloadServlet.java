package edu.fpt.comp1640.servlet;

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
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

    public static void main(String[] args) {
        System.out.println(sdf.format(new Date()));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String[]> map = request.getParameterMap();

        if (map.containsKey("id")) {
            downloadSingleFile(map, response);
        } else if (map.containsKey("submissions")) {
            downloadSubmissions(map, response);
        }
    }

    private void downloadSingleFile(Map<String, String[]> map, HttpServletResponse response) {
        String fileSql = "SELECT disk_location FROM Files WHERE id = ?";
        try {
            String id = map.get("id")[0];
            DatabaseUtils.getResult(fileSql, new Object[]{id}, rs -> {
                String address = rs.getString("disk_location");
                File file = new File(address);
                response.setHeader("Content-Disposition", String.format("attachment; filename=%s", file.getName()));
                sendFile(response, file);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void downloadSubmissions(Map<String, String[]> map, HttpServletResponse response) {
        //language=SQL
        String sql = "SELECT S.id, S2.rollnumber, submit_time, disk_location FROM Submissions S JOIN Files F ON S.id = F.submission_id JOIN Students S2 ON S.student_id = S2.id WHERE S.id IN (?)";

        try {
            String[] submissions = map.get("submissions");
            DatabaseUtils.getResult(sql, new Object[]{submissions}, rs -> {
                int studentId = rs.getInt(1);
                String rollNumber = rs.getString(2);
                String date = sdf.format(rs.getDate(3).getTime());
                String location = rs.getString(4);

                System.out.format("%d %s %s %s", studentId, rollNumber, date, location);
            });
        } catch (Exception e) {
            e.printStackTrace();
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

    private void sendFile(HttpServletResponse response, File file) throws IOException {
        try (OutputStream out = response.getOutputStream(); InputStream in = new FileInputStream(file)) {
            byte[] buffer = new byte[4096];
            int length;
            while ((length = in.read(buffer)) > -1) {
                out.write(buffer, 0, length);
            }
            out.flush();
        }
    }

}
