package edu.fpt.comp1640.servlet;

import edu.fpt.comp1640.model.user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        //language=SQL
        StringBuilder sql = new StringBuilder("SELECT SM.id, SM.name, student_id, submit_time, chosen, rollnumber, U.name FROM Submissions SM JOIN Students S ON SM.student_id = S.id JOIN Users U ON S.id = U.role_id WHERE U.role = 3 ");
        String[] fields = {"SM_id", "SM_name", "student_id", "publish_year", "submit_time", "chosen", "faculty_id", "rollnumber", "U_id", "U_name", "role_id"};

        response.setContentType("application/json");
        try {
            List params = buildQuery(request, sql);
            String json = getJSON(sql.toString(), fields, params.toArray());
            response.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().format("{\"error\": \"%s\"}", e.getMessage());
        }
    }

    private static List buildQuery(HttpServletRequest request, StringBuilder sql) {
        Map<String, String[]> map = request.getParameterMap();
        ArrayList<Integer> params = new ArrayList<>(3);

        User user = (User) request.getSession().getAttribute("user");
        if (user.getRole() == User.ROLE_STUDENT) {
            sql.append("AND S.id = ? ");
            params.add(user.getRoleId());
        } else {
            sql.append("AND faculty_id = ? ");
            params.add(Integer.parseInt(map.get("faculty")[0]));
        }

        sql.append("ORDER BY submit_time LIMIT 10 ");
        if (map.containsKey("page")) {
            sql.append("OFFSET ?");
            params.add(Integer.parseInt(map.get("page")[0]) * 10);
        }
        return params;
    }
}
