package fr.eni.projet.encheres.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.eni.projet.encheres.bll.UtilisateurService;
import fr.eni.projet.encheres.bo.Adresse;
import fr.eni.projet.encheres.bo.Utilisateur;
import jakarta.validation.Valid;



public class RegisterController {

    private UtilisateurService utilisateurService;

    public RegisterController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "register";
    }

    @PostMapping
    public String registerUser(@Valid @ModelAttribute("utilisateur") Utilisateur utilisateur, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        // Traiter la création de l'utilisateur et de l'adresse
        Adresse adresse = utilisateur.getAdresse();
        utilisateurService.creerUtilisateur(utilisateur, adresse);
        return "redirect:/login";
    }
}
