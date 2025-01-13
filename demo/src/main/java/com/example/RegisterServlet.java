package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
                try (Connection connection = DatabaseConnection.getConnection()) {
                    // Check if username or email exists
                    String checkSql = "SELECT * FROM users WHERE username = ? OR email = ?";
                    try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
                        checkStmt.setString(1, username);
                        checkStmt.setString(2, email);
                        if (checkStmt.executeQuery().next()) {
                            response.sendRedirect("/demo/register.jsp?error=exists");
                            return;
                        }
                    }
                
                    String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, username);
                        statement.setString(2, password);
                        statement.setString(3, email);
                        statement.executeUpdate();
                        response.sendRedirect("/demo/AllJob.jsp");
                    }
                } catch (SQLException e) {
                    throw new ServletException(e);
                }
    }
}