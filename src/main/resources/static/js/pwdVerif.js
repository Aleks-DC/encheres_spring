function validatePassword() {
	
	var motDePasse = document.getElementById("motDePasse").value;
	var verifyMotDePasse = document.getElementById("verifyMotDePasse").value;


	// Vérification des mots de passe
	if (motDePasse !== verifyMotDePasse) {
		alert("Les mots de passe ne correspondent pas.");
		return false;
	}

	return true;
}

document.getElementById("submit").addEventListener('click', function(event){
	event.preventDefault();
	validateEmail();
	this.form.submit();
});