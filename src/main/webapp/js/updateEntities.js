document.addEventListener("DOMContentLoaded", () => {
    const toggleQuestionsButton = document.getElementById("toggleQuestions");
    const toggleStandardsButton = document.getElementById("toggleStandards");
    const toggleVerdierButton = document.getElementById("toggleVerdier");
    const questionsList = document.getElementById("questionsList");
    const standardsList = document.getElementById("standardsList");
    const verdiList = document.getElementById("verdiList");

    // Toggle display of questions, standards, and values lists
    toggleQuestionsButton.addEventListener("click", () => {
        questionsList.style.display = questionsList.style.display === "none" ? "block" : "none";
    });

    toggleStandardsButton.addEventListener("click", () => {
        standardsList.style.display = standardsList.style.display === "none" ? "block" : "none";
    });

    toggleVerdierButton.addEventListener("click", () => {
        verdiList.style.display = verdiList.style.display === "none" ? "block" : "none";
    });

    // Update selected questions
    document.querySelectorAll(".update-question").forEach(button => {
        button.addEventListener("click", async () => {
            const questionItem = button.closest(".question-item");
            const questionId = button.getAttribute("data-id");
            const updatedTitle = questionItem.querySelector(".update-input").value;
            const childrenIds = Array.from(questionItem.querySelector(".children-select").selectedOptions).map(opt => opt.value);
            const parentIds = Array.from(questionItem.querySelector(".parents-select").selectedOptions).map(opt => opt.value);
            const verdiIds = Array.from(questionItem.querySelector(".values-select").selectedOptions).map(opt => opt.value);

            try {
                const response = await fetch("/updatesporsmol", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({ id: questionId, newTitle: updatedTitle, children: childrenIds, parents: parentIds, verdier: verdiIds }),
                });

                if (response.ok) {
                    alert("Question updated successfully.");
                    window.location.reload();
                } else {
                    alert("Error updating question.");
                }
            } catch (error) {
                console.error("Error:", error);
                alert("Error updating question.");
            }
        });
    })
    

    // Update selected standards
    document.querySelectorAll(".update-standard").forEach(button => {
        button.addEventListener("click", async () => {
            const standardId = button.getAttribute("data-id");
            // Use closest to navigate to the parent container, then find the input within that container
            const inputField = button.closest(".standard-item").querySelector(".update-input-standard");
            const updatedTitle = inputField.value;
    
            try {
                const response = await fetch("/updatestandard", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({ id: standardId, newTitle: updatedTitle }),
                });
    
                if (response.ok) {
                    alert("Standard updated successfully.");
                    window.location.reload(); // Reload to see the updated info
                } else {
                    alert("Error updating standard.");
                }
            } catch (error) {
                console.error("Error:", error);
                alert("Error updating standard.");
            }
        });
    });

    // Update selected values
    document.querySelectorAll(".update-verdi").forEach(button => {
        button.addEventListener("click", async () => {
            const verdiId = button.getAttribute("data-id");
            // Navigate to the parent container first, then find the input within that container
            const inputField = button.parentElement.querySelector(".update-input-verdi");
            const updatedTitle = inputField.value;
    
            try {
                const response = await fetch("/updateverdi", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({ id: verdiId, newTitle: updatedTitle }),
                });
    
                if (response.ok) {
                    alert("Value updated successfully.");
                    window.location.reload(); // Reload to see the updated info
                } else {
                    alert("Error updating value.");
                }
            } catch (error) {
                console.error("Error:", error);
                alert("Error updating value.");
            }
        });
    });
});
