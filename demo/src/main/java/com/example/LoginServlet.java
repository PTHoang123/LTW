package com.example;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT id, username, email, is_admin, image_url, cv_url FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet rs = statement.executeQuery();
                
                if (rs.next()) {
                    HttpSession session = request.getSession();
                    session.setAttribute("userId", rs.getInt("id"));
                    session.setAttribute("username", rs.getString("username"));
                    session.setAttribute("email", rs.getString("email"));
                    session.setAttribute("isAdmin", rs.getBoolean("is_admin"));
                    session.setAttribute("imageUrl", rs.getString("image_url"));
                    session.setAttribute("cvUrl", rs.getString("cv_url"));
                    
                    response.sendRedirect("/demo/AddJob.jsp");
                } else {
                    response.sendRedirect("/demo/login.jsp?error=invalid");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("/demo/login.jsp?error=database");
        }
    }
}