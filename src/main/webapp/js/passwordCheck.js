
var timeout = null; // Timeout to delay execution

function checkPassword() {
    clearTimeout(timeout); // Clear the timeout if there is one
    timeout = setTimeout(function() { // Set a new timeout
        var password = document.getElementById('password').value;
        var confirmPassword = document.getElementById('confirmPassword').value;
        var strengthText = document.getElementById('passwordStrength');
        var matchText = document.getElementById('passwordMatch');

        // Check for password strength
        if (password.length < 8 || !/\d/.test(password) || !/[a-zA-Z]/.test(password)) {
            strengthText.textContent = 'Weak password: Must be at least 8 characters with a number and a letter.';
            strengthText.style.color = 'red';
        } else {
            strengthText.textContent = 'Strong password';
            strengthText.style.color = 'green';
        }

        // Check if passwords match
        if (password !== confirmPassword) {
            matchText.textContent = 'Passwords do not match';
            matchText.style.color = 'red';
        } else {
            matchText.textContent = 'Passwords match';
            matchText.style.color = 'green';
        }
    }, 800); // Delay in milliseconds, adjust as needed
}