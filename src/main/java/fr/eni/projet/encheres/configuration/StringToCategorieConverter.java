package fr.eni.projet.encheres.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.eni.projet.encheres.bo.Categorie;
import fr.eni.projet.encheres.dal.ArticleAVendreDAOImpl;

@Component
public class StringToCategorieConverter implements Converter<String, Categorie> {
	
	@Autowired
    private ArticleAVendreDAOImpl articleAVendreDAO;

    public StringToCategorieConverter() {
    }
    
    @Autowired
    public void setArticleVendreDAO(ArticleAVendreDAOImpl articleAVendreDAO) {
        this.articleAVendreDAO = articleAVendreDAO;
    }

    @Override
    public Categorie convert(String libelle) {
        return articleAVendreDAO.getCategorieByLibelle(libelle);
    }
}
