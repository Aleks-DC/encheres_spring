package fr.eni.projet.encheres.bo;

import java.time.LocalDateTime;
import java.util.Objects;

public class Enchere {
	private LocalDateTime date;
	private int montant;

	private Utilisateur acquereur;
	private ArticleAVendre articleAVendre;
	private String idUtilisateur;

	public Enchere() {
	}

	public Enchere(LocalDateTime date, int montant, Utilisateur acquereur, ArticleAVendre articleAVendre) {
		super();
		this.date = date;
		this.montant = montant;
		this.acquereur = acquereur;
		this.articleAVendre = articleAVendre;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public int getMontant() {
		return montant;
	}

	public void setMontant(int montant) {
		this.montant = montant;
	}

	public Utilisateur getAcquereur() {
		return acquereur;
	}

	public void setAcquereur(Utilisateur acquereur) {
		this.acquereur = acquereur;
	}

	public ArticleAVendre getArticleAVendre() {
		return articleAVendre;
	}

	public void setArticleAVendre(ArticleAVendre articleAVendre) {
		this.articleAVendre = articleAVendre;
	}

	public String getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(String idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Enchere [date=");
		builder.append(date);
		builder.append(", montant=");
		builder.append(montant);
		builder.append(", acquereur=");
		builder.append(acquereur);
		builder.append(", articleAVendre=");
		builder.append(articleAVendre);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(acquereur, articleAVendre, date, montant);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Enchere other = (Enchere) obj;
		return Objects.equals(acquereur, other.acquereur) && Objects.equals(articleAVendre, other.articleAVendre)
				&& Objects.equals(date, other.date) && montant == other.montant;
	}

}
