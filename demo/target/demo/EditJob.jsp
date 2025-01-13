
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Job</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <header>
        <h1>Edit Job</h1>
    </header>
    
    <main>
        <form action="editJob" method="post">
            <input type="hidden" name="id" value="${job.id}">
            
            <div class="form-group">
                <label for="job-title">Job Title:</label>
                <input type="text" id="job-title" name="job-title" value="${job.jobTitle}" required>
            </div>
            
            <div class="form-group">
                <label for="company-name">Company Name:</label>
                <input type="text" id="company-name" name="company-name" value="${job.companyName}" required>
            </div>
            
            <div class="form-group">
                <label for="location">Location:</label>
                <input type="text" id="location" name="location" value="${job.location}" required>
            </div>
            
            <div class="form-group">
                <label for="job-type">Job Type:</label>
                <select id="job-type" name="job-type" required>
                    <option value="full-time" ${job.jobType == 'full-time' ? 'selected' : ''}>Full-Time</option>
                    <option value="part-time" ${job.jobType == 'part-time' ? 'selected' : ''}>Part-Time</option>
                    <option value="contract" ${job.jobType == 'contract' ? 'selected' : ''}>Contract</option>
                    <option value="internship" ${job.jobType == 'internship' ? 'selected' : ''}>Internship</option>
                </select>
            </div>
            
            <div class="form-group">
                <label for="salary">Salary:</label>
                <input type="text" id="salary" name="salary" value="${job.salary}" required>
            </div>
            
            <div class="form-group">
                <label for="description">Description:</label>
                <textarea id="description" name="description" rows="4" required>${job.description}</textarea>
            </div>
            
            <div class="form-group">
                <button type="submit">Update Job</button>
                <a href="AllJob.jsp" class="button">Cancel</a>
            </div>
        </form>
    </main>
</body>
</html>