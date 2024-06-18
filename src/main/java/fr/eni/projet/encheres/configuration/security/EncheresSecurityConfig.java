package fr.eni.projet.encheres.configuration.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class EncheresSecurityConfig {

	// Récupération des utilisateurs de l'application via la base de données
	@Bean
	UserDetailsManager userDetailsManager(DataSource dataSource) {

		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager
				.setUsersByUsernameQuery("SELECT pseudo, mot_de_passe, 1 FROM utilisateurs WHERE pseudo= ? ");
		jdbcUserDetailsManager
				.setAuthoritiesByUsernameQuery("SELECT u.pseudo, r.role FROM utilisateurs u INNER JOIN roles r "
						+ "ON r.is_admin = u.administrateur WHERE u.pseudo= ? ");

		return jdbcUserDetailsManager;
	}

	// Restriction des URLs selon la connexion utilisateur et leurs rôles
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> {
			auth

					// permettre à tout le monde d'accéder à l'URL racine
					.requestMatchers("/").permitAll()
					// permettre à tout le monde d'accéder à l'URL de création de compte
					.requestMatchers("/register").permitAll()
					// Permettre à tous les utilisateurs d'afficher correctement les images et le css
					.requestMatchers("/css/*").permitAll()
					.requestMatchers("/images/*").permitAll()
					// Toutes autres url et méthodes ne sont accessibles que si connecté
					.anyRequest().permitAll();
		});

		// Autorisation d'accès au formulaire de connexion + redirection vers l'accueil
		// une fois connecté
		http.formLogin(form -> {
						form.loginPage("/login").permitAll();
						form.defaultSuccessUrl("/");
		});

		// Logout --> vider la session et le contexte de sécurité
		http.logout(logout -> 
					logout
					.invalidateHttpSession(true)
					.clearAuthentication(true)
					.deleteCookies("JSESSIONID")
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.logoutSuccessUrl("/")
					.permitAll());

		return http.build();
	}

}
