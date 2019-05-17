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
            String[] fields;

            if (user instanceof Student) {
                //language=SQL
                sql.append("SELECT id, name, submit_time, chosen FROM Submissions S WHERE student_id = ? ");
                fields = new String[]{"id", "name", "submit_time", "chosen"};
                param.add(user.getRoleId());
            } else {
                //language=SQL
                sql.append("SELECT SM.id, SM.name, rollnumber, U.name AS student_name, submit_time, chosen FROM Submissions SM JOIN Students S ON SM.student_id = S.id JOIN Users U ON S.id = U.role_id WHERE role = 3 ");
                fields = new String[]{"id", "sm_name", "rollnumber", "student_name", "submit_time", "chosen"};

                if (user instanceof Coordinator) {
                    sql.append("AND faculty_id = ? ");
                    param.add(((Coordinator) user).getFacultyId());
                } else if (user instanceof Guest) {
                    sql.append("AND faculty_id = ? AND chosen = 1 ");
                    param.add(((Guest) user).getFacultyId());
                } else {
                    sql.append("AND chosen = 1 ");
                }
            }
            Map<String, String[]> parameterMap = request.getParameterMap();

            if (parameterMap.containsKey("year")) {
                sql.append("AND publish_year = ? ");
                int year = Integer.parseInt(request.getParameter("year"));
                param.add(year);
            }

            sql.append("ORDER BY submit_time DESC LIMIT 10 ");
            if (parameterMap.containsKey("page")) {
                sql.append("OFFSET ? ");
                int page = Integer.parseInt(request.getParameter("page"));
                param.add((page - 1) * 10);
            }
            System.out.println(param);
            System.out.println(sql);
            String json = getJSON(sql.toString(), fields, param.toArray());
            response.getWriter().print(json);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("{\"error\": \"Cannot fetch data!\"}");
        }
    }
}
