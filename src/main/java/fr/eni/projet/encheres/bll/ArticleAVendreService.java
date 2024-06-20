package fr.eni.projet.encheres.bll;

import java.util.List;

import fr.eni.projet.encheres.bo.ArticleAVendre;
import fr.eni.projet.encheres.bo.Categorie;
import fr.eni.projet.encheres.exception.BusinessException;

public interface ArticleAVendreService {

	ArticleAVendre getById(long noArticle);

	List<ArticleAVendre> getAll();

	void update(ArticleAVendre articleAVendre) throws BusinessException;

    void delete(int id);

	void creer(ArticleAVendre articleAVendre) throws BusinessException;

	List<Categorie> getAllCategories();

	List<ArticleAVendre> getByCategorie(long categorieId);

	List<ArticleAVendre> searchByMotCle(String motCle);

	List<ArticleAVendre> findByCategorieAndMotCle(long categorieId, String motCle);
	
	List<ArticleAVendre> getToutesMesVentes(String pseudoVendeur);

	List<ArticleAVendre> getMesVentesNonDebutees(String pseudoVendeur);

	List<ArticleAVendre> getMesVentesEnCours(String pseudoVendeur);

	List<ArticleAVendre> getMesVentesTerminees(String pseudoVendeur);

	

}
