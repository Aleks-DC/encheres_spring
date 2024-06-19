package fr.eni.projet.encheres;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.projet.encheres.bll.UtilisateurService;
import fr.eni.projet.encheres.bo.Adresse;
import fr.eni.projet.encheres.bo.Utilisateur;
import fr.eni.projet.encheres.dal.UtilisateurDAO;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class CreateUtilisateurTest {

    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    UtilisateurDAO utilisateurDAO;

    @Test
    @Transactional
    public void testCreerUtilisateur() {
        // Création de l'adresse
        Adresse adresse = new Adresse();
        adresse.setRue("rue de test");
        adresse.setCodePostal("75000");
        adresse.setVille("Paris");

        // Création de l'utilisateur
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPseudo("AleksDC");
        utilisateur.setNom("DC");
        utilisateur.setPrenom("Aleks");
        utilisateur.setEmail("aleks@dc.com");
        utilisateur.setTelephone("0123456789");
        utilisateur.setMotDePasse("password");
        //utilisateur.setCredit(88);
        utilisateur.setAdmin(true);
        
        // Appel du service pour créer l'utilisateur et son adresse
        utilisateurService.creerUtilisateur(utilisateur, adresse);

        // Recherche de l'utilisateur pour vérification
        Utilisateur testUtilisateur = utilisateurService.consulterUtilisateur("AleksDC");
        assertNotNull(testUtilisateur, "L'utilisateur ne devrait pas être null");
        
        // Vérification des champs de l'utilisateur
        assertEquals("AleksDC", testUtilisateur.getPseudo(), "Le pseudo de l'utilisateur ne correspond pas");
        assertEquals("DC", testUtilisateur.getNom(), "Le nom de l'utilisateur ne correspond pas");
        assertEquals("Aleks", testUtilisateur.getPrenom(), "Le prénom de l'utilisateur ne correspond pas");
        assertEquals("aleks@dc.com", testUtilisateur.getEmail(), "L'email de l'utilisateur ne correspond pas");
        assertEquals("0123456789", testUtilisateur.getTelephone(), "Le téléphone de l'utilisateur ne correspond pas");

        // Vérification du mot de passe encodé
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        assertTrue(passwordEncoder.matches("password", testUtilisateur.getMotDePasse()), "Le mot de passe ne correspond pas");

        //assertEquals(88, testUtilisateur.getCredit(), "Le crédit de l'utilisateur ne correspond pas");
        assertEquals(true, testUtilisateur.isAdmin(), "Le statut admin de l'utilisateur ne correspond pas");

        // Vérification des champs de l'adresse
        assertNotNull(testUtilisateur.getAdresse(), "L'adresse ne devrait pas être null");
        assertEquals("rue de test", testUtilisateur.getAdresse().getRue(), "La rue de l'adresse ne correspond pas");
        assertEquals("75000", testUtilisateur.getAdresse().getCodePostal(), "Le code postal de l'adresse ne correspond pas");
        assertEquals("Paris", testUtilisateur.getAdresse().getVille(), "La ville de l'adresse ne correspond pas");
    }
}
