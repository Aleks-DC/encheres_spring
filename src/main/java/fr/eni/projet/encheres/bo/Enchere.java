package fr.eni.projet.encheres.bo;

import java.time.LocalDateTime;

public class Enchere {
	private LocalDateTime date;
    private int montant;
    
    private Utilisateur acquereur;
    private ArticleAVendre articleAVendre;
    
	public Enchere() {
	}

	public Enchere(LocalDateTime date, int montant, Utilisateur acquereur, ArticleAVendre articleAVendre) {
		super();
		this.date = date;
		this.montant = montant;
		this.acquereur = acquereur;
		this.articleAVendre = articleAVendre;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Enchere [date=");
		builder.append(date);
		builder.append(", montant=");
		builder.append(montant);
		builder.append(", ArticleAVendre=");
		builder.append(articleAVendre);
		builder.append("]");
		return builder.toString();
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
    
	
    
}
