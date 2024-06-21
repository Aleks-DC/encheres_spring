package fr.eni.projet.encheres.bll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.projet.encheres.bo.Adresse;
import fr.eni.projet.encheres.bo.Utilisateur;
import fr.eni.projet.encheres.dal.AdresseDAO;
import fr.eni.projet.encheres.dal.UtilisateurDAO;
import fr.eni.projet.encheres.exception.BusinessException;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
	
	
	private UtilisateurDAO utilisateurDAO;
	private AdresseDAO adresseDAO;
	
	@Autowired
	public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO, AdresseDAO adresseDAO) {
		this.utilisateurDAO = utilisateurDAO;
		this.adresseDAO = adresseDAO;
	}

	@Override
	@Transactional
	public void creerUtilisateur(Utilisateur utilisateur) {
		/*BusinessException be = new BusinessException();
		boolean isValid = true;
		
		validerUtilisateur(utilisateur, be);
		isValid &= validerUtilisateurUnique(utilisateur.getPseudo(), be);
		isValid &= validerEmailUnique(utilisateur.getEmail(), be);
		isValid &= validerAdresseUnique(utilisateur.getAdresse(), be);
		
		if (isValid) {*/
			// Création de l'adresse
			Adresse adresse = utilisateur.getAdresse();
			adresseDAO.create(adresse);
	
			// Création du mot de passe
			PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
			utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
	
			// Initialiser les crédits à 10
			utilisateur.setCredit(10);
	
			// et on crée le tout
			utilisateurDAO.create(utilisateur);
		/*} else {
			throw be;
		}*/

	}

	@Override
	@Transactional
	public void modifierUtilisateur(Utilisateur utilisateur) {
		/*BusinessException be = new BusinessException();
		boolean isValid = true;
		validerUtilisateur(utilisateur, be);
		isValid &= validerEmailUnique(utilisateur.getEmail(), be);
		/*isValid &= validerAdresseUnique(utilisateur.getAdresse(), be);
		if (isValid) {*/
			Adresse adresse = utilisateur.getAdresse();
			if (adresse.getId() != 0) {
				adresseDAO.update(adresse);
			} else {
				adresseDAO.create(adresse);
			}
			utilisateurDAO.update(utilisateur);
		/*}else {
			throw be;
		}*/
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
	

	@Override
	public void updatePoint(Utilisateur utilisateur) {
		// TODO Auto-generated method stub
		
	}
}	
	//Les méthodes de validation------------------------------------------------------------------------------------------------------
		/*private boolean validerUtilisateur(Utilisateur u, BusinessException be) { 
			if (u == null) { 
				be.add(BusinessCode.VALIDATION_UTILISATEUR_NULL);
				return false;
			}
			System.out.println("utilisateur existe");
			return true;
		}
		
		public boolean validerUtilisateurUnique(String pseudo, BusinessException be) {
			try {
				Utilisateur utilisateur = utilisateurDAO.findByPseudo(pseudo);
				if (utilisateur != null) {
					be.add(BusinessCode.VALIDATION_UTILISATEUR_UNIQUE_PSEUDO);
					return false;
				}
			} catch (DataAccessException e) {
				be.add(BusinessCode.VALIDATION_UTILISATEUR_UNIQUE_PSEUDO);
				return false;
			}
			return true;
		}
		
		private boolean validerEmailUnique(String email, BusinessException be) {
			try {
				Utilisateur utilisateur = utilisateurDAO.findByEmail(email);
				if (utilisateur != null) {
					be.add(BusinessCode.VALIDATION_UTILISATEUR_UNIQUE_EMAIL);
					return false;
				}
			} catch (DataAccessException e) {
				be.add(BusinessCode.VALIDATION_UTILISATEUR_UNIQUE_EMAIL);
				throw new BusinessException ("Erreur lors de la validation de l'email unique", e);
			}
			return true;
		}*/
		
		/*private boolean validerAdresseUnique(Adresse adresse, BusinessException be) {
			try {
	            // Récupérer toutes les adresses existantes
	            List<Adresse> adressesExistantes = adresseDAO.findAll();

<<<<<<< HEAD
	            for (Adresse existingAdresse : adressesExistantes) {
	                if (sontIdentiques(adresse, existingAdresse)) {
	                    be.add(BusinessCode.VALIDATION_UTILISATEUR_UNIQUE_ADRESSE);
	                    return false;
	                }
	            }
	        } catch (DataAccessException e) {
	            be.add(BusinessCode.VALIDATION_UTILISATEUR_UNIQUE_ADRESSE);
	            return false;
	        }
	        return true;
	    }

	    // Méthode utilitaire pour vérifier si deux adresses sont identiques
	    private boolean sontIdentiques(Adresse adresse1, Adresse adresse2) {
	        return adresse1.getRue().equals(adresse2.getRue()) &&
	               adresse1.getCodePostal().equals(adresse2.getCodePostal()) &&
	               adresse1.getVille().equals(adresse2.getVille());
	    }
*/
		

