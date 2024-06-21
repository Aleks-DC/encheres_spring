function validateEmailPassword() {
    const email = document.getElementById("email").value;
    const verifyEmail = document.getElementById("verifyEmail").value;
    const password = document.getElementById("motDePasse").value;
    const verifyPassword = document.getElementById("verifyMotDePasse").value;

    if (email !== verifyEmail || password !== verifyPassword) {
        alert("Les vérifications de l'adresse mail et du mot de passe ne correspondent pas.");
        return false;
    }

    if (!isValidEmail(email)) {
        alert("L'adresse email n'est pas valide.");
        return false;
    }

    return true;
}

function isValidEmail(email) {
    const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    return emailPattern.test(email);
}

document.getElementById("btnsubmit").addEventListener('click', function(event) {
    event.preventDefault();
    if (validateEmailPassword()) {
        const form = document.querySelector("form");
        form.submit();
    }
});