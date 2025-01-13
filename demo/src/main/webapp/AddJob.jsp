<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Job</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <header>
        <h1>Add Job</h1>
        <c:if test="${not empty sessionScope.username}">
            <p>Welcome, ${sessionScope.username}</p>
        </c:if>
    </header>
    <nav>
        <ul>
          <li><a href="AddJob.jsp">Add Job</a></li>
          <li><a href="AllJob.jsp">All Jobs</a></li>
          <li><a href="stats.jsp">Stats</a></li>
          <li><a href="Profile.jsp">Profile</a></li>
          <c:choose>
            <c:when test="${empty sessionScope.userId}">
              <li class="right"><a href="login.jsp">Login</a></li>
              <li class="right"><a href="register.jsp">Register</a></li>
            </c:when>
            <c:otherwise>
              <li class="right">
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
              </li>
            </c:otherwise>
          </c:choose>
        </ul>
      </nav>
    <main>
        <c:if test="${empty sessionScope.userId}">
            <p>Please <a href="login.jsp">login</a> to add jobs.</p>
        </c:if>
        
        <c:if test="${not empty sessionScope.userId}">
            <form action="addJob" method="post">
                <c:if test="${not empty requestScope.error}">
                    <div class="error-message">
                        ${requestScope.error}
                    </div>
                </c:if>
                <div class="form-group">
                    <label for="job-title">Job Title:</label>
                    <input type="text" id="job-title" name="job-title" required>
                </div>
                <div class="form-group">
                    <label for="company-name">Company Name:</label>
                    <input type="text" id="company-name" name="company-name" required>
                </div>
                <div class="form-group">
                    <label for="location">Location:</label>
                    <input type="text" id="location" name="location" required>
                </div>
                <div class="form-group">
                    <label for="job-type">Job Type:</label>
                    <select id="job-type" name="job-type" required>
                        <option value="full-time">Full-Time</option>
                        <option value="part-time">Part-Time</option>
                        <option value="contract">Contract</option>
                        <option value="internship">Internship</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="salary">Salary (VND):</label>
                    <input type="text" id="salary" name="salary" 
                           placeholder="e.g., 1000000 VND" 
                           pattern="^\d+\s*VND$" 
                           title="Enter amount followed by VND (e.g., 1000000 VND)"
                           required>
                </div>
                <div class="form-group">
                    <label for="description">Job Description:</label>
                    <textarea id="description" name="description" rows="4" required></textarea>
                </div>
                <div class="form-group">
                    <button type="submit">Submit</button>
                </div>
            </form>
        </c:if>
    </main>
</body>
</html>