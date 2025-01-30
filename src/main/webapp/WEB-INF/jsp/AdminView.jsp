
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>

<!DOCTYPE html>
<html>
<head>
    <title>Min side</title>
    <link href="/css/styles.css" rel="stylesheet">
</head>
<body>
    <custom:navbar currentPage="${currentPage}" loggedIn="${loggedIn}" />
    <div>
        <main class="containeradmin">
        <!-- Ny registrer-knapp -->
        <a href="/nyfoering" role="button">Legg til ny føring i databasen</a>
        <a href="/slettfoering" role="button">Slett føring i databasen</a>
        <a href="/update" role="button">Oppdater føring i databasen</a>
    </main>
    </div>
    <script src="/js/layoutScript.js"></script>
    <custom:footer />
</body>

</html>
