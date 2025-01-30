<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>

<!DOCTYPE html>
<html>
<head>
    <title>Om oss</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>
    <custom:navbar currentPage="${currentPage}" loggedIn="${loggedIn}" />
    <main class="container">
        <div class="grid">
            <section>
                <hgroup>
                    <h2>Om oss</h2>
                    <h3>test</h3>
                </hgroup>
                <p>litt info</p>
            </section>
        </div>
    </main>
    <script src="/js/layoutScript.js"></script>
    <custom:footer />
</body>
</html>