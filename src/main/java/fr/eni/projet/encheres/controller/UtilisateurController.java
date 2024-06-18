package fr.eni.projet.encheres.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.eni.projet.encheres.bll.UtilisateurService;
import fr.eni.projet.encheres.bo.Adresse;
import fr.eni.projet.encheres.bo.Utilisateur;

@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {

	
	private UtilisateurService utilisateurService;

	@PostMapping("/inscription")
	public void inscription(@RequestBody Utilisateur utilisateur, Adresse adresse) {
		utilisateurService.creerUtilisateur(utilisateur, adresse);
	}

//	@PostMapping("/connexion")
//	public Utilisateur connexion(@RequestParam String pseudo, @RequestParam String motDePasse) {
//		return utilisateurService.(pseudo, motDePasse);
//	}

	@PutMapping("/{pseudo}")
	public void modifierUtilisateur(@PathVariable String pseudo, @RequestBody Utilisateur utilisateur) {
		utilisateur.setPseudo(pseudo);
		utilisateurService.modifierUtilisateur(utilisateur);
	}

	@GetMapping
	public List<Utilisateur> consulterUtilisateurs() {
		return utilisateurService.consulterUtilisateurs();
	}

	@GetMapping("/{pseudo}")
	public Utilisateur consulterUtilisateur(@PathVariable String pseudo) {
		return utilisateurService.consulterUtilisateur(pseudo);
	}
}
