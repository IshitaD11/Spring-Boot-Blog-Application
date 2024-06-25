// confirm_password_validation.js
document.addEventListener("DOMContentLoaded", function (){
    var form = document.getElementById("reset-password");

    form.addEventListener("submit",function (event){
        checkConfirmPassword(event);
    });

    var confirmPasswordInput = document.getElementById("confirm-password");
    confirmPasswordInput.addEventListener("blur",function () {
        checkConfirmPassword(null);
    });
});


function checkConfirmPassword(event){
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirm-password").value;
    var errorElement = document.getElementById("passwordError");

    if(password!==confirmPassword) {
        errorElement.textContent = "Password do not match";
        if (event) {
            event.preventDefault(); // prevents form submission
        }
    }
    else {
        errorElement.textContent = "" ;
    }
}