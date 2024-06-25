// password-validation.js
document.addEventListener('DOMContentLoaded', function () {
    // Add a submit event listener to all forms
    const forms = document.querySelectorAll('form');

    forms.forEach(function (form) {
        form.addEventListener('submit', function (event) {
            const passwordInput = form.querySelector('input[type="password"]');
            if (passwordInput) {
                const password = passwordInput.value;
                const passwordError = validatePassword(password);

                // Find or create the error message element
                let errorMessageElement = form.querySelector('.password-error-message');
                if (!errorMessageElement) {
                    errorMessageElement = document.createElement('div');
                    errorMessageElement.className = 'password-error-message';
                    errorMessageElement.style.color = 'red';
                    passwordInput.parentNode.appendChild(errorMessageElement);
                }

                if (passwordError) {
                    // Show error message and prevent form submission
                    errorMessageElement.textContent = passwordError;
                    event.preventDefault();
                } else {
                    // Clear error message if validation passes
                    errorMessageElement.textContent = '';
                }
            }
        });
    });

    function validatePassword(password) {
        // Define the password criteria
        const minLength = 8;
        const maxLength = 20;
        const hasLetter = /[a-zA-Z]/.test(password);
        const hasNumber = /\d/.test(password);
        const hasSpecialChar = /[!@#$%^&*()]/.test(password);

        if (password.length < minLength || password.length > maxLength) {
            return 'Password must be 8-20 characters long and contain at least one letter, one number, and one special character.';
        }

        if (!hasLetter || !hasNumber || !hasSpecialChar) {
            return 'Password must be 8-20 characters long and contain at least one letter, one number, and one special character.';
        }

        return '';
    }
});
