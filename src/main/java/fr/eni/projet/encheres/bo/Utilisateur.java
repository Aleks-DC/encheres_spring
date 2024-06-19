package fr.eni.projet.encheres.bo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Utilisateur {
	
	@NotBlank(message = "Le pseudo ne peut pas être vide.")
    @Size(min = 3, max = 20, message = "Le pseudo doit contenir entre {min} et {max} caractères.")
	private String pseudo;
	
	@NotBlank(message = "Le nom ne peut pas être vide.")
	@Size(max = 50, message = "Le nom ne peut pas dépasser {max} caractères.")
    private String nom;
    
	@NotBlank(message = "Le prénom ne peut pas être vide.")
	@Size(max = 50, message = "Le prénom ne peut pas dépasser {max} caractères.")
    private String prenom;
    
    @NotBlank(message = "L'email ne peut pas être vide.")
    @Email(message = "Veuillez saisir une adresse email valide.")
    private String email;
    
 // @Pattern(regexp = "^(0|\\+33|0033)[1-9][0-9]{8}$", message = "Le numéro de téléphone doit être au format valide.")
    private String telephone;
	
    //Valider formulaire modif avant d'attaquer mdp
    private String motDePasse;
    private int credit;
    private boolean admin;
    private Adresse adresse;

	public Utilisateur() {
	}

	public Utilisateur(@NotNull @Size(min = 3, max = 20) String pseudo, @NotNull @Size(max = 50) String nom,
			@NotNull @Size(max = 50) String prenom, @Email String email,
			@Pattern(regexp = "^(0|\\+33|0033)[1-9][0-9]{8}$") String telephone, String motDePasse, int credit,
			boolean admin) {
		super();
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.admin = admin;
	}

	public Utilisateur(@NotNull @Size(min = 3, max = 20) String pseudo, @NotNull @Size(max = 50) String nom,
			@NotNull @Size(max = 50) String prenom, @Email String email,
			@Pattern(regexp = "^(0|\\+33|0033)[1-9][0-9]{8}$") String telephone, String motDePasse, int credit,
			boolean admin, Adresse adresse) {
		super();
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.admin = admin;
		this.adresse = adresse;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public Utilisateur orElseThrow(Object object) {
		// TODO ???
		return null;
	}

	public Utilisateur orElse(Object object) {
		// TODO ???
		return null;
	}

}
