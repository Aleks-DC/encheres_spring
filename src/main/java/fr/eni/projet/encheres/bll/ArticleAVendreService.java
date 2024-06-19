package fr.eni.projet.encheres.bll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import fr.eni.projet.encheres.bo.ArticleAVendre;
import fr.eni.projet.encheres.dal.ArticleAVendreDAO;
import fr.eni.projet.encheres.exception.BusinessException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;

@Service
@Validated
public class ArticleAVendreService {

    @Autowired
    private ArticleAVendreDAO articleAVendreDAO;
    
    @Autowired
    private Validator validator;

    public ArticleAVendre creer(@Valid ArticleAVendre articleAVendre) throws BusinessException {
        var violations = validator.validate(articleAVendre);
        if (!violations.isEmpty()) {
            throw new BusinessException(violations.toString());
        }

        return articleAVendreDAO.creer(articleAVendre);
    }

    public ArticleAVendre getById(long noArticle) {
        return articleAVendreDAO.getById(noArticle);
    }

    public List<ArticleAVendre> getAll() {
        return articleAVendreDAO.getAll();
    }

    public void update(@Valid ArticleAVendre articleAVendre) throws BusinessException { // Validation avant update
        var violations = validator.validate(articleAVendre);
        if (!violations.isEmpty()) {
            throw new BusinessException(violations.toString());
        }
        articleAVendreDAO.update(articleAVendre);
    }

    public void delete(int noArticle) {
        articleAVendreDAO.delete(noArticle);
    }
}
