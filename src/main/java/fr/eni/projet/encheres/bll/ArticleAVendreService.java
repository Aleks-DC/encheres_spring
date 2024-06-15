package fr.eni.projet.encheres.bll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.eni.projet.encheres.bo.ArticleAVendre;
import fr.eni.projet.encheres.dal.ArticleAVendreDAO;

@Service
public class ArticleAVendreService {

    private ArticleAVendreDAO articleAVendreDAO;

    public ArticleAVendre creer(ArticleAVendre articleAVendre, ArticleAVendreDAO articleAVendreDAO) { 
        return articleAVendreDAO.creer(articleAVendre); 
    }

    public ArticleAVendre getById(int noArticle, ArticleAVendreDAO articleAVendreDAO) {
        return articleAVendreDAO.getById(noArticle);
    }

    public List<ArticleAVendre> getAll(ArticleAVendreDAO articleAVendreDAO) {
        return articleAVendreDAO.getAll();
    }

    public void update(ArticleAVendre articleAVendre) {
        articleAVendreDAO.update(articleAVendre);
    }

    public void delete(int noArticle, ArticleAVendreDAO articleAVendreDAO) {
        articleAVendreDAO.delete(noArticle);
    }
}
