package fr.eni.projet.encheres.bll;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fr.eni.projet.encheres.bo.Utilisateur;
import fr.eni.projet.encheres.dal.UtilisateurDAO;
import fr.eni.projet.encheres.exception.UtilisateurNonTrouveException;


@Service
public class UtilisateurServiceImpl implements UtilisateurService {
	   private final UtilisateurDAO utilisateurDAO;
	   
//	   @Autowired
	    private final BCryptPasswordEncoder bCryptPasswordEncoder;

	    public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO, BCryptPasswordEncoder bCryptPasswordEncoder) {
	        this.utilisateurDAO = utilisateurDAO;
	        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	    }
	    
    public Utilisateur findByPseudo(String pseudo) {
    	Utilisateur utilisateur = utilisateurDAO.findByPseudo(pseudo);
        if (utilisateur == null) {
            throw new UtilisateurNonTrouveException("Utilisateur non trouvé avec le pseudo : " + pseudo);
        }
        return utilisateur;
    }
   
//        Optional<Utilisateur> utilisateurOptional = Optional.of(utilisateurDAO.findByPseudo(pseudo));
//        if (utilisateurOptional.isPresent()) {
//            return utilisateurOptional.get();
//        } else {
//            throw new UtilisateurNonTrouveException("Utilisateur non trouvé avec le pseudo : " + pseudo);
//        }
//    } 

    public void updateUtilisateur(Utilisateur utilisateur) {
    	Utilisateur utilisateurExistant = findByPseudo(utilisateur.getPseudo()); // Utilisation du pseudo
        if (utilisateurExistant == null) {
            throw new UtilisateurNonTrouveException("Utilisateur non trouvé pour la mise à jour");
        }
        // Logique de mise à jour des champs de l'utilisateur
        // ... (Assurez-vous de ne pas modifier le pseudo ici si vous l'utilisez comme clé unique)
        utilisateurDAO.updateUtilisateur(utilisateur);
    }

	@Override
	public void saveUtilisateur(Utilisateur utilisateur) {
		// TODO Auto-generated method stub
		
	}
}