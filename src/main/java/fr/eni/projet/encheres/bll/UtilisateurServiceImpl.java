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
		this.utilisateurDAO = utilisateurDAO;
		this.adresseDAO = adresseDAO;
	}

	@Override
	@Transactional
	public void creerUtilisateur(Utilisateur utilisateur, Adresse adresse) {
		
		// Création de l'adresse
		adresseDAO.create(adresse);
		utilisateur.setAdresse(adresse);
		
		// Création du mot de passe
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
		
		// et on crée le tout
		utilisateurDAO.create(utilisateur);
		
	}

	@Override
	public void modifierUtilisateur(Utilisateur utilisateur) {
		utilisateurDAO.update(utilisateur);
		adresseDAO.update(utilisateur.getAdresse());
	}

	@Override
	public Utilisateur consulterUtilisateur(String pseudo) {
        Utilisateur utilisateur = utilisateurDAO.findByPseudo(pseudo);
        if (utilisateur != null && utilisateur.getAdresse() != null) {
            Adresse adresse = adresseDAO.findById((int) utilisateur.getAdresse().getId());
            utilisateur.setAdresse(adresse);
        }
        return utilisateur;
    }

	@Override
    public List<Utilisateur> consulterUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurDAO.findAll();
        for (Utilisateur utilisateur : utilisateurs) {
            if (utilisateur.getAdresse() != null) {
                Adresse adresse = adresseDAO.findById((int) utilisateur.getAdresse().getId());
                utilisateur.setAdresse(adresse);
            }
        }
        return utilisateurs;
    }


		@Transactional
	    public void modifierMotDePasse(String pseudo, String ancienMotDePasse, String nouveauMotDePasse) {
			
	        Utilisateur utilisateur = utilisateurDAO.findByPseudo(pseudo);
			PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
			utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
	        if (utilisateur != null && passwordEncoder.matches(ancienMotDePasse, utilisateur.getMotDePasse())) {
	            utilisateur.setMotDePasse(passwordEncoder.encode(nouveauMotDePasse));
	            utilisateurDAO.update(utilisateur);
	        } else {
	            throw new IllegalArgumentException("Ancien mot de passe incorrect");
	        }
	}

}
