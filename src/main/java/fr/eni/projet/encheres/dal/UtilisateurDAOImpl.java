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
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	private final String INSERT_USER = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, mot_de_passe, credit, administrateur, no_adresse) VALUES (:pseudo, :nom, :prenom, :email, :telephone, :motDePasse, :credit, :administrateur, :noAdresse)";
	private final String UPDATE_USER = "UPDATE UTILISATEURS SET nom=:nom, prenom=:prenom, email=:email, telephone=:telephone, no_adresse=:noAdresse WHERE pseudo=:pseudo";
	private final String FIND_BY_PSEUDO = "SELECT pseudo, nom, prenom, email, telephone, mot_de_passe, credit, administrateur, no_adresse FROM UTILISATEURS WHERE pseudo = :pseudo";
	private final String FIND_BY_EMAIL = "SELECT pseudo, nom, prenom, email, telephone, mot_de_passe, credit, administrateur, no_adresse FROM UTILISATEURS WHERE email = :email";
	private final String FIND_ALL_USERS = "SELECT pseudo, nom, prenom, email, telephone, mot_de_passe, credit, administrateur, no_adresse FROM UTILISATEURS";
	private final String DELETE_BY_PSEUDO = "DELETE FROM UTILISATEURS WHERE pseudo = :pseudo";
	


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
	public int update(Utilisateur utilisateur) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", utilisateur.getPseudo());
		namedParameters.addValue("nom", utilisateur.getNom());
		namedParameters.addValue("prenom", utilisateur.getPrenom());
		namedParameters.addValue("email", utilisateur.getEmail());
		namedParameters.addValue("telephone", utilisateur.getTelephone());
		// namedParameters.addValue("motDePasse", utilisateur.getMotDePasse());
		// namedParameters.addValue("credit", utilisateur.getCredit());
		// namedParameters.addValue("administrateur", utilisateur.isAdmin());
		namedParameters.addValue("noAdresse", utilisateur.getAdresse().getId());

		return jdbcTemplate.update(UPDATE_USER, namedParameters);
	}

	@Override
	public Utilisateur findByPseudo(String pseudo) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", pseudo);
		return jdbcTemplate.queryForObject(FIND_BY_PSEUDO, namedParameters, new UtilisateurRowMapper());
	}

	@Override
	public Utilisateur findByEmail(String email) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("email", email);
		return jdbcTemplate.queryForObject(FIND_BY_EMAIL, namedParameters, new UtilisateurRowMapper());
	}

	@Override
	public List<Utilisateur> findAll() {
		return jdbcTemplate.query(FIND_ALL_USERS, new UtilisateurRowMapper());
	}

	@Override
	public void deleteByPseudo(String pseudo) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", pseudo);
		jdbcTemplate.update(DELETE_BY_PSEUDO, namedParameters);
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
			utilisateur.setAdresse(adresse);
			return utilisateur;
		}
	}
}
