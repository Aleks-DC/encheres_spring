package fr.eni.projet.encheres.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.eni.projet.encheres.bll.UtilisateurServiceImpl;
import fr.eni.projet.encheres.bo.Utilisateur;

@RestController
@RequestMapping("/users")
public class UtilisateurController {
	private final UtilisateurServiceImpl utilisateurService;
	
	public UtilisateurController (UtilisateurServiceImpl utilisateurService) {
		this.utilisateurService = utilisateurService;
	}
	

}
