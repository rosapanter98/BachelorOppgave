<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>

<!DOCTYPE html>
<html>
<head>
    <title>Registration Page</title>
    <link href="/css/styles.css" rel="stylesheet">
</head>
<body>
    <custom:navbar currentPage="${currentPage}" loggedIn="${loggedIn}" />
    <h2>Register</h2>
    <div id="loginregister-container">
        <form action="${pageContext.request.contextPath}/register" method="POST">
            <div>
                <label for="firstName">First Name:</label>
                <input type="text" id="firstName" name="firstName" required>
            </div>
            <div>
                <label for="lastName">Last Name:</label>
                <input type="text" id="lastName" name="lastName" required>
            </div>
            <div>
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div>
                <label for="phone">Phone:</label>
                <input type="text" id="phone" name="phone" required>
            </div>
            <div>
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" oninput="checkPassword()" required>
                <span id="passwordStrength"></span>
            </div>
            <div>
                <label for="confirmPassword">Confirm Password:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" oninput="checkPassword()" required>
                <span id="passwordMatch"></span>
            </div>
            <div>
                <button type="submit">Register</button>
            </div>
            <c:if test="${!empty errorMessage}">
        <div style="color: red">${errorMessage}</div>
            </c:if>
        </form>
    </div>
    

    
    <script src="/js/layoutScript.js"></script>
    <script src="/js/passwordCheck.js"></script>
    
    <!-- Simple form for user registration -->
    <custom:footer />
</body>
</html>