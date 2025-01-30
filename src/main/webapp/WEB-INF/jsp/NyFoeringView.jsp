<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>

<!DOCTYPE html>
<html>
<head>
    <title>Legg til Spørsmål</title>
    <script id="forskrift-data" type="application/json">${forskriftJson}</script>
    <script src="/js/standardForm.js"></script>
    <script src="/js/sporsmolForm.js"></script>
</head>
<body>
    <custom:navbar currentPage="${currentPage}" loggedIn="${loggedIn}" />

    
        <main class="container">
            <div class="grid">
            <!-- Form for creating new 'verdi' -->
            <form action="/nyfoering" method="POST">
                <div>
                    <label for="verdi_tittel">Tittel for ny verdi:</label>
                    <input type="text" id="verdi_tittel" name="verdi_tittel" required/>
                </div>
                <div>
                    <button type="submit">Opprett Verdi</button>
                </div>

            </form>
            <form action="/nyfoering" method="POST" enctype="multipart/form-data">
                <div>
                    <label for="forside_pdf">Velg forside (PDF):</label>
                    <input type="file" id="forside_pdf" name="forside_pdf" accept=".pdf">
                </div>
                <div>
                    <button type="submit">Last opp ny forside</button>
                </div>
            </form>
        </div>
    </main>

    <main class="container">
        <div class="grid">
            <!-- Form for creating new 'forskrift' -->
            <form action="/nyfoering" method="POST">
                <div>
                    <label for="forskrift_tittel">Tittel for ny forskrift:</label>
                    <input type="text" id="forskrift_tittel" name="forskrift_tittel" required/>
                </div>
                <div>
                    <label for="forskrift_beskrivelse">Beskrivelse:</label>
                    <textarea id="forskrift_beskrivelse" name="forskrift_beskrivelse" required></textarea>
                </div>
                <div>
                    <label for="forskrift_publiseringsdato">Publiseringsdato:</label>
                    <input type="date" id="forskrift_publiseringsdato" name="forskrift_publiseringsdato" required>
                </div>
                <div>
                    <label for="forskrift_url">URL:</label>
                    <input type="url" id="forskrift_url" name="forskrift_url" required>
                </div>
                <div>
                    <button type="submit">Opprett Forskrift</button>
                </div>
            </form>
        

            <!-- Form for creating new 'standard' -->
            <form action="/nyfoering" method="POST">
                <div>
                    <label for="standard_tittel">Tittel:</label>
                    <input type="text" id="standard_tittel" name="standard_tittel" required>
                </div>
                <div>
                    <label for="standard_nummer">Standard Nummer:</label>
                    <input type="text" id="standard_nummer" name="standard_nummer" required>
                </div>
                <div>
                    <label for="publiserings_ar">Publiseringsår:</label>
                    <input type="text" id="publiserings_ar" name="publiserings_ar" required>
                </div>
                <div id="amendment-container">
                    <label>Amendments:</label>
                    <button type="button" onclick="addAmendmentField()">+ Add Amendment</button>
                </div>
                <div id="verdi-container">
                    <label>Velg Verdier:</label>
                    <button type="button" onclick="addVerdiField()">+ Add Verdi</button>
                </div>
                <div>
                    <button type="submit">Opprett Standard</button>
                </div>
            </form>
        </div>
    </main>
            <main class="container">
                <div class="grid">
            <!-- Form for creating new 'sporsmol' -->
            <form action="/nyfoering" method="POST" enctype="multipart/form-data">
                <div>
                    <label for="sporsmol_tittel">Tittel for nytt spørsmål:</label>
                    <input type="text" id="sporsmol_tittel" name="sporsmol_tittel" required>
                </div>
                <div>
                    <label for="sporsmol_beskrivelse">Beskrivelse for nytt spørsmål:</label>
                    <input type="text" id="sporsmol_beskrivelse" name="sporsmol_beskrivelse" required>
                </div>
                <div>
                    <label for="tilleggsinformasjon_pdf">Last opp Tilleggsinformasjon (PDF):</label>
                    <input type="file" id="tilleggsinformasjon_pdf" name="tilleggsinformasjon_pdf" accept=".pdf">
                </div>
                <div id="parent-container">
                    <label>Velg Parent Spørsmål:</label>
                    <button type="button" onclick="addParentField()">+ Legg til Parent</button>
                </div>
                <div id="child-container">
                    <label>Velg Child Spørsmål:</label>
                    <button type="button" onclick="addChildField()">+ Legg til Child</button>
                </div>
                <div id="verdi-container-sporsmol">
                    <label>Velg Verdi:</label>
                    <button type="button" onclick="addVerdiFieldSporsmol()">+ Legg til Verdi</button>
                </div>
                <div id="forskrift-container">
                    <label>Velg Forskrift:</label>
                    <button type="button" onclick="addForskriftField()">+ Legg til Forskrift</button>
                </div>
                <div>
                    <button type="submit">Opprett Spørsmål</button>
                </div>
            </form>
            <form action=""></form>
        </div>
        </main>
        

    <script>
        var parentQuestions = JSON.parse('${parentSporsmolJson}');
        var childQuestions = JSON.parse('${childSporsmolJson}');
        var verdierList = JSON.parse('${verdierJson}');
    </script>
    <script id="forskrift-data" type="application/json">${forskriftJson}</script>
    <script>
        // Pass verdititler fra serveren til JavaScript
        var verdiTitler = "${verdier}".split(",");
    </script>
</body>
</html>
