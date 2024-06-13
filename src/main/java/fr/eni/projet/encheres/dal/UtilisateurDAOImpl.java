package fr.eni.projet.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.projet.encheres.bo.Adresse;
import fr.eni.projet.encheres.bo.Utilisateur;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {
	
	
	@Autowired 
	private NamedParameterJdbcTemplate jdbcTemplate;

	private final String FIND_BY_PSEUDO = "SELECT u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.mot_de_passe, u.credit, u.administrateur, a.rue, a.code_postal, a.ville " +
								            "FROM Utilisateurs u " +
								            "INNER JOIN Adresses a ON u.no_adresse = a.no_adresse " +
								            "WHERE u.pseudo = :pseudo";
	private final String UPDATE_UTILISATEUR= "UPDATE Utilisateurs SET (nom = :nom, prenom = :prenom, email = :email, telephone = :telephone WHERE pseudo = :pseudo ";
	private final String UPDATE_ADRESSE = "UPDATE Adresses SET rue = :rue, code_postal = :codePostal, ville = :ville WHERE no_adresse = :noAdresse";
	

	@Override
	public Utilisateur findByPseudo(String pseudo) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
	    namedParameters.addValue("pseudo", pseudo);
	    
	   
	    try {
	        return jdbcTemplate.queryForObject(FIND_BY_PSEUDO, namedParameters, new UtilisateurRowMapper());
	    } catch (EmptyResultDataAccessException e) {
	        return null;
	    }	}
	
	//Abdallah : Est-ce qu'il faut séparer INSERT TABLE Utilisateurs et Adresses ? 
	@Override
	public void updateUtilisateur(Utilisateur utilisateur) {
	    MapSqlParameterSource namedParametersUtilisateur = new MapSqlParameterSource();
	    namedParametersUtilisateur.addValue("nom", utilisateur.getNom());
	    namedParametersUtilisateur.addValue("prenom", utilisateur.getPrenom());
	    namedParametersUtilisateur.addValue("email", utilisateur.getEmail());
	    namedParametersUtilisateur.addValue("telephone", utilisateur.getTelephone());
	    namedParametersUtilisateur.addValue("pseudo", utilisateur.getPseudo());
	    jdbcTemplate.update(UPDATE_UTILISATEUR, namedParametersUtilisateur);

	    if (utilisateur.getAdresse() != null) {
	        MapSqlParameterSource namedParametersAdresse = new MapSqlParameterSource();
	        namedParametersAdresse.addValue("rue", utilisateur.getAdresse().getRue());
	        namedParametersAdresse.addValue("codePostal", utilisateur.getAdresse().getCodePostal());
	        namedParametersAdresse.addValue("ville", utilisateur.getAdresse().getVille());
	        namedParametersAdresse.addValue("noAdresse", utilisateur.getAdresse().getId());

	        jdbcTemplate.update(UPDATE_ADRESSE, namedParametersAdresse);
	    }
	}
	
	private static class UtilisateurRowMapper implements RowMapper<Utilisateur> {
	    @Override
	    public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
	        Utilisateur utilisateur = new Utilisateur();
	        utilisateur.setPseudo(rs.getString("pseudo"));
	        utilisateur.setPrenom(rs.getString("prenom"));
	        utilisateur.setNom(rs.getString("nom"));
	        utilisateur.setEmail(rs.getString("Email"));
	        utilisateur.setTelephone(rs.getString("telephone"));
	        utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
	        utilisateur.setCredit(rs.getInt("credit"));
	        utilisateur.setAdmin(rs.getBoolean("administrateur"));
	        // ... (mapper les autres champs de l'utilisateur)
	        
	        // Mapper l'adresse
	        Adresse adresse = new Adresse();
	        adresse.setRue(rs.getString("rue"));
	        adresse.setCodePostal(rs.getString("code_postal"));
	        adresse.setVille(rs.getString("ville"));
	        utilisateur.setAdresse(adresse);
	        
	        return utilisateur;
	    }
	}
}