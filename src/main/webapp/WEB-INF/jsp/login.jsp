
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link href="/css/styles.css" rel="stylesheet">
    <!-- Add link to CSS for styling if necessary -->
</head>
<body>
    <custom:navbar currentPage="${currentPage}" loggedIn="${loggedIn}" />
    <h2>Login</h2>
    <div id="loginregister-container">
        <form action="${pageContext.request.contextPath}/login" method="POST">
            <label for="username">Email</label>
            <input type="text" id="username" name="username" required>
            
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
            
            <button type="submit" class="primary">Login</button>
        </form>
    </div>
    <custom:footer />
</body>
</html>