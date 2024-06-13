package fr.eni.projet.encheres.bll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.eni.projet.encheres.bo.Utilisateur;
import fr.eni.projet.encheres.dal.UtilisateurDAO;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

	@Autowired
	private UtilisateurDAO utilisateurDAO;

	@Override
	public Utilisateur createUtilisateur(Utilisateur utilisateur) {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
		return utilisateurDAO.create(utilisateur);
	}
	
	// Pas besoin, mais je garde de côté pour le passwordEncoder.matches ==> future méthode changeMotDePasse()
	@Override
	public Utilisateur connexion(String pseudo, String motDePasse) {
//		Utilisateur utilisateur = utilisateurDAO.findByPseudo(pseudo);
//		if (utilisateur != null && passwordEncoder.matches(motDePasse, utilisateur.getMotDePasse())) {
//			return utilisateur;
//		}
		return null;
	}

	@Override
	public Utilisateur updateUtilisateur(Utilisateur utilisateur) {
		return utilisateurDAO.update(utilisateur);
	}

	@Override
	public List<Utilisateur> consulterUtilisateurs() {
		return utilisateurDAO.findAll();
	}

	@Override
	public Utilisateur consulterUtilisateur(String pseudo) {
		return utilisateurDAO.findByPseudo(pseudo);
	}
}
