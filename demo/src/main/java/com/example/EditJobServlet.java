package com.example;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class EditJobServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int jobId = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM jobs WHERE id = ? AND created_by = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, jobId);
                stmt.setInt(2, userId);
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
                    
                    request.setAttribute("job", job);
                    request.getRequestDispatcher("/EditJob.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        int jobId = Integer.parseInt(request.getParameter("id"));

        try (Connection conn = DatabaseConnection.getConnection()) {
            String checkSql = "SELECT created_by FROM jobs WHERE id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, jobId);
                ResultSet rs = checkStmt.executeQuery();
                
                if (!rs.next() || rs.getInt("created_by") != userId) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
            }

            String sql = "UPDATE jobs SET job_title=?, company_name=?, location=?, job_type=?, salary=?, description=? WHERE id=? AND created_by=?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, request.getParameter("job-title"));
                stmt.setString(2, request.getParameter("company-name"));
                stmt.setString(3, request.getParameter("location"));
                stmt.setString(4, request.getParameter("job-type"));
                stmt.setString(5, request.getParameter("salary"));
                stmt.setString(6, request.getParameter("description"));
                stmt.setInt(7, jobId);
                stmt.setInt(8, userId);
                
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    response.sendRedirect("/demo/AllJob.jsp");
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