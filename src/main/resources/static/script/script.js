// Disable form submissions if there are invalid fields
(function () {
    'use strict';
    window.addEventListener('load', function () {
        // Get the forms we want to add validation styles to
        var forms = document.getElementsByClassName('needs-validation');
        // Loop over them and prevent submission
        var validation = Array.prototype.filter.call(forms, function (form) {
            form.addEventListener('submit', function (event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    }, false);
})();
// Disable submit button if all fields are empty
document.getElementById('loginBtn').disabled = true;
document.getElementById('name').addEventListener('keyup', e => {
    //Check for the input's value
    if (e.target.value === "") {
        document.getElementById('loginBtn').disabled = true;
    } else {
        document.getElementById('loginBtn').disabled = false;
    }
});
document.getElementById('surname').addEventListener('keyup', e => {
    //Check for the input's value
    if (e.target.value === "") {
        document.getElementById('loginBtn').disabled = true;
    } else {
        document.getElementById('loginBtn').disabled = false;
    }
});
document.getElementById('email').addEventListener('keyup', e => {
    //Check for the input's value
    if (e.target.value === "") {
        document.getElementById('loginBtn').disabled = true;
    } else {
        document.getElementById('loginBtn').disabled = false;
    }
});
document.getElementById('password').addEventListener('keyup', e => {
    //Check for the input's value
    if (e.target.value === "") {
        document.getElementById('loginBtn').disabled = true;
    } else {
        document.getElementById('loginBtn').disabled = false;
    }
});