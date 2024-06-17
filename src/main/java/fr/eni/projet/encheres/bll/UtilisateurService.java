package fr.eni.projet.encheres.bll;

import java.util.List;

import fr.eni.projet.encheres.bo.Adresse;
import fr.eni.projet.encheres.bo.Utilisateur;

public interface UtilisateurService {
	
	Utilisateur createUtilisateur(Utilisateur utilisateur);

	//Pas besoin, géré par notre super copain Spring Security
	Utilisateur connexion(String pseudo, String motDePasse);

	Utilisateur modifierUtilisateur(Utilisateur utilisateur);

	List<Utilisateur> consulterUtilisateurs();

	Utilisateur consulterUtilisateur(String pseudo);

	void updateAdresse(Adresse adresse);
}
