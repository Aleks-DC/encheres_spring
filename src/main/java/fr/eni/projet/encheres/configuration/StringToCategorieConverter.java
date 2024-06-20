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
    public Categorie convert(String idString) { // On attend maintenant un ID (String)
        if (idString == null || idString.isEmpty()) {
            return null; // Gérer le cas où aucune catégorie n'est sélectionnée
        }	

        try {
            Long id = Long.parseLong(idString);
            return articleAVendreDAO.getCategorieById(id); // Récupérer la catégorie par son ID
        } catch (NumberFormatException e) {
            // Gérer le cas où la chaîne n'est pas un nombre valide (ne devrait pas arriver)
            return null;
        }
    }
}

