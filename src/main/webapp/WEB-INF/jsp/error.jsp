<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>

<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <custom:navbar currentPage="${currentPage}" loggedIn="${loggedIn}"/>
        <main class="container">
            <div class="grid">
                <section>
                    <hgroup>
                        <h2>Woops</h2>
                        <h3>Her gikk det galt</h3>
                    </hgroup>
                    <p>Vi beklager</p>
                </section>
            </div>
        </main>
    <script src="/js/layoutScript.js"></script>
    <custom:footer />
</body>
</html>