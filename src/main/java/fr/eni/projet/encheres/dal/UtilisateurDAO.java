package fr.eni.projet.encheres.dal;

import java.util.List;

import fr.eni.projet.encheres.bo.Utilisateur;
import fr.eni.projet.encheres.exception.BusinessException;

public interface UtilisateurDAO {
	
	void create(Utilisateur utilisateur); // Create
	
	Utilisateur findByPseudo(String pseudo); // Read
	
	Utilisateur findByEmail(String email); // Read

	List<Utilisateur> findAll(); // Read

	void update(Utilisateur utilisateur); // Update

	void deleteByPseudo(String pseudo); // Delete
	
    void updatePoint(Utilisateur utilisateur); // Update des points

}
