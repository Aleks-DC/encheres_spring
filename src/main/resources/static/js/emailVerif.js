function validateEmail() {
	var email = document.getElementById("email").value;
	var verifyEmail = document.getElementById("verifyEmail").value;
	
	// Vérification des emails
	if (email !== verifyEmail) {
		alert("Les adresses email ne correspondent pas.");
		return false;
	}
	
	// Vérification du pattern de l'email
	var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
	if (!emailPattern.test(email)) {
		alert("L'adresse email n'est pas valide.");
		return false;
	}

	return true;
}