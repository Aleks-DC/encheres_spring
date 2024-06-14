package fr.eni.projet.encheres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index"; // nom de la page Thymeleaf sans l'extension .html
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
    public String register() {
        return "register"; // nom de la page Thymeleaf sans l'extension .html
    }
}
