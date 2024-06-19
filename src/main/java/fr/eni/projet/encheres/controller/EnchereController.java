package fr.eni.projet.encheres.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.eni.projet.encheres.bll.ArticleAVendreService;
import fr.eni.projet.encheres.bo.ArticleAVendre;
import jakarta.validation.Valid;

@Controller
public class EnchereController {

    @Autowired
    private ArticleAVendreService articleAVendreService;

    @GetMapping("/") 
    public String afficherAccueil(Model model) {
        List<ArticleAVendre> articlesAVendre = articleAVendreService.getAll();
        model.addAttribute("articlesAVendre", articlesAVendre);
        return "index"; 
    }

    @GetMapping("/articles/nouveau")
    public String afficherFormulaireCreation(Model model) {
        model.addAttribute("articleAVendre", new ArticleAVendre());
        return "creer-article";
    }

    @PostMapping("/articles/nouveau")
    public String creerArticle(@Valid @ModelAttribute("articleAVendre") ArticleAVendre articleAVendre, 
                               BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "creer-article"; 
        }

        try {
            articleAVendreService.creer(articleAVendre);
            redirectAttributes.addFlashAttribute("successMessage", "Article créé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la création de l'article : " + e.getMessage());
        }

        return "redirect:/";
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
