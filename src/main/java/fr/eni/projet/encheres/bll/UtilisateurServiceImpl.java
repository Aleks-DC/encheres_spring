package fr.eni.projet.encheres.bll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.projet.encheres.bo.Adresse;
import fr.eni.projet.encheres.bo.Utilisateur;
import fr.eni.projet.encheres.dal.AdresseDAO;
import fr.eni.projet.encheres.dal.UtilisateurDAO;
import fr.eni.projet.encheres.exception.BusinessCode;
import fr.eni.projet.encheres.exception.BusinessException;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

	@Autowired
	private UtilisateurDAO utilisateurDAO;
	private AdresseDAO adresseDAO;

	
	public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO, AdresseDAO adresseDAO) {
		this.utilisateurDAO = utilisateurDAO;
		this.adresseDAO = adresseDAO;
	}

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
	@Transactional
	public Utilisateur modifierUtilisateur(Utilisateur utilisateur) {
		// Validation des données de la couche présentation
				BusinessException be = new BusinessException();
				 try {
			            Utilisateur updatedUser = utilisateurDAO.update(utilisateur);
			            return updatedUser;
			        } catch (DataAccessException e) {
			            be.add(BusinessCode.BLL_UTILISATEUR_UPDATE_ERREUR);
			            throw be;
			        }
	}

    public void updateAdresse(Adresse adresse) {
        adresseDAO.update(adresse);
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
