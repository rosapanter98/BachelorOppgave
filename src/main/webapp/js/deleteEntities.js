document.addEventListener("DOMContentLoaded", () => {


    document.getElementById('toggleQuestions').addEventListener('click', function() {
        var element = document.getElementById('questionsList');
        element.style.display = (element.style.display === 'none') ? 'block' : 'none';
    });
    
    document.getElementById('toggleStandards').addEventListener('click', function() {
        var element = document.getElementById('standardsList');
        element.style.display = (element.style.display === 'none') ? 'block' : 'none';
    });
    
    document.getElementById('toggleVerdier').addEventListener('click', function() {
        var element = document.getElementById('verdiList');
        element.style.display = (element.style.display === 'none') ? 'block' : 'none';
    });
    
    document.getElementById('toggleForskrifter').addEventListener('click', function() {
        var element = document.getElementById('forskriftList');
        element.style.display = (element.style.display === 'none') ? 'block' : 'none';
    });

    const toggleQuestionsButton = document.getElementById("toggleQuestions");
    const toggleStandardsButton = document.getElementById("toggleStandards");
    const toggleVerdierButton = document.getElementById("toggleVerdier");
    const toggleForskrifterButton = document.getElementById("toggleForskrifter");
    const questionsList = document.getElementById("questionsList");
    const standardsList = document.getElementById("standardsList");
    const verdiList = document.getElementById("verdiList");
    const forskriftList = document.getElementById("forskriftList");
    const deleteQuestionsButton = document.getElementById("deleteQuestions");
    const deleteStandardsButton = document.getElementById("deleteStandards");
    const deleteVerdierButton = document.getElementById("deleteVerdier");
    const deleteForskrifterButton = document.getElementById("deleteForskrifter");
    // Function to handle deletion requests
    async function deleteEntities(endpoint, checkboxesClass) {
        const selectedItems = Array.from(document.querySelectorAll(checkboxesClass + ':checked'))
            .map(checkbox => checkbox.value);
        
        if (selectedItems.length > 0) {
            try {
                const response = await fetch(endpoint, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({ ids: selectedItems }),
                });

                if (response.ok) {
                    alert("Selected items deleted successfully.");
                    window.location.reload();
                } else {
                    alert("Error deleting items.");
                }
            } catch (error) {
                console.error("Error:", error);
                alert("Error deleting items.");
            }
        }
    }

    // Delete event listeners for each entity type
    deleteQuestionsButton.addEventListener("click", () => deleteEntities("/slettsporsmol", ".delete-checkbox"));
    deleteStandardsButton.addEventListener("click", () => deleteEntities("/slettstandard", ".delete-checkbox-standard"));
    deleteVerdierButton.addEventListener("click", () => deleteEntities("/slettverdi", ".delete-checkbox-verdi"));
    deleteForskrifterButton.addEventListener("click", () => deleteEntities("/slettforskrift", ".delete-checkbox-forskrift"));
});
