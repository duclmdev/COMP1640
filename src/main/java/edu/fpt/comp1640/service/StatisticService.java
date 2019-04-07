package edu.fpt.comp1640.service;

import edu.fpt.comp1640.model.user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StatisticService extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        StringBuffer requestURL = request.getRequestURL();
        System.out.println(requestURL);
        String servletPath = request.getServletPath();
        System.out.println(servletPath);
        if (request.getQueryString() != null) {
            requestURL.append("?").append(request.getQueryString());
        }
        String completeURL = requestURL.toString();
        System.out.println(completeURL);

    }
}
