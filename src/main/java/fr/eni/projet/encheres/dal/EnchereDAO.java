package fr.eni.projet.encheres.dal;

import fr.eni.projet.encheres.bo.Enchere;

public interface EnchereDAO {

	void creer();
	
	Enchere getByIdArticle();
	
	Enchere getByIdUtilisateur();
	
	Enchere getAllEncheres();
	
	void updateEnchere();
	
	void deleteEnchere();
	
	
}
