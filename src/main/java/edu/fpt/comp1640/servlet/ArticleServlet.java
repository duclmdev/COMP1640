package edu.fpt.comp1640.servlet;

import edu.fpt.comp1640.database.Database;
import edu.fpt.comp1640.utils.DatabaseUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(name = "ArticleServlet")
public class ArticleServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        Map<String, String[]> map = request.getParameterMap();
        try {
            if (map.containsKey("id")) {
                returnInfo(request, response);
            } else if (map.containsKey("select")) {
                updateArticle(request, response);
            }
        } catch (Exception e) {
            response.getWriter().write("{\"error\": \"Cannot fetch data!\"}");
        }
    }

    private void updateArticle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //language=SQL
        String sql = "UPDATE Submissions SET chosen = ? WHERE id = ?";

        String select = request.getParameter("select");
        String selectId = request.getParameter("select_id");
        int selected = select.equals("true") ? 1 : 0;
        Object[] param = {selected, selectId};

        try (Database db = new Database()) {
            db.update(sql, param);
            response.getWriter().write("{\"success\": \"Update successfully!\"}");
        } catch (SQLException e) {
            response.getWriter().write("{\"error\": \"Cannot fetch data!\"}");
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
