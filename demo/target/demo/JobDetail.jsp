<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Job Detail</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/styles.css"
    />
  </head>
  <body>
    <header>
      <h1>Job Detail</h1>
    </header>
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
