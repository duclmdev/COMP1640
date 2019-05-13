package edu.fpt.comp1640.servlet;

import edu.fpt.comp1640.model.user.Coordinator;
import edu.fpt.comp1640.model.user.Guest;
import edu.fpt.comp1640.model.user.Student;
import edu.fpt.comp1640.model.user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static edu.fpt.comp1640.utils.DatabaseUtils.getJSON;

public class SubmissionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response
    ) throws ServletException, IOException {
        StringBuilder sql = new StringBuilder();
        response.setContentType("application/json");
        try {
            User user = (User) request.getSession().getAttribute("user");
            ArrayList<Integer> param = new ArrayList<>();
            String[] fields = null;

            if (user instanceof Student) {
                //language=SQL
                sql.append("SELECT id, name, submit_time, chosen FROM Submissions S WHERE student_id = ? AND publish_year = ? ORDER BY submit_time DESC LIMIT 10");
                fields = new String[]{"id", "name", "submit_time", "chosen"};
                param.add(user.getRoleId());
            } else if (user instanceof Coordinator || user instanceof Guest) {
                //language=SQL
                sql.append("SELECT SM.id, SM.name, rollnumber, U.name AS student_name, submit_time, chosen FROM Submissions SM JOIN Students S ON SM.student_id = S.id JOIN Users U ON S.id = U.role_id WHERE role = 3 AND faculty_id = 1 AND publish_year = 2 ");
                fields = new String[]{"id", "sm_name", "rollnumber", "student_name", "submit_time", "chosen"};

                if (user instanceof Coordinator) {
                    sql.append("ORDER BY submit_time DESC LIMIT 10");
                    param.add(((Coordinator) user).getFacultyId());
                } else {
                    sql.append("AND chosen = 1 ORDER BY submit_time DESC LIMIT 10");
                    param.add(((Guest) user).getFacultyId());
                }
            }
            Map<String, String[]> parameterMap = request.getParameterMap();

            if (parameterMap.containsKey("year")) {
                int year = Integer.parseInt(parameterMap.get("year")[0]);
                param.add(year);
            }

            if (parameterMap.containsKey("page")) {
                sql.append("  OFFSET ?");
                int page = Integer.parseInt(parameterMap.get("page")[0]);
                param.add((page - 1) * 10);
            }

            String json = getJSON(sql.toString(), fields, param.toArray());
            response.getWriter().print(json);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("{\"error\": \"Cannot fetch data!\"}");
        }
    }
}
