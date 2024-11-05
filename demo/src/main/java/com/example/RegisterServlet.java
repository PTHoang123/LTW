package com.example;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class RegisterServlet extends HttpServlet {
    private static final Map<String, String> users = new HashMap<>();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (users.containsKey(username)) {
            response.getWriter().println("<h1>Username already exists!</h1>");
        } else {
            users.put(username, password);
            response.getWriter().println("<h1>Registration successful!</h1>");
        }
    }
}