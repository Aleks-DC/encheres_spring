package fr.eni.projet.encheres.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import fr.eni.projet.encheres.bll.ArticleAVendreService;
import fr.eni.projet.encheres.bo.ArticleAVendre;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/articles")
public class EnchereController { 

    @Autowired
    private ArticleAVendreService articleAVendreService;

    @GetMapping("/nouveau")
    public String afficherFormulaireCreation(Model model) {
        model.addAttribute("articleAVendre", new ArticleAVendre());
        return "creer-article";
    }

    @PostMapping("/nouveau")
    public String creerArticle(@Valid @ModelAttribute("articleAVendre") ArticleAVendre articleAVendre, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "creer-article"; 
        }
        
        articleAVendreService.creer(articleAVendre);
        return "redirect:/articles";
    }

    // Ajoutez les autres méthodes pour les actions :
    // - @GetMapping("/{id}") pour afficher les détails d'un article
    // - @GetMapping("/modifier/{id}") pour afficher le formulaire de modification
    // - @PostMapping("/modifier/{id}") pour traiter la modification
    // - @PostMapping("/supprimer/{id}") pour traiter la suppression
}
	