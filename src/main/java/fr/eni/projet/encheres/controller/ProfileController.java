package fr.eni.projet.encheres.controller;

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
@RequestMapping("/profile")
public class ProfileController {
	
	private UtilisateurService utilisateurService;
	
	public ProfileController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}


	@GetMapping
	public String afficherProfil(@RequestParam(name = "pseudo") String pseudo,Model model) {
		Utilisateur utilisateur = utilisateurService.findByPseudo(pseudo);
		if (utilisateur != null) {
			model.addAttribute("utilisateur", utilisateur);
			return "view-profile"; // Vue à afficher
		} else {
			// Gérer le cas où aucun utilisateur n'est trouvé avec le pseudo donné
			return "redirect:/"; // Rediriger vers accueil pour test
		}
	}
	
	@PostMapping("/view-profile-update")
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