package fr.eni.projet.encheres.dal;

import fr.eni.projet.encheres.bo.Utilisateur;


public interface UtilisateurDAO {
	
	public Utilisateur findByPseudo (String pseudo);

	public void updateUtilisateur(Utilisateur utilisateur);
}
