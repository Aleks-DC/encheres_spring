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
import fr.eni.projet.encheres.bo.PasswordChangeForm;
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
	            return "redirect:/";
	        }
	    }
	 
	@PostMapping("/modifier")
	public String updateUtilisateur (@Valid @ModelAttribute("utilisateur") Utilisateur utilisateur, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "profil-update";
		}else {
			System.out.println("L'utilisateur récupéré depuis le formulaire : "+ utilisateur);
			utilisateurService.modifierUtilisateur(utilisateur);
			return "redirect:/monProfil";
		}
	}
	
	@GetMapping("/changeMdp")
    public String afficherFormulaireMotDePasse(Model model) {
        model.addAttribute("passwordChangeForm", new PasswordChangeForm());
        return "change-password";
    }

    @PostMapping("/changeMdp")
    public String changerMotDePasse(@Valid @ModelAttribute("passwordChangeForm") PasswordChangeForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "profil-update-pwd";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String pseudo = authentication.getName();
        try {
            utilisateurService.modifierMotDePasse(pseudo, form.getOldPassword(), form.getNewPassword());
            return "redirect:/monProfil";
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("oldPassword", "error.oldPassword", e.getMessage());
            return "profil-update-pwd";
        }
    }
	
}
