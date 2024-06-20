package fr.eni.projet.encheres.dal;

import java.util.List;

import fr.eni.projet.encheres.bo.ArticleAVendre;
import fr.eni.projet.encheres.bo.Categorie;


public interface ArticleAVendreDAO {

	void creer(ArticleAVendre articleAVendre);

	ArticleAVendre getById(long noArticle); // Changer le nom du paramètre en noArticle

    List<ArticleAVendre> getAll();
    
    List<Categorie> getAllCategories();

    void update(ArticleAVendre articleAVendre);

    void delete(int noArticle); // Changer le nom du paramètre en noArticle

	Categorie getCategorieByLibelle(String libelle);

	Categorie getCategorieById(Long id);
}
