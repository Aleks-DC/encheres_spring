package fr.eni.projet.encheres.bll;

import java.util.List;

import fr.eni.projet.encheres.bo.Adresse;
import fr.eni.projet.encheres.bo.Utilisateur;

public interface UtilisateurService {
	
	void creerUtilisateur(Utilisateur utilisateur, Adresse adresse);

	void modifierUtilisateur(Utilisateur utilisateur, Adresse adresse);
	
	Utilisateur consulterUtilisateur(String pseudo);

	List<Utilisateur> consulterUtilisateurs();

	//Pour plus tard
	void modifierMotDePasse(String pseudo, String ancienMotDePasse, String nouveauMotDePasse);
}
