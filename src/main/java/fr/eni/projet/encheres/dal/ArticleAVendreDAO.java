package fr.eni.projet.encheres.dal;

import java.util.List;

import fr.eni.projet.encheres.bo.ArticleAVendre;
import fr.eni.projet.encheres.bo.Categorie;


public interface ArticleAVendreDAO {

	void creer(ArticleAVendre articleAVendre);

	ArticleAVendre getById(long noArticle);

    List<ArticleAVendre> getAll();
    
    List<Categorie> getAllCategories();

    void update(ArticleAVendre articleAVendre);

    void delete(int noArticle);
    
    Categorie getCategorieById(Long id);
	
	List<ArticleAVendre> getByCategorie(long categorieId);
	
	List<ArticleAVendre> searchByMotCle(String motCle);

	List<ArticleAVendre> findByCategorieAndMotCle(long categorieId, String motCle);
	
    void encherir(long articleId, String pseudoUtilisateur, int montantEnchere);
}
