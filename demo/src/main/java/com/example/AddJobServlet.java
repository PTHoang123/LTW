package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddJobServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get userId from session
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.sendRedirect("/demo/login.html");
            return;
        }

        String jobTitle = request.getParameter("job-title");
        String companyName = request.getParameter("company-name");
        String location = request.getParameter("location");
        String jobType = request.getParameter("job-type");
        String salary = request.getParameter("salary");
        String description = request.getParameter("description");

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO jobs (job_title, company_name, location, job_type, salary, description, created_by) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, jobTitle);
                statement.setString(2, companyName);
                statement.setString(3, location);
                statement.setString(4, jobType);
                statement.setString(5, salary);
                statement.setString(6, description);
                statement.setInt(7, userId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("/demo/AllJob.jsp");
    }
}