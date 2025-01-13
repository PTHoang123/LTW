package com.example;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.google.gson.Gson;


public class StatsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            Map<String, Object> stats = new HashMap<>();
            
            // Average salary by job type
            String salaryQuery = "SELECT job_type, AVG(CAST(REPLACE(REPLACE(salary, 'VND', ''), ' ', '') AS DECIMAL)) as avg_salary " +"FROM jobs GROUP BY job_type";
            
            // Job count by type
            String countQuery = "SELECT job_type, COUNT(*) as count FROM jobs GROUP BY job_type";
            
            try (PreparedStatement stmt1 = conn.prepareStatement(salaryQuery);
                     PreparedStatement stmt2 = conn.prepareStatement(countQuery)) {
                
                Map<String, Double> salaryStats = new HashMap<>();
                ResultSet rs = stmt1.executeQuery();
                while (rs.next()) {
                    salaryStats.put(rs.getString("job_type"), rs.getDouble("avg_salary"));
                }
                stats.put("salaryByType", salaryStats);
                
                Map<String, Integer> countStats = new HashMap<>();
                rs = stmt2.executeQuery();
                while (rs.next()) {
                    countStats.put(rs.getString("job_type"), rs.getInt("count"));
                }
                stats.put("countByType", countStats);
            }
            
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(stats));
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}