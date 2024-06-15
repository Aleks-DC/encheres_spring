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

import fr.eni.projet.encheres.bll.UtilisateurService;
import fr.eni.projet.encheres.bo.Utilisateur;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/monProfil")
public class ProfilMyController {
	
	private UtilisateurService utilisateurService;
	
	public ProfilMyController(UtilisateurService utilisateurService) {
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
	            return "profil-my"; 
	        } else {
	            // Gérer le cas où aucun utilisateur n'est trouvé avec le pseudo donné
	            return "redirect:/"; 
	        }
	    }
	
	 @GetMapping("/modifier")
	    public String afficherFormulaireModification(Model model) {
	        // Récupérer l'utilisateur actuellement authentifié
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String pseudo = authentication.getName();
	        
	        // Rechercher l'utilisateur par son pseudo 
	        Utilisateur utilisateur = utilisateurService.consulterUtilisateur(pseudo);
	        if (utilisateur != null) {
	            model.addAttribute("utilisateur", utilisateur);
	            return "profil-update";
	        } else {
	        	
	        	//TODO récupérer l'adresse => créer Le Service et la DAO Adresse
	        	
	            return "redirect:/";
	        }
	    }
	 
	@PostMapping("/modifier")
	public String updateUtilisateur (@Valid @ModelAttribute("utilisateur") Utilisateur utilisateur, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "profil-update";
		}else {
			utilisateurService.modifierUtilisateur(utilisateur);
			System.out.println("L'utilisateur récupéré depuis le formulaire : "+ utilisateur);
			 return "redirect:/monProfile";
		}
	}
}