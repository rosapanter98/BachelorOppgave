<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>


<!DOCTYPE html>
<html>
<head>
    <title>Veileder</title>
    <link href="/css/veileder.css" rel="stylesheet">
</head>

<body>
    <custom:navbar currentPage="${currentPage}" loggedIn="${loggedIn}" />
    <main class="container">
        <div class="grid">
            <!-- Check if the list is not empty and display form -->
            <c:if test="${not empty sporsmolList}">
                <form action="/veileder" method="POST">
                    <div>
                        <c:forEach var="sporsmol" items="${sporsmolList}">
                            <div class="label-checkbox">
                                <label>${sporsmol.tittel}</label>
                                <input type="checkbox" name="jaSvarIds" value="${sporsmol.id}"/>
                                <section id="section-${sporsmol.id}" style="display: none;">
                                    <p>${sporsmol.beskrivelse}</p>
                                </section>
                            </div>
                        </c:forEach>
                    </div>
                    <div>
                        <button type="submit" style="margin-top: 10px;">Submit</button>
                    </div>
                </form>
            </c:if>
        </div>
    </main>
    
    <script src="/js/layoutScript.js"></script>
    <script src="/js/veileder.js"></script>
    <custom:footer />
</body>
</html>
