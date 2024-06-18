package fr.eni.projet.encheres.bll;

import java.util.List;

import fr.eni.projet.encheres.bo.Adresse;
import fr.eni.projet.encheres.bo.Utilisateur;

public interface UtilisateurService {
	
	void creerUtilisateur(Utilisateur utilisateur, Adresse adresse);

	//Pas besoin, géré par notre super copain Spring Security
	Utilisateur connexion(String pseudo, String motDePasse);

	void modifierUtilisateur(Utilisateur utilisateur);
	
	Utilisateur consulterUtilisateur(String pseudo);

	List<Utilisateur> consulterUtilisateurs();

	//Pour plus tard
	void modifierMotDePasse(String pseudo, String ancienMotDePasse, String nouveauMotDePasse);
}
