<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Job Added Successfully</title>
    <link rel="stylesheet" href="styles.css" />
  </head>
  <body>
    <header>
      <h1>Success!</h1>
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

      <div class="success-message">
        <h2>Job Posted Successfully</h2>
        <p>Your job listing has been added to our database.</p>
        <a href="AllJob.jsp" class="button">View All Jobs</a>
      </div>
    </main>
  </body>
</html>
