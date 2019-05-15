package edu.fpt.comp1640.servlet;

import edu.fpt.comp1640.model.user.Coordinator;
import edu.fpt.comp1640.model.user.Guest;
import edu.fpt.comp1640.model.user.Student;
import edu.fpt.comp1640.model.user.User;
import edu.fpt.comp1640.utils.DatabaseUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class DashboardServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //language=SQL
        StringBuilder sql = new StringBuilder("SELECT count(S.id) AS submitted, count(CASE S.chosen WHEN 1 THEN 1 ELSE NULL END) AS chosen, first_deadline, second_deadline, PY.name FROM Submissions S JOIN Students S2 ON S.student_id = S2.id JOIN PublishYears PY ON S.publish_year = PY.id WHERE strftime('%Y', 'now') = PY.year");

        response.setContentType("application/json");
        try {
            User user = (User) request.getSession().getAttribute("user");
            ArrayList<Integer> param = new ArrayList<>();
            if (user instanceof Student) {
                sql.append(" AND S.student_id = ?");
                param.add(user.getRoleId());
            } else if (user instanceof Coordinator) {
                Coordinator coordinator = (Coordinator) user;
                sql.append(" AND S2.faculty_id = ?");
                param.add(coordinator.getFacultyId());
            } else if (user instanceof Guest) {
                Guest guest = (Guest) user;
                sql.append(" AND S2.faculty_id = ?");
                param.add(guest.getFacultyId());
            }
            String json = DatabaseUtils.getJSON(
                    sql.toString(),
                    new String[]{"submitted", "chosen", "first_deadline", "second_deadline", "name"},
                    param.toArray()
            );
            response.getWriter().print(json);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("{\"error\": \"Cannot fetch data!\"}");
        }
    }
}
