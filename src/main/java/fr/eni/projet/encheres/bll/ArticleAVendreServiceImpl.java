package fr.eni.projet.encheres.bll;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.projet.encheres.bo.ArticleAVendre;
import fr.eni.projet.encheres.bo.Categorie;
import fr.eni.projet.encheres.dal.ArticleAVendreDAO;
import fr.eni.projet.encheres.exception.BusinessException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;

@Service
public class ArticleAVendreServiceImpl implements ArticleAVendreService {

	@Autowired
	private ArticleAVendreDAO articleAVendreDAO;

	@Autowired
	private Validator validator;

	@Override
	public ArticleAVendre getById(long noArticle) {
		return articleAVendreDAO.getById(noArticle);
	}

	@Override
	public List<Categorie> getAllCategories() {
		return articleAVendreDAO.getAllCategories();
	}

	@Override
	public List<ArticleAVendre> getAll() {
		return articleAVendreDAO.getAll();
	}

	@Override
	@Transactional
	public void update(@Valid ArticleAVendre articleAVendre) throws BusinessException {
		var violations = validator.validate(articleAVendre);
		if (!violations.isEmpty()) {
			throw new BusinessException(buildErrorMessage(violations));
		}
		articleAVendreDAO.update(articleAVendre);
	}

	@Override
	@Transactional
	public void delete(int noArticle) {
		articleAVendreDAO.delete(noArticle);
}
	
    @Override
    @Transactional
    public void encherir(long articleId, String pseudoUtilisateur, int montantEnchere) throws BusinessException {
        ArticleAVendre article = articleAVendreDAO.getById(articleId);
        Utilisateur utilisateur = utilisateurService.getUtilisateurByPseudo(pseudoUtilisateur);

        // Vérifications métier
        if (article.getDateFinEncheres().isBefore(LocalDate.now())) {
            throw new BusinessException("Les enchères sont terminées pour cet article.");
        }

        if (utilisateur.getCredit() < montantEnchere) {
            throw new BusinessException("Vous n'avez pas assez de crédits pour enchérir.");
        }

        // Logique d'enchère
        article.setPrixVente(montantEnchere);
        articleAVendreDAO.update(article);

        // Déduire les crédits de l'utilisateur
        utilisateur.setCredit(utilisateur.getCredit() - montantEnchere);
        utilisateurService.updateUtilisateur(utilisateur); // Assurez-vous d'avoir cette méthode dans votre UtilisateurService

        // Enregistrement de l'enchère
        articleAVendreDAO.encherir(articleId, pseudoUtilisateur, montantEnchere);
    }

	@Override
	@Transactional
	public void creer(ArticleAVendre articleAVendre) throws BusinessException {
		articleAVendreDAO.creer(articleAVendre);
	}

    private String buildErrorMessage(Set<ConstraintViolation<ArticleAVendre>> violations) {
        return violations.stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining("\n"));
    }
    
    // TODO @Alexis BLL filtrage des articles
    public List<ArticleAVendre> getByCategorie(long categorieId){
    	return articleAVendreDAO.getByCategorie(categorieId);
    }

    @Override
    public List<ArticleAVendre> searchByMotCle(String motCle) {
        return articleAVendreDAO.searchByMotCle(motCle);
    }

    @Override
    public List<ArticleAVendre> findByCategorieAndMotCle(long categorieId, String motCle) {
        return articleAVendreDAO.findByCategorieAndMotCle(categorieId, motCle);
    }
}
