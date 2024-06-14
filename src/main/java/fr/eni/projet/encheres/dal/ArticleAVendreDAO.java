package fr.eni.projet.encheres.dal;

import java.util.List;

import fr.eni.projet.encheres.bo.ArticleAVendre;

public interface ArticleAVendreDAO {

    ArticleAVendre creer(ArticleAVendre articleAVendre);

    ArticleAVendre getById(int id);

    List<ArticleAVendre> getAll();

    void update(ArticleAVendre articleAVendre);

    void delete(int id);
}
