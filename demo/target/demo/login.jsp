<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Login</title>
    <link rel="stylesheet" href="styles.css" />
  </head>
  <body>
    <header>
      <h1>Login</h1>
    </header>

    <main>
      <div class="form-container">
        <c:if test="${param.error == 'invalid'}">
          <p class="error-message">Invalid username or password</p>
        </c:if>

        <form action="login" method="post">
          <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required />
          </div>

          <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required />
          </div>

          <div class="form-group">
            <button type="submit">Login</button>
          </div>

          <p>Don't have an account? <a href="register.jsp">Register here</a></p>
        </form>
      </div>
    </main>
  </body>
</html>
