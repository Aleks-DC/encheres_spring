package fr.eni.projet.encheres.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.eni.projet.encheres.bll.ArticleAVendreService;
import fr.eni.projet.encheres.bll.UtilisateurService;
import fr.eni.projet.encheres.bo.Adresse;
import fr.eni.projet.encheres.bo.ArticleAVendre;
import fr.eni.projet.encheres.bo.Utilisateur;
import fr.eni.projet.encheres.exception.BusinessException;
import jakarta.validation.Valid;

@Controller
public class EnchereController {

	@Autowired
	private ArticleAVendreService articleAVendreService;

//    @GetMapping("/") 
//    public String afficherAccueil(Model model) {
//        List<ArticleAVendre> articlesAVendre = articleAVendreService.getAll();
//        model.addAttribute("articlesAVendre", articlesAVendre);
//        return "index"; 
//    }
	
	// TODO @Alexis Controller filtrage des articles
	@GetMapping("/")
	public String afficherAccueil(@RequestParam(name = "motCle", required = false) String motCle,
	                              @RequestParam(name = "categorie", required = false) Long categorieId, Model model) {
	    List<ArticleAVendre> articlesAVendre;
	    // Vérifie si les deux filtres sont utilisés
	    if (categorieId != null && motCle != null) {
	        articlesAVendre = articleAVendreService.findByCategorieAndMotCle(categorieId, motCle);
	    } else if (categorieId != null) {
	        articlesAVendre = articleAVendreService.getByCategorie(categorieId);
	    } else if (motCle != null) {
	        articlesAVendre = articleAVendreService.searchByMotCle(motCle);
	    } else {
	        articlesAVendre = articleAVendreService.getAll();
	    }
	    model.addAttribute("articlesAVendre", articlesAVendre);
	    model.addAttribute("categories", articleAVendreService.getAllCategories());
	    return "index";
	}


    @PostMapping("/articles/nouveau")
    public String creerArticle(@Valid @ModelAttribute("articleAVendre") ArticleAVendre articleAVendre, 
                               BindingResult result, RedirectAttributes redirectAttributes, Model model,
                               Principal principal) throws BusinessException {
        
    	if (articleAVendre.getCategorie().getId() == 0) {
    	    articleAVendre.setCategorie(null); 
    	}

        if (result.hasErrors()) {
            model.addAttribute("categories", articleAVendreService.getAllCategories());
            return "creer-article";
        }

        if (articleAVendre.getCategorie() == null) {
            result.rejectValue("categorie", "NotNull", "Veuillez sélectionner une catégorie.");
            model.addAttribute("categories", articleAVendreService.getAllCategories());
            return "creer-article";
        }
        
        if (principal != null) {
            String pseudoUtilisateur = principal.getName();
            articleAVendre.setNomVendeur(pseudoUtilisateur);

            Utilisateur utilisateurConnecte = new Utilisateur();

            Adresse adresseUtilisateur = utilisateurConnecte.getAdresse();
			if (adresseUtilisateur != null) {
			    articleAVendre.setRetrait(adresseUtilisateur);
			} else {
			    result.rejectValue("retrait", "NotNull", "Veuillez renseigner une adresse de retrait.");
			}
        } else {
            throw new BusinessException("L'utilisateur doit être connecté pour créer un article.");
        }

		if (articleAVendre.getCategorie() == null) {
			result.rejectValue("categorie", "NotNull", "Veuillez sélectionner une catégorie.");
			model.addAttribute("categories", articleAVendreService.getAllCategories());
			return "creer-article";
		}

		try {
			articleAVendreService.creer(articleAVendre);
			redirectAttributes.addFlashAttribute("successMessage", "Article créé avec succès !");
			return "redirect:/";
		} catch (BusinessException e) {
			model.addAttribute("errorMessage", e.getMessage());
			model.addAttribute("categories", articleAVendreService.getAllCategories());
			return "creer-article";
		}
	}

	@GetMapping("/articles/{id}")
	public String afficherDetailArticle(@PathVariable("id") long id, Model model) {
		ArticleAVendre article = articleAVendreService.getById(id);
		if (article == null) {
			return "redirect:/";
		}
		model.addAttribute("article", article);
		return "detailsArticle";
	}
}
