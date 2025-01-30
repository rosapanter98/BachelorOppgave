<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>

<!DOCTYPE html>
<html>
<head>
    <title>Delete Entities</title>
    <script src="/js/deleteEntities.js" defer></script>
    <link rel="stylesheet" href="/css/delete.css">
    </style>
</head>
<body>
<custom:navbar currentPage="${currentPage}" loggedIn="${loggedIn}" />
<main class="container">
    <div class="grid">
        <!-- Questions Section -->
        <button id="toggleQuestions">Questions</button>
        <div id="questionsList">
            <c:forEach var="sporsmol" items="${sporsmolList}">
                <div class="question-item">
                    <input type="checkbox" class="delete-checkbox" value="${sporsmol.id}" />
                    <span class="question-title">${sporsmol.tittel}</span>
                    <div class="question-details">
                        <strong>Children:</strong>
                        <c:forEach var="child" items="${sporsmol.children}">
                            <span>${child.tittel}</span>
                        </c:forEach>
                        <strong>Parents:</strong>
                        <c:forEach var="parent" items="${sporsmol.parents}">
                            <span>${parent.tittel}</span>
                        </c:forEach>
                        <strong>Values:</strong>
                        <c:forEach var="verdi" items="${sporsmol.verdier}">
                            <span>${verdi.tittel}</span>
                        </c:forEach>
                    </div>
                </div>
            </c:forEach>
            <button id="deleteQuestions">Delete Selected Questions</button>
        </div>
    </div>
    <div class="grid">
        <!-- Standards Section -->
        <button id="toggleStandards">Standards</button>
        <div id="standardsList">
            <c:forEach var="standard" items="${standardsList}">
                <div class="standard-item">
                    <input type="checkbox" class="delete-checkbox-standard" value="${standard.id}" />
                    <span class="standard-title">${standard.tittel} (${standard.standardNummer})</span>
                    <div class="standard-details">
                        <strong>Year of Publication:</strong> ${standard.publiseringsAr}<br/>
                        <strong>Amendments:</strong> ${standard.amendments}<br/>
                        <strong>Verdi Foringer:</strong>
                        <c:forEach var="foring" items="${standard.verdiFøringer}">
                            <div>
                                ${foring.verdi.tittel} - ${foring.numeriskVerdi}
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:forEach>
            <button id="deleteStandards">Delete Selected Standards</button>
        </div>
    </div>
    </main>
    <main class="container">
        <button id="toggleVerdier">Values</button>
        <div class="grid">
        <!-- Values Section -->
        <div id="verdiList">
            <c:forEach var="verdi" items="${verdiList}">
                <div class="verdi-item">
                    <input type="checkbox" class="delete-checkbox-verdi" value="${verdi.id}" />
                    <span class="verdi-title">${verdi.tittel}</span>
                    <div class="verdi-details">
                        <strong>Verdi Foringer:</strong>
                        <c:forEach var="foring" items="${verdi.verdiFøringer}">
                            <div>
                                ${foring.standard.tittel} - ${foring.numeriskVerdi}
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:forEach>
            <button id="deleteVerdier">Delete Selected Values</button>
        </div>
    </div>
    <div class="grid">
        <!-- Regulations Section -->
        <button id="toggleForskrifter">Forskrifter</button>
        <div id="forskriftList">
            <c:forEach var="forskrift" items="${forskriftList}">
                <div class="forskrift-item">
                    <input type="checkbox" class="delete-checkbox-forskrift" value="${forskrift.id}" />
                    <span class="forskrift-title">${forskrift.tittel}</span>
                    <div class="forskrift-details">
                        <strong>Beskrivelse:</strong> ${forskrift.beskrivelse}<br/>
                        <strong>Publiseringsdato:</strong> <fmt:formatDate value="${forskrift.publiseringsdato}" pattern="yyyy-MM-dd"/><br/>
                        <strong>URL:</strong> <a href="${forskrift.url}">${forskrift.url}</a>
                    </div>
                </div>
            </c:forEach>
            <button id="deleteForskrifter">Delete Selected Forskrifter</button>
        </div>
    </div>
</main>
<custom:footer />
<script src="/js/layoutScript.js"></script>
</body>
</html>
