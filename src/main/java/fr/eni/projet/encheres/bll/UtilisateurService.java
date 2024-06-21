package fr.eni.projet.encheres.bll;

import java.util.List;

import fr.eni.projet.encheres.bo.Utilisateur;

public interface UtilisateurService {
	
	void creerUtilisateur(Utilisateur utilisateur);

	void modifierUtilisateur(Utilisateur utilisateur);
	
	Utilisateur consulterUtilisateur(String pseudo);

	List<Utilisateur> consulterUtilisateurs();

	void modifierMotDePasse(String pseudo, String ancienMotDePasse, String nouveauMotDePasse);

	void updatePoint(Utilisateur utilisateur);
}
