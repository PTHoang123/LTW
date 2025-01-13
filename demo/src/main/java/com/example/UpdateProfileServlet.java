package com.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.File;
import java.nio.file.*;
import java.sql.*;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1MB
    maxFileSize = 1024 * 1024 * 10,  // 10MB
    maxRequestSize = 1024 * 1024 * 15 // 15MB
)
public class UpdateProfileServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "uploads";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String uploadPath = getServletContext().getRealPath("") + UPLOAD_DIR;
        Files.createDirectories(Paths.get(uploadPath));

        String imageUrl = null;
        String cvUrl = null;

        // Handle file uploads
        for (Part part : request.getParts()) {
            String fileName = part.getSubmittedFileName();
            if (fileName != null && !fileName.isEmpty()) {
                String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
                String filePath = uploadPath + File.separator + uniqueFileName;
                
                part.write(filePath);
                
                if (part.getName().equals("profile-image")) {
                    imageUrl = UPLOAD_DIR + "/" + uniqueFileName;
                } else if (part.getName().equals("cv-file")) {
                    cvUrl = UPLOAD_DIR + "/" + uniqueFileName;
                }
            }
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            StringBuilder sql = new StringBuilder("UPDATE users SET ");
            boolean needComma = false;

            if (request.getParameter("new-username") != null && !request.getParameter("new-username").isEmpty()) {
                sql.append("username = ?");
                needComma = true;
            }
            if (request.getParameter("new-email") != null && !request.getParameter("new-email").isEmpty()) {
                if (needComma) sql.append(", ");
                sql.append("email = ?");
                needComma = true;
            }
            if (request.getParameter("new-password") != null && !request.getParameter("new-password").isEmpty()) {
                if (needComma) sql.append(", ");
                sql.append("password = ?");
                needComma = true;
            }
            if (imageUrl != null) {
                if (needComma) sql.append(", ");
                sql.append("image_url = ?");
                needComma = true;
            }
            if (cvUrl != null) {
                if (needComma) sql.append(", ");
                sql.append("cv_url = ?");
            }
            
            sql.append(" WHERE id = ?");

            try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
                int paramIndex = 1;
                
                if (request.getParameter("new-username") != null && !request.getParameter("new-username").isEmpty()) {
                    stmt.setString(paramIndex++, request.getParameter("new-username"));
                }
                if (request.getParameter("new-email") != null && !request.getParameter("new-email").isEmpty()) {
                    stmt.setString(paramIndex++, request.getParameter("new-email"));
                }
                if (request.getParameter("new-password") != null && !request.getParameter("new-password").isEmpty()) {
                    stmt.setString(paramIndex++, request.getParameter("new-password"));
                }
                if (imageUrl != null) {
                    stmt.setString(paramIndex++, imageUrl);
                }
                if (cvUrl != null) {
                    stmt.setString(paramIndex++, cvUrl);
                }
                
                stmt.setInt(paramIndex, userId);
                System.out.println("Upload Path: " + uploadPath);
                System.out.println("User ID: " + userId);
                System.out.println("SQL Query: " + sql.toString());
                stmt.executeUpdate();
                if (stmt.executeUpdate() > 0) {
                    if (request.getParameter("new-username") != null && !request.getParameter("new-username").isEmpty()) {
                        session.setAttribute("username", request.getParameter("new-username"));
                    }
                    if (request.getParameter("new-email") != null && !request.getParameter("new-email").isEmpty()) {
                        session.setAttribute("email", request.getParameter("new-email"));
                    }
                    if (imageUrl != null) {
                        session.setAttribute("imageUrl", imageUrl);
                    }
                    if (cvUrl != null) {
                        session.setAttribute("cvUrl", cvUrl);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("Profile.jsp");
    }
}