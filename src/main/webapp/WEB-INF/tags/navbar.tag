<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@1/css/pico.min.css">

<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ attribute name="currentPage" required="true" type="java.lang.String" %>
<%@ attribute name="loggedIn" required="true" type="java.lang.Boolean" %>
<%@ attribute name="role" required="false" type="java.lang.String" %>

<nav class="container-fluid" style="display: flex; position: relative; align-items: center; height: 120px; border-bottom: 2px solid #ccc;">
    <li style="flex: 0 0 auto; z-index: 2;">
         <a href="/home" class="logo-link">
        <img src="/images/ceme_logo.png" alt="CE ME Logo" style="height: 100px; display: block;">
        <a>
    </li>
    <ul style="position: absolute; left: 50%; transform: translateX(-50%); display: flex; justify-content: center; z-index: 1;">
        <li class="${currentPage == 'home' ? 'active' : ''}"><a href="/home">Hjem</a></li>
        <li class="${currentPage == 'veileder' ? 'active' : ''}"><a href="/veileder">Veileder</a></li>
        <li class="${currentPage == 'about' ? 'active' : ''}"><a href="/about">Om oss</a></li>
        <li class="${currentPage == 'contact' ? 'active' : ''}"><a href="/contact">Kontakt</a></li>
        
        <c:if test="${role == 'ROLE_ADMIN'}">
            <li><a href="/admin">Admin Dashboard</a></li>
        </c:if>
    </ul>
    <ul style="flex: 0 0 auto; display: flex; justify-content: flex-end; z-index: 2;">
        <li>
            <c:choose>
                <c:when test="${loggedIn}">
                    <a href="/logout" role="button">Logout</a>
                </c:when>
                <c:otherwise>
                    <a href="/login" role="button">Login</a>
                    <a href="/register" role="button">Registrer</a>
                </c:otherwise>
            </c:choose>
        </li>
    </ul>
</nav>
