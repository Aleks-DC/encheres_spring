package fr.eni.projet.encheres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.projet.encheres.bll.UtilisateurService;
import fr.eni.projet.encheres.bo.Utilisateur;

@Controller
@RequestMapping("/profile")
public class ProfileController {
	
	private UtilisateurService utilisateurService;
	
	@GetMapping
	public String afficherProfil(@RequestParam(name = "pseudo", required = true) String pseudo,Model model) {
		Utilisateur utilisateur = utilisateurService.findByPseudo(pseudo);
		return "view-profile-details";
	}
	
}
