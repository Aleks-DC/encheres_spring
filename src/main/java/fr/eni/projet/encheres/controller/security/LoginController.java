package fr.eni.projet.encheres.controller.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

//Injection de la liste des attributs en session
class LoginController {
	// Injection du service ContexteService

	@GetMapping("/login")
	String login() {
		return "login";
	}

}
