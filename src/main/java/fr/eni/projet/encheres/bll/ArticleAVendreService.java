package fr.eni.projet.encheres.bll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.eni.projet.encheres.bo.ArticleAVendre;
import fr.eni.projet.encheres.dal.ArticleAVendreDAO;

@Service
public class ArticleAVendreService {

    @Autowired
    private ArticleAVendreDAO articleAVendreDAO;

    public ArticleAVendre creer(ArticleAVendre articleAVendre) { 
        return articleAVendreDAO.creer(articleAVendre); 
    }

    // TODO: Ajouter les autres méthodes pour les actions :
    // - getById(int id)
    // - getAll()
    // - update(ArticleAVendre articleAVendre)
    // - delete(int id)
}
