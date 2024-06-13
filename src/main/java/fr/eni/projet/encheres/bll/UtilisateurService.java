package fr.eni.projet.encheres.bll;

import org.springframework.stereotype.Service;

import fr.eni.projet.encheres.bo.Utilisateur;

@Service
public interface UtilisateurService {
	
    public Utilisateur findByPseudo(String pseudo);

    public void updateUtilisateur(Utilisateur utilisateur);

	public void saveUtilisateur(Utilisateur utilisateur);

}