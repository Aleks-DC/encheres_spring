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
import fr.eni.projet.encheres.bo.Adresse;
import fr.eni.projet.encheres.bo.ArticleAVendre;
import fr.eni.projet.encheres.bo.Categorie;
import fr.eni.projet.encheres.bo.Utilisateur;
import fr.eni.projet.encheres.exception.BusinessException;
import jakarta.validation.Valid;

@Controller
public class EnchereController {

	@Autowired
	private ArticleAVendreService articleAVendreService;
	
	// TODO @Alexis Controller filtrage des articles
	
    @GetMapping("/")
    public String afficherAccueil(@RequestParam(name = "motCle", required = false) String motCle,
                                  @RequestParam(name = "categorie", required = false) Long categorieId,
                                  @RequestParam(name = "affichage", required = false) String affichage,
                                  @RequestParam(name = "typeFiltre", required = false) String typeFiltre,
                                  Model model, Principal principal) {
        List<ArticleAVendre> articlesAVendre = null;

        // Vérifie si l'utilisateur souhaite filtrer ses ventes
        if ("ventes".equals(affichage) && principal != null) {
            // Filtrage des ventes selon le type de filtre sélectionné
            if ("enCours".equals(typeFiltre)) {
                articlesAVendre = articleAVendreService.getMesVentesEnCours(principal.getName());
            } else if ("nonDebutees".equals(typeFiltre)) {
                articlesAVendre = articleAVendreService.getMesVentesNonDebutees(principal.getName());
            } else if ("terminees".equals(typeFiltre)) {
                articlesAVendre = articleAVendreService.getMesVentesTerminees(principal.getName());
            } else {
                articlesAVendre = articleAVendreService.getToutesMesVentes(principal.getName());
            }
        } else {
            // Filtrage des achats selon le type de filtre sélectionné
            if ("ouvertes".equals(typeFiltre)) {
                articlesAVendre = articleAVendreService.getEncheresOuvertes();
            } else if ("remportees".equals(typeFiltre) && principal != null) {
                articlesAVendre = articleAVendreService.getMesEncheresRemportees(principal.getName());
            } else if ("enCours".equals(typeFiltre) && principal != null) {
                articlesAVendre = articleAVendreService.getMesEncheresEnCours(principal.getName());
            } else if (categorieId != null && motCle != null) {
                // Filtrage par catégorie et mot-clé
                articlesAVendre = articleAVendreService.findByCategorieAndMotCle(categorieId, motCle);
            } else if (categorieId != null) {
                // Filtrage par catégorie uniquement
                articlesAVendre = articleAVendreService.getByCategorie(categorieId);
            } else if (motCle != null) {
                // Filtrage par mot-clé uniquement
                articlesAVendre = articleAVendreService.searchByMotCle(motCle);
            } else {
                // Aucun filtre appliqué, affiche tous les articles
                articlesAVendre = articleAVendreService.getAll();
            }
        }

        // Ajoute les articles et les catégories au modèle pour les passer à la vue
        model.addAttribute("articlesAVendre", articlesAVendre);
        model.addAttribute("categories", articleAVendreService.getAllCategories());
        
        // Retourne le nom de la vue
        return "index";
    }



	@GetMapping("/articles/nouveau")
	public String afficherFormulaireCreation(Model model) {
		model.addAttribute("categories", articleAVendreService.getAllCategories());
		model.addAttribute("articleAVendre", new ArticleAVendre());
		return "creer-article";
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
	
    @GetMapping("/articles/{id}/supprimer")
    public String supprimerArticle(@PathVariable("id") long id, RedirectAttributes redirectAttributes, Principal principal) throws BusinessException {
        ArticleAVendre article = articleAVendreService.getById(id);
		if (article == null) {
		    redirectAttributes.addFlashAttribute("errorMessage", "Article non trouvé.");
		} else if (!article.getNomVendeur().equals(principal.getName())) {
		    redirectAttributes.addFlashAttribute("errorMessage", "Vous n'êtes pas autorisé à supprimer cet article.");
		} else {
		    articleAVendreService.delete((int) id);
		    redirectAttributes.addFlashAttribute("successMessage", "Article supprimé avec succès.");
		}
        return "redirect:/";
    }

    @GetMapping("/articles/{id}/modifier")
    public String afficherFormulaireModification(@PathVariable("id") long id, Model model, Principal principal) {
        ArticleAVendre article = articleAVendreService.getById(id);
        if (article == null) {
            return "redirect:/";
        }
        if (!article.getNomVendeur().equals(principal.getName())) {
            return "redirect:/";
        }
        if (article.getCategorie() == null) {
            article.setCategorie(new Categorie());
        }

        model.addAttribute("categories", articleAVendreService.getAllCategories());
        model.addAttribute("articleAVendre", article);
        return "modifier-article";
    }

    @PostMapping("/articles/{id}/modifier")
    public String modifierArticle(@PathVariable("id") long id,
                                  @Valid @ModelAttribute("articleAVendre") ArticleAVendre articleAVendre,
                                  BindingResult result, RedirectAttributes redirectAttributes, Model model, Principal principal) {
        if (result.hasErrors()) {
            model.addAttribute("categories", articleAVendreService.getAllCategories());
            return "modifier-article";
        }

        try {
            ArticleAVendre existingArticle = articleAVendreService.getById(id);
            if (existingArticle == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Article non trouvé.");
                return "redirect:/";
            }
            if (!existingArticle.getNomVendeur().equals(principal.getName())) {
                redirectAttributes.addFlashAttribute("errorMessage", "Vous n'êtes pas autorisé à modifier cet article.");
                return "redirect:/";
            }

            existingArticle.setNom(articleAVendre.getNom());
            existingArticle.setDescription(articleAVendre.getDescription());
            existingArticle.setCategorie(articleAVendre.getCategorie());
            existingArticle.setPrixInitial(articleAVendre.getPrixInitial());
            existingArticle.setDateDebutEncheres(articleAVendre.getDateDebutEncheres());
            existingArticle.setDateFinEncheres(articleAVendre.getDateFinEncheres());
            
            articleAVendreService.update(existingArticle);

            existingArticle = articleAVendreService.getById(id);
            model.addAttribute("article", existingArticle);

            redirectAttributes.addFlashAttribute("successMessage", "Article modifié avec succès.");
            return "redirect:/articles/" + id;
        } catch (BusinessException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("categories", articleAVendreService.getAllCategories());
            return "modifier-article";
        }
    }
    
    @PostMapping("/articles/{id}/encherir")
    public String encherir(@PathVariable("id") long id, RedirectAttributes redirectAttributes, Principal principal) {
        try {
            ArticleAVendre article = articleAVendreService.getById(id);
            if (article == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Article non trouvé.");
            } else if (article.getNomVendeur().equals(principal.getName())) {
                redirectAttributes.addFlashAttribute("errorMessage", "Vous ne pouvez pas enchérir sur votre propre article.");
            } else {
                int montantEnchere = article.getPrixVente() + 1;

                articleAVendreService.encherir(id, principal.getName(), montantEnchere);
                redirectAttributes.addFlashAttribute("successMessage", "Enchère enregistrée avec succès.");
            }
        } catch (BusinessException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de l'enchère : " + e.getMessage());
        }
        return "redirect:/articles/" + id;
    }
    
}

