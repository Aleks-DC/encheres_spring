package fr.eni.projet.encheres.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.eni.projet.encheres.bo.Categorie;
import fr.eni.projet.encheres.dal.ArticleAVendreDAO;

@Component
public class StringToCategorieConverter implements Converter<String, Categorie> {

	@Autowired
    private ArticleAVendreDAO articleAVendreDAO;
    
    // Constructeur par défaut
    public StringToCategorieConverter() {
    }

    @Override
    public Categorie convert(String idString) {
        if (idString == null || idString.isEmpty()) {
            return null;
        }

        try {
            Long id = Long.parseLong(idString);

            return articleAVendreDAO.getCategorieById(id);// Récupérer la catégorie par son ID
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID de catégorie invalide : " + idString);
        }
    }


}

