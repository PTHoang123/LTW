package com.example;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class JobDetailServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int jobId = Integer.parseInt(request.getParameter("id"));

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT j.*, u.username as created_by_username FROM jobs j " +
                        "LEFT JOIN users u ON j.created_by = u.id WHERE j.id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, jobId);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    Job job = new Job();
                    job.setId(rs.getInt("id"));
                    job.setJobTitle(rs.getString("job_title"));
                    job.setCompanyName(rs.getString("company_name"));
                    job.setLocation(rs.getString("location"));
                    job.setJobType(rs.getString("job_type"));
                    job.setSalary(rs.getString("salary"));
                    job.setDescription(rs.getString("description"));
                    job.setCreatedBy(rs.getInt("created_by"));
                    job.setCreatedByUsername(rs.getString("created_by_username"));
                    
                    request.setAttribute("job", job);
                    request.getRequestDispatcher("/Detail.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}