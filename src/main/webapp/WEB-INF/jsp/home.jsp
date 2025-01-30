<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>

<!DOCTYPE html>
<html>
<head>
    <title>Home Page</title>
</head>
<body>
    <custom:navbar currentPage="${currentPage}" loggedIn="${loggedIn}"/>
        <main class="container">
            <div class="grid">
                <section>
                    <hgroup>
                        <h2>Welcome to CE ME</h2>
                        <h3>Your Business Solutions Partner</h3>
                    </hgroup>
                    <p>CE ME provides cutting-edge solutions tailored to your business needs. Discover our wide range of services and how we can help you grow.</p>
                </section>
            </div>
        </main>
    <script src="/js/layoutScript.js"></script>
    <custom:footer />
</body>
</html>