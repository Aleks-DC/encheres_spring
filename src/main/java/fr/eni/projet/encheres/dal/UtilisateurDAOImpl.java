package fr.eni.projet.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.projet.encheres.bo.Adresse;
import fr.eni.projet.encheres.bo.Utilisateur;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {

	private final String INSERT_USER = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, mot_de_passe, credit, administrateur, no_adresse) VALUES (:pseudo, :nom, :prenom, :email, :telephone, :motDePasse, :credit, :administrateur, :noAdresse)";
//	private final String UPDATE_USER = "UPDATE UTILISATEURS SET nom=:nom, prenom=:prenom, email=:email, telephone=:telephone, administrateur=:administrateur, no_adresse=:noAdresse, adresse=:adresse WHERE pseudo=:pseudo";
	private final String FIND_BY_PSEUDO = "SELECT pseudo, nom, prenom, email, telephone, mot_de_passe, credit, administrateur, no_adresse FROM UTILISATEURS WHERE pseudo = :pseudo";
//    private final String FIND_BY_PSEUDO = 
//    	    "SELECT u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.mot_de_passe, u.credit, u.administrateur, " +
//    	    "a.no_adresse, a.rue, a.code_postal, a.ville " +
//    	    "FROM UTILISATEURS u " +
//    	    "LEFT JOIN ADRESSES a ON u.no_adresse = a.no_adresse " +
//    	    "WHERE u.pseudo = :pseudo";
	private final String FIND_ALL_USERS = "SELECT pseudo, nom, prenom, email, telephone, mot_de_passe, credit, administrateur, no_adresse FROM UTILISATEURS";
	private final String DELETE_BY_PSEUDO = "DELETE FROM UTILISATEURS WHERE pseudo = :pseudo";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public void create(Utilisateur utilisateur) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", utilisateur.getPseudo());
		namedParameters.addValue("nom", utilisateur.getNom());
		namedParameters.addValue("prenom", utilisateur.getPrenom());
		namedParameters.addValue("email", utilisateur.getEmail());
		namedParameters.addValue("telephone", utilisateur.getTelephone());
		namedParameters.addValue("motDePasse", utilisateur.getMotDePasse());
		namedParameters.addValue("credit", utilisateur.getCredit());
		namedParameters.addValue("administrateur", utilisateur.isAdmin());
		namedParameters.addValue("noAdresse", utilisateur.getAdresse().getId());

		jdbcTemplate.update(INSERT_USER, namedParameters);

	}

	@Override
	public Utilisateur findByPseudo(String pseudo) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", pseudo);
		return jdbcTemplate.queryForObject(FIND_BY_PSEUDO, namedParameters, new UtilisateurRowMapper());
	}

	@Override
	public List<Utilisateur> findAll() {
		return jdbcTemplate.query(FIND_ALL_USERS, new UtilisateurRowMapper());
	}

	class UtilisateurRowMapper implements RowMapper<Utilisateur> {
		@Override
		public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
			Utilisateur utilisateur = new Utilisateur();
			utilisateur.setPseudo(rs.getString("pseudo"));
			utilisateur.setNom(rs.getString("nom"));
			utilisateur.setPrenom(rs.getString("prenom"));
			utilisateur.setEmail(rs.getString("email"));
			utilisateur.setTelephone(rs.getString("telephone"));
			utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
			utilisateur.setCredit(rs.getInt("credit"));
			utilisateur.setAdmin(rs.getBoolean("administrateur"));

			// On MAP juste l'ID, le reste sera géré dans la BLL
			Adresse adresse = new Adresse();
			adresse.setId(rs.getLong("no_adresse"));
//            adresse.setRue(rs.getString("rue"));
//            adresse.setCodePostal(rs.getString("code_postal"));
//            adresse.setVille(rs.getString("ville"));
			utilisateur.setAdresse(adresse);
			return utilisateur;
		}
	}

	@Override
    public void update(Utilisateur utilisateur) {
		String UPDATE_USER = "UPDATE UTILISATEURS SET nom=:nom, prenom=:prenom, email=:email, telephone=:telephone, administrateur=:administrateur, no_adresse=:noAdresse, adresse=:adresse WHERE pseudo=:pseudo";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("nom", utilisateur.getNom());
        namedParameters.addValue("prenom", utilisateur.getPrenom());
        namedParameters.addValue("email", utilisateur.getEmail());
        namedParameters.addValue("telephone", utilisateur.getTelephone());
        namedParameters.addValue("administrateur", utilisateur.isAdmin());
        namedParameters.addValue("noAdresse", utilisateur.getAdresse().getId());
        namedParameters.addValue("adresse", utilisateur.getAdresse());
        jdbcTemplate.update(UPDATE_USER, namedParameters);
        
        int rowsUpdated = jdbcTemplate.update(UPDATE_USER, namedParameters);

        if (rowsUpdated > 0) {
            // Mise à jour réussie
            System.out.println("Utilisateur mis à jour avec succès: " + utilisateur);
        } else {
            // Aucune mise à jour effectuée
            System.out.println("Aucune mise à jour n'a été effectuée pour l'utilisateur: " + utilisateur);
        }
    }
	

	@Override
	public void deleteByPseudo(String pseudo) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", pseudo);
		jdbcTemplate.update(DELETE_BY_PSEUDO, namedParameters);
	}
}
