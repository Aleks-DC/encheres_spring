package fr.eni.projet.encheres.dal;

import java.util.List;

import fr.eni.projet.encheres.bo.Utilisateur;

public interface UtilisateurDAO {
	
	void create(Utilisateur utilisateur); // Create
	
	Utilisateur findByPseudo(String pseudo); // Read

	List<Utilisateur> findAll(); // Read

	void update(Utilisateur utilisateur); // Update

	void deleteByPseudo(String pseudo); // Delete
}
