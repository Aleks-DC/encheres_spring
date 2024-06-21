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
import fr.eni.projet.encheres.bo.Enchere;
import fr.eni.projet.encheres.bo.Utilisateur;
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
	
	@Autowired
    private UtilisateurService utilisateurService;

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
	public void delete(int id) {
		articleAVendreDAO.delete(id);
}
	
	@Override
	@Transactional
	public void encherir(long articleId, String pseudoUtilisateur, int montantEnchere) throws BusinessException {
	    try {
			// 1. Récupérer l'article et l'utilisateur
			ArticleAVendre article = articleAVendreDAO.getById(articleId);
			if (article == null) {
			    throw new BusinessException("Article non trouvé.");
			}

			Utilisateur utilisateur = utilisateurService.consulterUtilisateur(pseudoUtilisateur);
			if (utilisateur == null) {
			    throw new BusinessException("Utilisateur non trouvé.");
			}

			// 2. Vérifications métier
			if (article.getDateFinEncheres().isBefore(LocalDate.now())) {
			    throw new BusinessException("Les enchères sont terminées pour cet article.");
			}

			if (utilisateur.getCredit() < montantEnchere) {
			    throw new BusinessException("Vous n'avez pas assez de crédits pour enchérir.");
			}

			if (montantEnchere <= article.getPrixVente()) {
			    throw new BusinessException("Votre enchère doit être supérieure à la meilleure offre actuelle.");
			}
			
			Enchere ancienneEnchere = articleAVendreDAO.getDerniereEnchere(articleId);

			// 3. Logique d'enchère
			article.setPrixVente(montantEnchere);
			utilisateur.setCredit(utilisateur.getCredit() - 1);

			// 4. Enregistrement de l'enchère (dans une transaction)
			articleAVendreDAO.update(article);
			utilisateurService.updatePoint(utilisateur);
			articleAVendreDAO.encherir(articleId, pseudoUtilisateur, montantEnchere);
			
			// Remboursement de l'ancien enchérisseur (si existant)
			if (ancienneEnchere != null) {
			    Utilisateur ancienEncherisseur = utilisateurService.consulterUtilisateur(ancienneEnchere.getIdUtilisateur());
			    if (ancienEncherisseur != null) {
			        ancienEncherisseur.setCredit(ancienEncherisseur.getCredit() + ancienneEnchere.getMontant());
			        utilisateurService.updatePoint(ancienEncherisseur);
			    }
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
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
    
    
    @Override
    public List<ArticleAVendre> getToutesMesVentes(String pseudoVendeur) {
    	return articleAVendreDAO.getToutesMesVentes(pseudoVendeur);
    }
    @Override
    public List<ArticleAVendre> getMesVentesNonDebutees(String pseudoVendeur) {
    	return articleAVendreDAO.getMesVentesNonDebutees(pseudoVendeur);
    }

    @Override
    public List<ArticleAVendre> getMesVentesEnCours(String pseudoVendeur) {
    	return articleAVendreDAO.getMesVentesEnCours(pseudoVendeur);
    }

    @Override
    public List<ArticleAVendre> getMesVentesTerminees(String pseudoVendeur) {
    	return articleAVendreDAO.getMesVentesTerminees(pseudoVendeur);
    }
}
