document.addEventListener('DOMContentLoaded', function() {
    console.log('DOM content loaded');
    var forskriftDataElement = document.getElementById('forskrift-data');
    var forskrifterData = forskriftDataElement.textContent;  // Hent tekstinnholdet direkte

    try {
        var forskrifterList = JSON.parse(forskrifterData);
        setForskrifterList(forskrifterList);
    } catch (error) {
        console.error("Failed to parse forskrift data: ", error);
    }
});

function addParentField() {
    const container = document.getElementById('parent-container');
    if (!container) {
        console.error('Parent container not found');
        return;
    }

    const select = document.createElement('select');
    select.name = 'parent_sporsmol_ids[]';
    select.onchange = handleParentChange;

    parentQuestions.forEach(question => {
        const option = document.createElement('option');
        option.value = question.id;
        option.textContent = question.tittel;
        if (!question.tittel) console.error('Missing title for question:', question);
        select.appendChild(option);
    });

    container.appendChild(select);
}

function addChildField() {
    const container = document.getElementById('child-container');
    const select = document.createElement('select');
    select.name = 'child_sporsmol_ids[]';
    select.onchange = handleChildChange;

    childQuestions.forEach(question => {
        const option = document.createElement('option');
        option.value = question.id;
        option.textContent = question.tittel;
        select.appendChild(option);
    });

    container.appendChild(select);
}

function addVerdiFieldSporsmol() {
    const container = document.getElementById('verdi-container-sporsmol');

    const select = document.createElement('select');
    select.name = 'verdi_sporsmol_ids[]';

    verdierList.forEach(verdi => {
        const option = document.createElement('option');
        option.value = verdi.id;
        option.textContent = verdi.tittel;
        select.appendChild(option);
    });

    container.appendChild(select);
}
function addForskriftField() {
    const container = document.getElementById('forskrift-container');
    if (!container) {
        console.error('Forskrift container not found');
        return;
    }

    const select = document.createElement('select');
    select.name = 'forskrift_ids[]';
    forskrifterList.forEach(forskrift => {
        const option = document.createElement('option');
        option.value = forskrift.id;
        option.textContent = forskrift.tittel;
        select.appendChild(option);
    });

    container.appendChild(select);
}
var forskrifterList = [];
function setForskrifterList(forskrifter) {
    forskrifterList = forskrifter;
}

function updateOptions(selectElement, selectedValue, isParentChange) {
    const sourceSelect = isParentChange ? document.querySelector('[name="parent_sporsmol_ids[]"]') : document.querySelector('[name="child_sporsmol_ids[]"]');
    const targetSelect = isParentChange ? document.querySelector('[name="child_sporsmol_ids[]"]') : document.querySelector('[name="parent_sporsmol_ids[]"]');

    const otherSelectedValue = targetSelect.value;

    const allQuestions = isParentChange ? parentQuestions : childQuestions;

    sourceSelect.innerHTML = '';
    allQuestions.forEach(question => {
        if (question.id !== otherSelectedValue) {
            const option = document.createElement('option');
            option.value = question.id;
            option.textContent = question.tittel;
            sourceSelect.appendChild(option);
        }
    });
    sourceSelect.value = selectedValue;

    const relatedQuestions = isParentChange ? childQuestions : parentQuestions;
    targetSelect.innerHTML = '';
    relatedQuestions.forEach(question => {
        if (question.id !== selectedValue) {
            const option = document.createElement('option');
            option.value = question.id;
            option.textContent = question.tittel;
            targetSelect.appendChild(option);
        }
    });

    if (otherSelectedValue) {
        const isValidOption = Array.from(targetSelect.options).some(option => option.value === otherSelectedValue);
        if (isValidOption) {
            targetSelect.value = otherSelectedValue;
        }
    }
}

function handleParentChange() {
    updateOptions(this, this.value, true);
}

function handleChildChange() {
    updateOptions(this, this.value, false);
}

window.addParentField = addParentField;
window.addChildField = addChildField;
window.addVerdiFieldSporsmol = addVerdiFieldSporsmol;
window.addForskriftField = addForskriftField;
window.setForskrifterList = setForskrifterList;
