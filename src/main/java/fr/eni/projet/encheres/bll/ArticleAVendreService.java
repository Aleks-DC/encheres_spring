package fr.eni.projet.encheres.bll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.eni.projet.encheres.bo.ArticleAVendre;
import fr.eni.projet.encheres.dal.ArticleAVendreDAO;

@Service
public class ArticleAVendreService {

    @Autowired
    private ArticleAVendreDAO articleAVendreDAO;

    public void creerEnchere(ArticleAVendre articleAVendre) {
    	articleAVendreDAO.creer(articleAVendre);
    }
}

