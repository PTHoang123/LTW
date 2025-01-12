package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

public class AllJobsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String searchTerm = request.getParameter("search");
        String jobType = request.getParameter("type");
        
        System.out.println("Search Term: " + searchTerm); // Debug
        System.out.println("Job Type: " + jobType); // Debug
        
        List<Job> jobs = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM jobs WHERE 1=1");
        List<Object> params = new ArrayList<>();
        
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            sql.append(" AND (LOWER(job_title) LIKE LOWER(?) OR LOWER(company_name) LIKE LOWER(?) OR LOWER(location) LIKE LOWER(?))");
            String searchPattern = "%" + searchTerm.trim() + "%";
            params.add(searchPattern);
            params.add(searchPattern);
            params.add(searchPattern);
        }
        
        if (jobType != null && !jobType.equals("all")) {
            sql.append(" AND LOWER(job_type) = LOWER(?)");
            params.add(jobType);
        }
        
        System.out.println("SQL Query: " + sql.toString()); // Debug
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
                System.out.println("Param " + (i+1) + ": " + params.get(i)); // Debug
            }
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Job job = new Job();
                job.setId(rs.getInt("id"));
                job.setJobTitle(rs.getString("job_title"));
                job.setCompanyName(rs.getString("company_name"));
                job.setLocation(rs.getString("location"));
                job.setJobType(rs.getString("job_type"));
                job.setSalary(rs.getString("salary"));
                job.setDescription(rs.getString("description"));
                job.setCreatedBy(rs.getInt("created_by"));
                jobs.add(job);
            }
            
            System.out.println("Found " + jobs.size() + " jobs"); // Debug
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonResponse = new Gson().toJson(jobs);
        System.out.println("JSON Response: " + jsonResponse); // Debug
        response.getWriter().write(jsonResponse);
    }
}