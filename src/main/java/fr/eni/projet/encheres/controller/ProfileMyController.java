package fr.eni.projet.encheres.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.projet.encheres.bll.UtilisateurService;
import fr.eni.projet.encheres.bo.Utilisateur;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/monProfile")
public class ProfileMyController {
	
	private UtilisateurService utilisateurService;
	
	public ProfileMyController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}


	 @GetMapping
	    public String afficherMonProfil(Model model) {
	        // Récupérer l'utilisateur actuellement authentifié
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String pseudo = authentication.getName();

	        // Rechercher l'utilisateur par son pseudo
	        Utilisateur utilisateur = utilisateurService.consulterUtilisateur(pseudo);
	        if (utilisateur != null) {
	            model.addAttribute("utilisateur", utilisateur);
	            return "profile-my"; 
	        } else {
	            // Gérer le cas où aucun utilisateur n'est trouvé avec le pseudo donné
	            return "redirect:/"; 
	        }
	    }
	
	@PostMapping("/profile-update")
	public String updateUtilisateur (@Valid @ModelAttribute("utilisateur") Utilisateur utilisateur, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "view-profile-update";
		}else {
			utilisateurService.updateUtilisateur(utilisateur);
			System.out.println("Le formateur récupéré depuis le formulaire : ");
			model.addAttribute("message", "Profil mis à jour avec succès");
			 return "redirect:/profile?pseudo=" + utilisateur.getPseudo();
		}
	}
}