package fr.eni.projet.encheres.bll;

import java.util.List;

import fr.eni.projet.encheres.bo.Utilisateur;
import fr.eni.projet.encheres.exception.BusinessException;

public interface UtilisateurService {
	
	void creerUtilisateur(Utilisateur utilisateur);

	void modifierUtilisateur(Utilisateur utilisateur);
	
	Utilisateur consulterUtilisateur(String pseudo);

	List<Utilisateur> consulterUtilisateurs();

	void modifierMotDePasse(String pseudo, String ancienMotDePasse, String nouveauMotDePasse);

	void updatePoint(Utilisateur utilisateur);
}
