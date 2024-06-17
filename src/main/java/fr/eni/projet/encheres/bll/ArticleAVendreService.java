package fr.eni.projet.encheres.bll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import fr.eni.projet.encheres.bo.ArticleAVendre;
import fr.eni.projet.encheres.dal.ArticleAVendreDAO;
import fr.eni.projet.encheres.exception.BusinessException;
import jakarta.validation.Valid;

@Service
@Validated
public class ArticleAVendreService {

    @Autowired
    private ArticleAVendreDAO articleAVendreDAO;

    public ArticleAVendre creer(@Valid ArticleAVendre articleAVendre) throws BusinessException {
        validerPrixVente(articleAVendre); // Validation spécifique au prix de vente
        return articleAVendreDAO.creer(articleAVendre); 
    }

    public ArticleAVendre getById(int noArticle) {
        return articleAVendreDAO.getById(noArticle);
    }

    public List<ArticleAVendre> getAll() {
        return articleAVendreDAO.getAll();
    }

    public void update(ArticleAVendre articleAVendre) {
        articleAVendreDAO.update(articleAVendre);
    }

    public void delete(int noArticle) {
        articleAVendreDAO.delete(noArticle);
    }
    
    private void validerPrixVente(ArticleAVendre article) throws BusinessException {
        if (article.getPrixVente() == null || article.getPrixVente() <= 0) {
            throw new BusinessException("Le prix de vente doit être supérieur à zéro.");
        }
    }
}
