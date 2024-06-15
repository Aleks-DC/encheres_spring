package fr.eni.projet.encheres.bll;

import java.util.List;

import fr.eni.projet.encheres.bo.Adresse;
import fr.eni.projet.encheres.bo.Utilisateur;

public interface UtilisateurService {
	
	Utilisateur creerUtilisateur(Utilisateur utilisateur, Adresse adresse);

	Utilisateur modifierUtilisateur(Utilisateur utilisateur);
	
	Utilisateur consulterUtilisateur(String pseudo);

	List<Utilisateur> consulterUtilisateurs();

	//Pour plus tard
	Utilisateur modifierMotDePasse(String pseudo, String motDePasse);
}
