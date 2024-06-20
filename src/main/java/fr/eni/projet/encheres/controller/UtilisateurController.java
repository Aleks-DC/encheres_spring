package fr.eni.projet.encheres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.eni.projet.encheres.bll.UtilisateurService;
import fr.eni.projet.encheres.bo.Adresse;
import fr.eni.projet.encheres.bo.Utilisateur;
import fr.eni.projet.encheres.exception.BusinessException;
import jakarta.validation.Valid;

@Controller
public class UtilisateurController {

	private final UtilisateurService utilisateurService;

	public UtilisateurController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}

	@GetMapping("/login")
	public String login() {
		return "login"; // nom de la page Thymeleaf sans l'extension .html
	}

	@GetMapping("/logout")
	public String logout() {
		return "index"; // nom de la page Thymeleaf sans l'extension .html
	}

	@GetMapping("/register")
	public String register(Model model) {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setAdresse(new Adresse()); // Assurez-vous que l'adresse est initialisée
		model.addAttribute("utilisateur", utilisateur);
		return "register"; // nom de la page Thymeleaf sans l'extension .html
	}

	@PostMapping("/register/new")
	public String soumettreFormulaire(@Valid @ModelAttribute("utilisateur") Utilisateur utilisateur, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "register";
		} else {
			try {
				System.out.println("Création de " + utilisateur);
				utilisateurService.creerUtilisateur(utilisateur);
				return "redirect:/login";
			} catch (BusinessException e) {
				e.getClefsExternalisations().forEach(key ->{
					ObjectError error= new ObjectError("globalError", key);
					bindingResult.addError(error);
				});
				return "register";
			}
		}
	}

	@GetMapping("/cancel")
	public String cancel() {
		return "redirect:/index";
	}
	
}
