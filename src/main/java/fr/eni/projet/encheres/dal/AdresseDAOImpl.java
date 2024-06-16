package fr.eni.projet.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.projet.encheres.bo.Adresse;

@Repository
public class AdresseDAOImpl implements AdresseDAO {

	private final String INSERT = "INSERT INTO ADRESSES (rue, code_postal, ville, adresse_eni) "
			+ " VALUES (:rue, :codePostal, :ville, :adresseEni)";
	private final String FIND_BY_ID = "SELECT no_adresse, rue, code_postal, ville, adresse_eni FROM ADRESSES WHERE no_adresse = :noAdresse";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public Adresse create(Adresse adresse) {

		KeyHolder keyHolder = new GeneratedKeyHolder();

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("rue", adresse.getRue());
		namedParameters.addValue("codePostal", adresse.getCodePostal());
		namedParameters.addValue("ville", adresse.getVille());
		namedParameters.addValue("adresseEni", false);

		jdbcTemplate.update(INSERT, namedParameters, keyHolder);

		if (keyHolder != null && keyHolder.getKey() != null) {
			// Mise à jour de l'identifiant de l'adresse auto-généré par la base
			adresse.setId(keyHolder.getKey().longValue());
		}
		return adresse;
	}

	@Override
	public Adresse findById(int noAdresse) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("noAdresse", noAdresse);
		return jdbcTemplate.queryForObject(FIND_BY_ID, params, new AdresseRowMapper());
	}

	class AdresseRowMapper implements RowMapper<Adresse> {
		@Override
		public Adresse mapRow(ResultSet rs, int rowNum) throws SQLException {
			Adresse adresse = new Adresse();
			adresse.setId(rs.getLong("no_adresse"));
			adresse.setRue(rs.getString("rue"));
			adresse.setCodePostal(rs.getString("code_postal"));
			adresse.setVille(rs.getString("ville"));
//			adresse.setVille(rs.getString("adresse_eni"));
			return adresse;
		}
	}

	@Override
	public void update(Adresse Adresse) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Adresse> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(int noAdresse) {
		// TODO Auto-generated method stub

	}

}
