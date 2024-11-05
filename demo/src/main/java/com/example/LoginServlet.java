package com.example;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {
    private static final Map<String, String> users = new HashMap<>();

    static {
        // Predefined user for testing
        users.put("testuser", "password123");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (users.containsKey(username) && users.get(username).equals(password)) {
            response.getWriter().println("<h1>Login successful!</h1>");
        } else {
            response.getWriter().println("<h1>Login failed!</h1>");
        }
    }
}