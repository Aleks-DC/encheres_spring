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
    @Transactional
    public void modifierUtilisateur(Utilisateur utilisateur) {
        // Vérifie que l'utilisateur et son adresse ne sont pas null
        if (utilisateur == null || utilisateur.getAdresse() == null) {
            throw new IllegalArgumentException("Utilisateur ou adresse ne peut pas être nul");
        }

        try {
            utilisateurDAO.update(utilisateur);
            adresseDAO.update(utilisateur.getAdresse());
        } catch (Exception e) {
            // Log l'exception
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour de l'utilisateur et de l'adresse", e);
        }
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

	@Override
	@Transactional
    public void modifierMotDePasse(String pseudo, String ancienMotDePasse, String nouveauMotDePasse, String confirmationMotDePasse) {
        Utilisateur utilisateur = utilisateurDAO.findByPseudo(pseudo);
        
        // vérification : oldPassword correspond à au mdp en bdd
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	    if (!passwordEncoder.matches(ancienMotDePasse, utilisateur.getMotDePasse())) {
	        throw new IllegalArgumentException("Ancien mot de passe incorrect");
	    }
	    
	    //Vérification : Nouveau Mdp correspond à confirmation
	    if (!nouveauMotDePasse.equals(confirmationMotDePasse)) {
	        throw new IllegalArgumentException("La confirmation du nouveau mot de passe ne correspond pas");
	    }
	    //mise à jour du mot de passe
        utilisateur.setMotDePasse(passwordEncoder.encode(nouveauMotDePasse));
        utilisateurDAO.update(utilisateur);

	}
	
}
