<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <header>
        <h1>User Profile</h1>
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

        <c:if test="${empty sessionScope.userId}">
            <p class="warning">Please <a href="login.jsp">login</a> to view your profile.</p>
        </c:if>

        <c:if test="${not empty sessionScope.userId}">
            <section class="profile-info">
                <h2>Profile Information</h2>
                <div class="profile-header">
                    <div class="avatar-container">
                        <c:choose>
                            <c:when test="${not empty sessionScope.imageUrl}">
                                <img src="${sessionScope.imageUrl}" alt="Profile Avatar" class="avatar"/>
                            </c:when>
                            <c:otherwise>
                                <img src="images/default-avatar.png" alt="Default Avatar" class="avatar"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="user-details">
                        <p><strong>Username:</strong> <span id="username">${sessionScope.username}</span></p>
                        <p><strong>Email:</strong> <span id="email">${sessionScope.email}</span></p>
                        <c:if test="${not empty sessionScope.cvUrl}">
                            <p><strong>CV:</strong> <a href="${sessionScope.cvUrl}" class="cv-link" download>Download CV</a></p>
                        </c:if>
                    </div>
                </div>
            </section>

            <section class="profile-update">
                <h2>Update Profile</h2>
                <form action="${pageContext.request.contextPath}/updateProfile" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="new-username">New Username:</label>
                        <input type="text" id="new-username" name="new-username">
                    </div>
                    <div class="form-group">
                        <label for="new-email">New Email:</label>
                        <input type="email" id="new-email" name="new-email">
                    </div>
                    <div class="form-group">
                        <label for="new-password">New Password:</label>
                        <input type="password" id="new-password" name="new-password">
                    </div>
                    <div class="form-group">
                        <label for="profile-image">Profile Image:</label>
                        <input type="file" id="profile-image" name="profile-image" accept="image/*">
                    </div>
                    <div class="form-group">
                        <label for="cv-file">CV Document:</label>
                        <input type="file" id="cv-file" name="cv-file" accept=".pdf,.doc,.docx">
                    </div>
                    <div class="form-group">
                        <button type="submit">Update Profile</button>
                    </div>
                </form>
            </section>
        </c:if>
    </main>
</body>
</html>