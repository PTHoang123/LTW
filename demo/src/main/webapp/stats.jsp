<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Job Statistics</title>
    <link rel="stylesheet" href="styles.css" />
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
  </head>
  <body>
    <header>
      <h1>Job Statistics</h1>
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
            <li class="right"><a href="logout">Logout</a></li>
          </c:otherwise>
        </c:choose>
      </ul>
    </nav>
    <main>
      <div class="charts-container">
        <div class="chart-wrapper">
          <canvas id="salaryChart"></canvas>
        </div>
      </div>
    </main>

    <script>
      fetch("/demo/getJobStats")
        .then((response) => response.json())
        .then((data) => {
          // Salary Chart
          const salaryCtx = document
            .getElementById("salaryChart")
            .getContext("2d");
          new Chart(salaryCtx, {
            type: "bar",
            data: {
              labels: Object.keys(data.salaryByType),
              datasets: [
                {
                  label: "Average Salary by Job Type (VND)",
                  data: Object.values(data.salaryByType),
                  backgroundColor: "rgba(54, 162, 235, 0.5)",
                },
              ],
            },
            options: {
              responsive: true,
              scales: {
                y: {
                  beginAtZero: true,
                  ticks: {
                    callback: (value) => value.toLocaleString() + " VND",
                  },
                },
              },
            },
          });
        })
        .catch((error) => console.error("Error:", error));
    </script>
  </body>
</html>
