package fr.eni.projet.encheres.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.eni.projet.encheres.bll.UtilisateurService;
import fr.eni.projet.encheres.bo.Utilisateur;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {

	@Autowired
	private UtilisateurService utilisateurService;

	@PostMapping("/inscription")
	public Utilisateur inscription(@RequestBody Utilisateur utilisateur) {
		return utilisateurService.createUtilisateur(utilisateur);
	}

	@PostMapping("/connexion")
	public Utilisateur connexion(@RequestParam String pseudo, @RequestParam String motDePasse) {
		return utilisateurService.connexion(pseudo, motDePasse);
	}

	@PutMapping("/{pseudo}")
	public Utilisateur modifierUtilisateur(@PathVariable String pseudo, @RequestBody Utilisateur utilisateur) {
		utilisateur.setPseudo(pseudo);
		return utilisateurService.updateUtilisateur(utilisateur);
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
