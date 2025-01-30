<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>

<!DOCTYPE html>
<html>
<head>
    <title>Update Entities</title>
    <script src="/js/updateEntities.js" defer></script>
</head>

<body>
    <custom:navbar currentPage="${currentPage}" loggedIn="${loggedIn}" />
    <main class="container">
        <div class="grid">
        
        <!-- Questions Section -->
        <button id="toggleQuestions">Questions ▼</button>
        <div id="questionsList">
            <c:forEach var="sporsmol" items="${sporsmolList}">
                <div class="question-item">
                    <input type="text" class="update-input" value="${sporsmol.tittel}" />
                    <select multiple class="children-select">
                        <c:forEach var="child" items="${allSporsmolList}"> <!-- Assuming allSporsmolList contains all possible questions -->
                            <option value="${child.id}" ${fn:contains(sporsmol.children, child) ? 'selected' : ''}>${child.tittel}</option>
                        </c:forEach>
                    </select>
                    <select multiple class="parents-select">
                        <c:forEach var="parent" items="${allSporsmolList}">
                            <option value="${parent.id}" ${fn:contains(sporsmol.parents, parent) ? 'selected' : ''}>${parent.tittel}</option>
                        </c:forEach>
                    </select>
                    <select multiple class="values-select">
                        <c:forEach var="verdi" items="${allVerdiList}"> <!-- Assuming allVerdiList contains all possible values -->
                            <option value="${verdi.id}" ${fn:contains(sporsmol.verdier, verdi) ? 'selected' : ''}>${verdi.tittel}</option>
                        </c:forEach>
                    </select>
                    <button class="update-question" data-id="${sporsmol.id}">Update Question</button>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="grid">
        <!-- Standards Section -->
        <button id="toggleStandards">Standards ▼</button>
        <div id="standardsList">
            <c:forEach var="standard" items="${standardsList}">
                <div class="standard-item">
                    <input type="text" class="update-input-standard" value="${standard.tittel}" />
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
                    <button class="update-standard" data-id="${standard.id}">Update Standard</button>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="grid">
        <!-- Verdi Section -->
        <button id="toggleVerdier">Values ▼</button>
        <div id="verdiList">
            <c:forEach var="verdi" items="${verdiList}">
                <div class="verdi-item">
                    <input type="text" class="update-input-verdi" value="${verdi.tittel}" />
                    <span class="verdi-title">${verdi.tittel}</span>

                    <div class="verdi-details">
                        <strong>Verdi Foringer:</strong>
                        <c:forEach var="foring" items="${verdi.verdiFøringer}">
                            <div>
                                ${foring.standard.tittel} - ${foring.numeriskVerdi}
                            </div>
                        </c:forEach>
                    </div>
                    <button class="update-verdi" data-id="${verdi.id}">Update Value</button>
                </div>
            </c:forEach>
        </div>
    </div>
</main>
<script src="/js/layoutScript.js"></script>
<custom:footer />
</body>
</html>
