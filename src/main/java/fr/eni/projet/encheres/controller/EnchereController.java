package fr.eni.projet.encheres.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.eni.projet.encheres.bll.ArticleAVendreService;
import fr.eni.projet.encheres.bo.ArticleAVendre;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/enchere")
public class EnchereController {

    @Autowired
    private ArticleAVendreService articleAVendreService;

    @GetMapping("/creer")
    public String afficherFormulaireCreation(Model model) {
        model.addAttribute("articleAVendre", new ArticleAVendre());
        return "creer-enchere";
    }

    @PostMapping("/creer")
    public String creerEnchere(@Valid @ModelAttribute("articleVendu") ArticleAVendre articleAVendre, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "creer-enchere";
        }

        articleAVendreService.creerEnchere(articleAVendre);
        return "redirect:/";
    }
}
