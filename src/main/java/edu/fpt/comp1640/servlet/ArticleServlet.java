package edu.fpt.comp1640.servlet;

import edu.fpt.comp1640.utils.DatabaseUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "ArticleServlet")
public class ArticleServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");

        Map<String, String[]> map = request.getParameterMap();
        if (map.containsKey("id")) {
            try {
                returnInfo(request, response);
            } catch (Exception e) { }
        }
    }

    private void returnInfo(HttpServletRequest map, HttpServletResponse response) throws Exception {
        String id = map.getParameter("id");
        System.out.println(id);
        Object[] param = {id};
        //language=SQL
        String sqlSM = "SELECT name, submit_time, chosen FROM Submissions SM WHERE SM.id = ?";
        String jsonSM = DatabaseUtils.getJSON(sqlSM, new String[]{"name", "submit_time", "chosen"}, param);

        //language=SQL
        String sql = "SELECT F.disk_location, F.id AS file_id FROM Submissions SM JOIN Files F ON SM.id = F.submission_id WHERE SM.id = ?";
        String json = DatabaseUtils.getJSON(sql, new String[]{"disk_location", "id"}, param);

        response.setContentType("application/json");
        response.getWriter().write(String.format("{\"info\": %s, \"files\": %s}", jsonSM, json));

    }
}
