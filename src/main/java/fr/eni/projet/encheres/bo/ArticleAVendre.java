package fr.eni.projet.encheres.bo;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ArticleAVendre {
    @NotNull(message = "L'identifiant de l'article est obligatoire.")
	private long id;
    @NotBlank(message = "Le nom est obligatoire.")
    private String nom;
    @NotBlank(message = "La description est obligatoire.")
    private String description;
    @NotNull(message = "Veuillez saisir une date de début.")
    @FutureOrPresent(message = "La date de début des enchères doit être aujourd'hui ou dans le futur.")
    private LocalDate dateDebutEncheres;
    @NotNull(message = "Veuillez saisir une date de fin.")
    @Future(message = "La date de fin des enchères doit être après la date de début.")
    private LocalDate dateFinEncheres;
    private int statut;
    private int prixInitial;
    private Integer prixVente;
    @NotNull(message = "Une catégorie est requise.")
    private Categorie categorie;
    private Utilisateur vendeur;
    private Adresse retrait;
    private String nomVendeur;
    private String photo;
    
	public ArticleAVendre() {
	}

	public ArticleAVendre(long id, String nom, String description, LocalDate dateDebutEncheres,
			LocalDate dateFinEncheres, int statut, int prixInitial, int prixVente, Categorie categorie, Utilisateur vendeur,
			Adresse retrait) {
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.statut = statut;
		this.prixInitial = prixInitial;
		this.prixVente = prixVente;
		this.categorie = categorie;
		this.vendeur = vendeur;
		this.retrait = retrait;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ArticleAVendre [id=");
		builder.append(id);
		builder.append(", nom=");
		builder.append(nom);
		builder.append(", description=");
		builder.append(description);
		builder.append(", dateDebutEncheres=");
		builder.append(dateDebutEncheres);
		builder.append(", dateFinEncheres=");
		builder.append(dateFinEncheres);
		builder.append(", statut=");
		builder.append(statut);
		builder.append(", prixInitial=");
		builder.append(prixInitial);
		builder.append(", prixVente=");
		builder.append(prixVente);
		builder.append(", categorie=");
		builder.append(categorie);
		builder.append(", vendeur=");
		builder.append(vendeur);
		builder.append(", retrait=");
		builder.append(retrait);
		builder.append("]");
		return builder.toString();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDateDebutEncheres() {
		return dateDebutEncheres;
	}

	public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}

	public LocalDate getDateFinEncheres() {
		return dateFinEncheres;
	}

	public void setDateFinEncheres(LocalDate dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}

	public int getStatut() {
		return statut;
	}

	public void setStatut(int statut) {
		this.statut = statut;
	}

	public int getPrixInitial() {
		return prixInitial;
	}

	public void setPrixInitial(int prixInitial) {
		this.prixInitial = prixInitial;
	}

	public Integer getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(Integer prixVente) {
		this.prixVente = prixVente;
	}

	public Utilisateur getVendeur() {
		return vendeur;
	}

	public void setVendeur(Utilisateur vendeur) {
		this.vendeur = vendeur;
	}

	public Adresse getRetrait() {
		return retrait;
	}

	public void setRetrait(Adresse retrait) {
		this.retrait = retrait;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public Object getNoArticle() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setNoArticle(int intValue) {
		// TODO Auto-generated method stub
		
	}

	public String getPhoto() {
		// TODO Auto-generated method stub
		return photo;
	}

	public String getNomVendeur() {
		return nomVendeur;
	}

	public void setNomVendeur(String nomVendeur) {
		this.nomVendeur = nomVendeur;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
        
}
