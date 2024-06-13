package fr.eni.projet.encheres.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.projet.encheres.bo.Utilisateur;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {
	
	@Autowired 
	private JdbcTemplate jdbcTemplate;
	
	private final String FIND_BY_PSEUDO = "SELECT * FROM Utilisateurs"
											+ "WHERE pseudo = ?";
	
	
	@Override
	public Utilisateur findByPseudo(String pseudo) {
		return jdbcTemplate.queryForObject(FIND_BY_PSEUDO, new BeanPropertyRowMapper<>(Utilisateur.class), pseudo);
	}

	@Override
	public void updateUtilisateur(Utilisateur utilisateur) {
		// TODO Auto-generated method stub
	}

}
