<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>

<!DOCTYPE html>
<html>
<head>
    <title>PDF Options</title>
    <link href="/css/veileder.css" rel="stylesheet">
</head>
<body>
<custom:navbar currentPage="${currentPage}" loggedIn="${loggedIn}"/>

<main class="container">
    
    <div class="grid">
        <h1>Veileder PDF</h1>
        <p>Choose an option below to proceed with your PDF:</p>
        <ul>
            <li><a href="/veileder/view-pdf" role="button" target="_blank">View PDF in New Tab</a></li>
            <li><a href="/veileder/download-pdf" role="button" >Download PDF</a></li>
        </ul>
    </div>
    <div class="grid">
        <iframe src="/veileder/view-pdf" style="width:100%; height:600px; border: 1px solid #1d1d1d; "></iframe>
    </div>
</main>
    <script src="/js/layoutScript.js"></script>
    <script src="/js/veileder.js"></script>
    <custom:footer />
</body>
</html>
