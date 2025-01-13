<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Register</title>
    <link rel="stylesheet" href="styles.css" />
  </head>
  <body>
    <header>
      <h1>Register</h1>
    </header>

    <main>
      <div class="form-container">
        <c:if test="${param.error == 'exists'}">
          <p class="error-message">Username already exists</p>
        </c:if>

        <form action="register" method="post">
          <div class="form-group">
            <label for="username">Username:</label>
            <input
              type="text"
              id="username"
              name="username"
              required
              pattern="[A-Za-z0-9_]{3,20}"
              title="3-20 characters, letters, numbers and underscore only"
            />
          </div>

          <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required />
          </div>

          <div class="form-group">
            <label for="password">Password:</label>
            <input
              type="password"
              id="password"
              name="password"
              required
              pattern=".{6,}"
              title="Six or more characters"
            />
          </div>

          <div class="form-group">
            <label for="confirm-password">Confirm Password:</label>
            <input
              type="password"
              id="confirm-password"
              name="confirm-password"
              required
              oninput="checkPasswordMatch(this);"
            />
          </div>

          <div class="form-group">
            <button type="submit">Register</button>
          </div>

          <p>Already have an account? <a href="login.jsp">Login here</a></p>
        </form>
      </div>
    </main>
  </body>
  <script>
    function checkPasswordMatch(input) {
      if (input.value != document.getElementById("password").value) {
        input.setCustomValidity("Passwords must match");
      } else {
        input.setCustomValidity("");
      }
    }
  </script>
</html>
