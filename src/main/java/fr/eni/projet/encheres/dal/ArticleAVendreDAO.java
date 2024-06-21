package fr.eni.projet.encheres.dal;

import java.util.List;

import fr.eni.projet.encheres.bo.ArticleAVendre;
import fr.eni.projet.encheres.bo.Categorie;
import fr.eni.projet.encheres.bo.Enchere;

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

	void encherir(long articleId, String pseudoUtilisateur, int montantEnchere);

	Enchere getDerniereEnchere(long articleId);

	List<ArticleAVendre> getEncheresOuvertes();

	List<ArticleAVendre> getMesEncheresRemportees(String pseudoAcquereur);

	List<ArticleAVendre> getMesEncheresEnCours(String pseudoAcquereur);

}
