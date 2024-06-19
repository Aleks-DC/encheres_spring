package fr.eni.projet.encheres.dal;

import java.util.List;

import fr.eni.projet.encheres.bo.ArticleAVendre;


public interface ArticleAVendreDAO {

	ArticleAVendre creer(ArticleAVendre articleAVendre);

	ArticleAVendre getById(long noArticle); // Changer le nom du paramètre en noArticle

    List<ArticleAVendre> getAll();

    void update(ArticleAVendre articleAVendre);

    void delete(int noArticle); // Changer le nom du paramètre en noArticle
}
