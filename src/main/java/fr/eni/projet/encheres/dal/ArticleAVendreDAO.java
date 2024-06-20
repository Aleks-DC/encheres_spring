package fr.eni.projet.encheres.dal;

import java.util.List;

import fr.eni.projet.encheres.bo.ArticleAVendre;
import fr.eni.projet.encheres.bo.Categorie;

public interface ArticleAVendreDAO {

	void creer(ArticleAVendre articleAVendre); // CRUD

	ArticleAVendre getById(long noArticle);

	List<ArticleAVendre> getAll();

	List<Categorie> getAllCategories();

	void update(ArticleAVendre articleAVendre);

	void delete(int noArticle);

	// Filtres par catégorie ou mot clé
	
	Categorie getCategorieById(Long id);

	List<ArticleAVendre> getByCategorie(long categorieId);

	List<ArticleAVendre> searchByMotCle(String motCle);

	List<ArticleAVendre> findByCategorieAndMotCle(long categorieId, String motCle);

	// Filtres par état des ventes
	
	List<ArticleAVendre> getToutesMesVentes(String pseudoVendeur);

	List<ArticleAVendre> getMesVentesNonDebutees(String pseudoVendeur);

	List<ArticleAVendre> getMesVentesEnCours(String pseudoVendeur);

	List<ArticleAVendre> getMesVentesTerminees(String pseudoVendeur);
}
