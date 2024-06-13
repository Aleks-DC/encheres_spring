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

	private final String FIND_BY_PSEUDO = "SELECT * FROM Utilisateurs WHERE pseudo = :pseudo";
	private final String UPDATE = "UPDATE Utilisateurs SET (nom = :nom, prenom = :prenom, email = :email, telephone = :telephone WHERE pseudo = :pseudo ";
									//Ajouter élément Adresse du create
	

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
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("nom", utilisateur.getNom());
		namedParameters.addValue("prenom", utilisateur.getPrenom());
		namedParameters.addValue("email", utilisateur.getEmail());
		namedParameters.addValue("telephone", utilisateur.getTelephone());
		jdbcTemplate.update(UPDATE, namedParameters);
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