<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>All Jobs</title>
    <link rel="stylesheet" href="styles.css" />
    <script src="js/jobs.js" defer></script>
  </head>
  <body>
    <header>
      <h1>All Jobs</h1>
      <c:if test="${not empty sessionScope.username}">
        <p>Welcome, ${sessionScope.username}</p>
      </c:if>
    </header>

    <main>
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
              <li class="right"><a href="logout">Logout</a></li>
            </c:otherwise>
          </c:choose>
        </ul>
      </nav>

      <!-- Search bar -->
      <div class="search-bar">
        <input type="text" id="search" placeholder="Search jobs..." />
        <select id="filter-type">
          <option value="all">All Types</option>
          <option value="full-time">Full-Time</option>
          <option value="part-time">Part-Time</option>
          <option value="contract">Contract</option>
          <option value="internship">Internship</option>
        </select>
        <button type="button">Search</button>
      </div>

      <!-- Jobs table -->
      <div class="jobs-container">
        <table class="jobs-table">
          <thead>
            <tr>
              <th>Position</th>
              <th>Company</th>
              <th>Location</th>
              <th>Type</th>
              <th>Salary</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <!-- Jobs will be loaded dynamically -->
          </tbody>
        </table>
      </div>
    </main>

    <!-- Add user ID for JavaScript -->
    <script>
      window.currentUserId = ${sessionScope.userId != null ? sessionScope.userId : 'null'};
    </script>
  </body>
</html>
