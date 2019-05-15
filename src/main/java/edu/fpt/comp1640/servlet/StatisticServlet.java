package edu.fpt.comp1640.servlet;

import edu.fpt.comp1640.utils.DatabaseUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "StatisticServlet")
public class StatisticServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        response.setContentType("application/json");

        try {
            String result = "";
            if (map.containsKey("year")) {
                result = getSubmissionStatistic(map, response);
            }
            response.getWriter().write(result);
        } catch (Exception e) {
            response.getWriter().print("{\"error\": \"Cannot fetch data!\"}");
        }
    }

    private String getSubmissionStatistic(Map<String, String[]> map, HttpServletResponse response) throws Exception {
        //language=SQL
        String sql = "SELECT count(SM.id) AS count, publish_year, faculty_id, strftime('%Y-%m-%d', submit_time) AS submit_date, count(chosen) AS chosen FROM Submissions SM JOIN Students S ON SM.student_id = S.id WHERE SM.publish_year IN (?) GROUP BY submit_date, faculty_id ORDER BY submit_date";
        // AND submit_time >= (SELECT DATETIME(max(submit_time), '-7 day') FROM Submissions)
        String year = map.get("year")[0];
        return DatabaseUtils.getJSON(
                sql,
                new String[]{"count", "publish_year", "faculty_id", "submit_date", "chosen"},
                new Object[]{year});
    }
}
