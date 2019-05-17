package edu.fpt.comp1640.servlet;

import edu.fpt.comp1640.database.Database;
import edu.fpt.comp1640.utils.DatabaseUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(name = "AdminServlet")
public class AdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        response.setContentType("application/json");

        if (map.containsKey("get")) {
            getEvent(request, response);
        } else if (map.containsKey("update")) {
            updateEvent(request, response);
        } else if (map.containsKey("create")) {
            createEvent(request, response);
        } else if (map.containsKey("delete")) {
            deleteEvent(request, response);
        }
    }

    private void getEvent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //language=SQL
        String sql = "SELECT id, name, year, first_deadline, second_deadline  FROM PublishYears PY LIMIT 10 OFFSET ?";
        String[] fields = new String[]{"id", "name", "year", "first_deadline", "second_deadline"};
        int offset;
        try {
            offset = Integer.parseInt(request.getParameter("page")) * 10;
        } catch (Exception e) {
            offset = 0;
        }
        Object[] param = new Object[] {offset};

        try {
            response.getWriter().write(DatabaseUtils.getJSON(sql, fields, param));
        } catch (Exception e) {
            response.getWriter().write("{\"error\": \"Cannot fetch data!\"}");
        }
    }

    private void createEvent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //language=SQL
        String sql = "INSERT INTO PublishYears (name, year, first_deadline, second_deadline) VALUES (?, ?, ?, ?)";

        String name = request.getParameter("name");
        String year = request.getParameter("year");
        String first_deadline = request.getParameter("first_deadline");
        String second_deadline = request.getParameter("second_deadline");

        String[] param = new String[] {name, year, first_deadline, second_deadline};
        try (Database db = new Database()) {
            ResultSet updated = db.insert(sql, param);
            if (updated.next()) {
                response.getWriter().write("{\"success\": \"The publish event has been created!\"}");
            } else {
                response.getWriter().write("{\"error\": \"Cannot create the publish event!\"}");
            }
        } catch (SQLException e) {
            response.getWriter().write("{\"error\": \"Cannot create the publish event!\"}");
        }
    }

    private void updateEvent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //language=SQL
        String sql = "UPDATE PublishYears SET name = ?, year = ?, first_deadline = ?, second_deadline = ? WHERE id = ?";

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String year = request.getParameter("year");
        String first_deadline = request.getParameter("first_deadline");
        String second_deadline = request.getParameter("second_deadline");

        String[] param = new String[] {name, year, first_deadline, second_deadline, id};
        try (Database db = new Database()) {
            int update = db.update(sql, param);
            if (update > 0) {
                response.getWriter().write("{\"success\": \"The publish event has been updated!\"}");
            } else {
                response.getWriter().write("{\"error\": \"Cannot update the publish event!\"}");
            }
        } catch (SQLException e) {
            response.getWriter().write("{\"error\": \"Cannot update the publish event!\"}");
        }
    }

    private void deleteEvent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //language=SQL
        String sql = "DELETE FROM PublishYears WHERE id = ?";
        String id = request.getParameter("id");
        String[] param = new String[] {id};

        try (Database db = new Database()) {
            int update = db.update(sql, param);

            if (update > 0) {
                response.getWriter().write("{\"success\": \"The publish event has been deleted!\"}");
            } else {
                response.getWriter().write("{\"error\": \"Cannot delete the publish event!\"}");
            }
        } catch (SQLException e) {
            response.getWriter().write("{\"error\": \"Cannot delete the publish event!\"}");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
