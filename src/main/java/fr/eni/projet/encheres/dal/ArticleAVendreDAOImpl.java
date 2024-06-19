package fr.eni.projet.encheres.dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.projet.encheres.bo.Adresse;
import fr.eni.projet.encheres.bo.ArticleAVendre;
import fr.eni.projet.encheres.bo.Categorie;

@Repository
public class ArticleAVendreDAOImpl implements ArticleAVendreDAO {

	private final JdbcTemplate jdbcTemplate;

	public ArticleAVendreDAOImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public ArticleAVendre creer(ArticleAVendre articleAVendre) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(
					"INSERT INTO ARTICLES_A_VENDRE (nom_article, description, photo, date_debut_encheres, date_fin_encheres, prix_initial, id_utilisateur, no_categorie, no_adresse_retrait) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, articleAVendre.getNom());
			ps.setString(2, articleAVendre.getDescription());
			ps.setDate(4, Date.valueOf(articleAVendre.getDateDebutEncheres()));
			ps.setDate(5, Date.valueOf(articleAVendre.getDateFinEncheres()));
			ps.setInt(6, articleAVendre.getPrixInitial());
			ps.setString(7, articleAVendre.getVendeur().getNom());
			ps.setLong(8, articleAVendre.getCategorie().getId());
			ps.setLong(9, articleAVendre.getRetrait().getId());
			return ps;
		}, keyHolder);

		articleAVendre.setId(keyHolder.getKey().longValue()); // Attention, l'ID dans ArticleAVendre est un long
		return articleAVendre;
	}

	@Override
	public ArticleAVendre getById(long noArticle) {
		String sql = "SELECT a.*, u.pseudo, u.nom, u.prenom, " +
                "c.libelle, r.rue, r.code_postal, r.ville, r.no_adresse " + 
                "FROM ARTICLES_A_VENDRE a " +
                "JOIN UTILISATEURS u ON a.id_utilisateur = u.pseudo " + 
                "JOIN CATEGORIES c ON a.no_categorie = c.no_categorie " +
                "LEFT JOIN ADRESSES r ON u.no_adresse = r.no_adresse " +
                "WHERE a.no_article = ?";
	    try {
	        return jdbcTemplate.queryForObject(sql, new AllRowMapper(), noArticle);
	    } catch (EmptyResultDataAccessException e) {
	        return null; 
	    }
	}

	@Override
	public List<ArticleAVendre> getAll() {
		String sql = "SELECT * FROM ARTICLES_A_VENDRE";
		return jdbcTemplate.query(sql, new ArticleRowMapper());
	}

	@Override
	public void update(ArticleAVendre articleAVendre) {
		String sql = "UPDATE ARTICLES_A_VENDRE SET nom_article = ?, description = ?, photo = ?, date_debut_encheres = ?, date_fin_encheres = ?, prix_initial = ?, id_utilisateur = ?, no_categorie = ?, no_adresse_retrait = ? WHERE no_article = ?";
		jdbcTemplate.update(sql, articleAVendre.getNom(), articleAVendre.getDescription(),
				java.sql.Date.valueOf(articleAVendre.getDateDebutEncheres()),
				java.sql.Date.valueOf(articleAVendre.getDateFinEncheres()), articleAVendre.getPrixInitial(),
				articleAVendre.getVendeur(), articleAVendre.getCategorie(), articleAVendre.getRetrait(),
				articleAVendre.getNoArticle());
	}

	@Override
	public void delete(int noArticle) {
		String sql = "DELETE FROM ARTICLES_A_VENDRE WHERE no_article = ?";
		jdbcTemplate.update(sql, noArticle);
	}
	
	class ArticleRowMapper implements RowMapper<ArticleAVendre> {
		@Override
		public ArticleAVendre mapRow(ResultSet rs, int rowNum) throws SQLException {
			ArticleAVendre article = new ArticleAVendre();
			article.setId(rs.getLong("no_article"));
			article.setNom(rs.getString("nom_article"));
			article.setDescription(rs.getString("description"));
			article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
			article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
			article.setPrixInitial(rs.getInt("prix_initial"));
			article.setPrixVente(rs.getInt("prix_vente"));
			article.setNomVendeur(rs.getString("id_utilisateur"));
			article.setPhoto(rs.getString("photo"));

			return article;
		}
	}

	class AllRowMapper implements RowMapper<ArticleAVendre> {
        @Override
        public ArticleAVendre mapRow(ResultSet rs, int rowNum) throws SQLException {
            ArticleAVendre article = new ArticleAVendre();
            article.setId(rs.getLong("no_article"));
            article.setNom(rs.getString("nom_article"));
            article.setDescription(rs.getString("description"));
            article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
            article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
            article.setPrixInitial(rs.getInt("prix_initial"));
            article.setPrixVente(rs.getInt("prix_vente"));
            article.setNomVendeur(rs.getString("id_utilisateur"));
            article.setPhoto(rs.getString("photo"));
            Adresse retrait = new Adresse();
            retrait.setId(rs.getLong("no_adresse"));
            retrait.setRue(rs.getString("rue"));
            retrait.setCodePostal(rs.getString("code_postal"));
            retrait.setVille(rs.getString("ville"));
            article.setRetrait(retrait);
            Categorie categorie = new Categorie();
            categorie.setId(rs.getLong("no_categorie"));
            categorie.setLibelle(rs.getString("libelle"));
            article.setCategorie(categorie);

            return article;
        }
	}

}
