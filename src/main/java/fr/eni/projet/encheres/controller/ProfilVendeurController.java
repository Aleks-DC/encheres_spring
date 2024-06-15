package fr.eni.projet.encheres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.projet.encheres.bll.UtilisateurService;
import fr.eni.projet.encheres.bo.Utilisateur;

@Controller
@RequestMapping("/profilVendeur")
public class ProfilVendeurController {
	
	private UtilisateurService utilisateurService;
	
	public ProfilVendeurController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}


	   @GetMapping
	    public String afficherProfilVendeur(@RequestParam(name = "pseudo") String pseudo, Model model) {
	        Utilisateur utilisateur = utilisateurService.consulterUtilisateur(pseudo);
	        if (utilisateur != null) {
	            model.addAttribute("utilisateur", utilisateur);
	            return "profil-vendeur"; // Vue à afficher
	        } else {
	            // Gérer le cas où aucun utilisateur n'est trouvé avec le pseudo donné
	            return "redirect:/"; // Rediriger vers la page d'accueil
	        }
	    }
}