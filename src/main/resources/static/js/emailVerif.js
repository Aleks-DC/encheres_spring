function validateEmail() {
	const email = document.getElementById("email").value;
	const verifyEmail = document.getElementById("verifyEmail").value;
	
	// Vérification des emails
	if (email !== verifyEmail) {
		alert("Les adresses email ne correspondent pas.");
		return false;
	}
	
	// Vérification du pattern de l'email
	const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
	if (!emailPattern.test(email)) {
		alert("L'adresse email n'est pas valide.");
		return false;
	}

	return true;
}


document.getElementById("submit").addEventListener('click', function(event){
	event.preventDefault();
	if (validateEmail()) {
		document.getElementById("form").submit();
	}
});
