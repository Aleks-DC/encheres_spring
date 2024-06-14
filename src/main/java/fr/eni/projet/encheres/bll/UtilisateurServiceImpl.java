package fr.eni.projet.encheres.bll;

import java.util.List;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.projet.encheres.bo.Adresse;
import fr.eni.projet.encheres.bo.Utilisateur;
import fr.eni.projet.encheres.dal.AdresseDAO;
import fr.eni.projet.encheres.dal.UtilisateurDAO;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

	private UtilisateurDAO utilisateurDAO;
	private AdresseDAO adresseDAO;

	public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO, AdresseDAO adresseDAO) {
		super();
		this.utilisateurDAO = utilisateurDAO;
		this.adresseDAO = adresseDAO;
	}

	@Override
	@Transactional
	public Utilisateur creerUtilisateur(Utilisateur utilisateur, Adresse adresse) {
		
		// Création de l'adresse
		Adresse a = adresseDAO.create(adresse);
		utilisateur.setAdresse(a);
		
		// Création du mot de passe
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
		
		// et on crée le tout
		return utilisateurDAO.create(utilisateur);
		
	}

	@Override
	public Utilisateur modifierUtilisateur(Utilisateur utilisateur) {
		return utilisateurDAO.update(utilisateur);
	}

	@Override
	public Utilisateur consulterUtilisateur(String pseudo) {
		return utilisateurDAO.findByPseudo(pseudo);
	}

	@Override
	public List<Utilisateur> consulterUtilisateurs() {
		return utilisateurDAO.findAll();
		
	}

	// Je garde de côté pour le passwordEncoder.matches ==> future méthode
	// changeMotDePasse() à compléter
	// @Override
	public Utilisateur modifierMotDePasse(String pseudo, String motDePasse) {
//		Utilisateur utilisateur = utilisateurDAO.findByPseudo(pseudo);
//		if (utilisateur != null && passwordEncoder.matches(motDePasse, utilisateur.getMotDePasse())) {
//			return utilisateur;
//		}
		return null;
	}
}
