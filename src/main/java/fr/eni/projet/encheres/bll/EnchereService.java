package fr.eni.projet.encheres.bll;

import java.util.List;

import fr.eni.projet.encheres.bo.Enchere;

public interface EnchereService {

	void createEnchere(long noArticle);

	List<Enchere> getAllEncheres();

	List<Enchere> findByIdAcquereur(String nomAcquereur);

	List<Enchere> findByIdArticle(long noArticle);

	void updateEnchere(long noArticle);

	void deleteEnchere(long noArticle);
	
	
	List<Enchere> getEncheresOuvertes(long noArticle);

	List<Enchere> getMesEncheresEnCours(String pseudoAcquereur);

	List<Enchere> getMesEncheresRemportees(String pseudoAcquereur);

}
