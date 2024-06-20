package fr.eni.projet.encheres.bll;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.projet.encheres.bo.ArticleAVendre;
import fr.eni.projet.encheres.bo.Categorie;
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
	public void delete(int noArticle) {
		articleAVendreDAO.delete(noArticle);
	}

	// TODO: supprimer les @Valid et validation date à faire

	@Override
	@Transactional
	public void creer(ArticleAVendre articleAVendre) throws BusinessException {
		articleAVendreDAO.creer(articleAVendre);
	}

	private String buildErrorMessage(Set<ConstraintViolation<ArticleAVendre>> violations) {
		return violations.stream().map(v -> v.getPropertyPath() + ": " + v.getMessage())
				.collect(Collectors.joining("\n"));
	}
}
