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
public class UpdateUtilisateurTest {

    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    UtilisateurDAO utilisateurDAO;

    @Test
    @Transactional
    public void testModifierUtilisateur() {
        // Création de l'adresse initiale
        Adresse adresse = new Adresse();
        adresse.setRue("rue de test");
        adresse.setCodePostal("75000");
        adresse.setVille("Paris");

        // Création de l'utilisateur initial
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPseudo("AleksDC");
        utilisateur.setNom("DC");
        utilisateur.setPrenom("Aleks");
        utilisateur.setEmail("aleks@dc.com");
        utilisateur.setTelephone("0123456789");
        utilisateur.setMotDePasse("password");
        utilisateur.setCredit(88);
        utilisateur.setAdmin(true);
        
        // Appel du service pour créer l'utilisateur et son adresse
        utilisateurService.creerUtilisateur(utilisateur, adresse);

        // Modification des informations de l'utilisateur
        utilisateur.setNom("UpdatedNom");
        utilisateur.setPrenom("UpdatedPrenom");
        utilisateur.setEmail("updated@dc.com");
        utilisateur.setTelephone("0987654321");
        utilisateur.setMotDePasse("newpassword");
        utilisateur.setCredit(99);
        utilisateur.setAdmin(false);

        // Modification des informations de l'adresse
        Adresse updatedAdresse = utilisateur.getAdresse();
        updatedAdresse.setRue("updated rue de test");
        updatedAdresse.setCodePostal("75001");
        updatedAdresse.setVille("Updated Paris");

        // Appel du service pour mettre à jour l'utilisateur et son adresse
        utilisateurService.modifierUtilisateur(utilisateur, adresse);

        // Recherche de l'utilisateur pour vérification
        Utilisateur testUtilisateur = utilisateurService.consulterUtilisateur("AleksDC");
        assertNotNull(testUtilisateur, "L'utilisateur ne devrait pas être null");
        
        // Vérification des champs de l'utilisateur mis à jour
        assertEquals("AleksDC", testUtilisateur.getPseudo(), "Le pseudo de l'utilisateur ne correspond pas");
        assertEquals("UpdatedNom", testUtilisateur.getNom(), "Le nom de l'utilisateur ne correspond pas");
        assertEquals("UpdatedPrenom", testUtilisateur.getPrenom(), "Le prénom de l'utilisateur ne correspond pas");
        assertEquals("updated@dc.com", testUtilisateur.getEmail(), "L'email de l'utilisateur ne correspond pas");
        assertEquals("0987654321", testUtilisateur.getTelephone(), "Le téléphone de l'utilisateur ne correspond pas");



        // Vérification des champs de l'adresse mise à jour
        assertNotNull(testUtilisateur.getAdresse(), "L'adresse ne devrait pas être null");
        assertEquals("updated rue de test", testUtilisateur.getAdresse().getRue(), "La rue de l'adresse ne correspond pas");
        assertEquals("75001", testUtilisateur.getAdresse().getCodePostal(), "Le code postal de l'adresse ne correspond pas");
        assertEquals("Updated Paris", testUtilisateur.getAdresse().getVille(), "La ville de l'adresse ne correspond pas");
    }
}
