<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Detail</title>
    <link rel="stylesheet" href="styles.css" />
  </head>
  <body>
    <header>
      <h1>Job Detail</h1>
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
      <div class="job-detail">
        <h2>${job.jobTitle}</h2>
        <div class="job-info">
          <p><strong>Company:</strong> ${job.companyName}</p>
          <p><strong>Location:</strong> ${job.location}</p>
          <p><strong>Type:</strong> ${job.jobType}</p>
          <p><strong>Salary:</strong> ${job.salary}</p>
          <p><strong>Posted By:</strong> ${job.createdByUsername}</p>
        </div>
        <div class="job-description">
          <h3>Description</h3>
          <p>${job.description}</p>
        </div>
        <div class="actions">
          <a href="AllJob.jsp" class="button">Back to Jobs</a>
        </div>
      </div>
    </main>
  </body>
</html>
