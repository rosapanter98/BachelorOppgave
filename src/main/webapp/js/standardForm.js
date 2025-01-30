

function addAmendmentField() {
    const container = document.getElementById('amendment-container');
    const input = document.createElement('input');
    input.type = 'text';
    input.name = 'amendments';
    container.appendChild(input);
}

var verdiIndex = 0;

function addVerdiField() {
    const container = document.getElementById('verdi-container');
    const div = document.createElement('div');
    const select = document.createElement('select');
    select.name = 'verdi_titler[]'; // Legg til klammeparenteser for å sende data som en liste

    verdiTitler.forEach(function(tittel) {
        const option = document.createElement('option');
        option.value = tittel.trim();
        option.textContent = tittel.trim();
        select.appendChild(option);
    });

    const numberInput = document.createElement('input');
    numberInput.type = 'number';
    numberInput.name = 'verdi_verdier[]'; // Legg til klammeparenteser for å sende data som en liste
    numberInput.min = 1;
    numberInput.max = 10;

    div.appendChild(select);
    div.appendChild(numberInput);
    container.appendChild(div);

    verdiIndex++;
}