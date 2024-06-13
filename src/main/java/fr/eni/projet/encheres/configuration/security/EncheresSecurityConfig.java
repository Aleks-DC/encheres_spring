package fr.eni.projet.encheres.configuration.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class EncheresSecurityConfig {

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Récupération des utilisateurs de l'application via la base de données
	@Bean
	UserDetailsManager userDetailsManager(DataSource dataSource) {

		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT pseudo, password, 1 FROM utilisateurs WHERE pseudo= ? ");
		jdbcUserDetailsManager
				.setAuthoritiesByUsernameQuery("SELECT u.pseudo, r.role FROM utilisateurs u INNER JOIN roles r "
						+ "ON r.is_admin = u.administrateur WHERE u.pseudo= ? ");

		return jdbcUserDetailsManager;
	}

	// Restriction des URLs selon la connexion utilisateur et leurs rôles
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests(auth -> {
	            auth

	                // Autoriser l'accès à la racine et à la page de connexion
	                .requestMatchers("/", "/login", "/register").permitAll()

	                // Permettre l'accès aux ressources statiques (CSS, images, etc.)
	                .requestMatchers("/css/**", "/images/**").permitAll()

	                // Enfin, toutes les autres requêtes nécessitent une authentification
	                .anyRequest().permitAll();
		});

		// Autorisation d'accès au formulaire de connexion + redirection vers l'accueil
		// une fois connecté
		http.formLogin(form -> {
			form.loginPage("/login").permitAll();
			form.defaultSuccessUrl("/");
		});

		// Logout --> vider la session et le contexte de sécurité
		http.logout(logout -> logout.invalidateHttpSession(true).clearAuthentication(true).deleteCookies("JSESSIONID")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").permitAll());

		return http.build();
	}

}
