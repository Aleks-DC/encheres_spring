package fr.eni.projet.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import fr.eni.projet.encheres.bo.Adresse;
import fr.eni.projet.encheres.bo.ArticleAVendre;
import fr.eni.projet.encheres.bo.Categorie;
import fr.eni.projet.encheres.bo.Utilisateur;

@Repository
public class ArticleAVendreDAOImpl implements ArticleAVendreDAO {

	private final JdbcTemplate jdbcTemplate;

	public ArticleAVendreDAOImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Autowired
	private DataSource dataSource;

	public void creer(ArticleAVendre article) {
		String sql = "INSERT INTO ARTICLES_A_VENDRE (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, id_utilisateur, no_categorie, photo, no_adresse_retrait) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			pstmt.setString(1, article.getNom());
			pstmt.setString(2, article.getDescription());
			pstmt.setDate(3, java.sql.Date.valueOf(article.getDateDebutEncheres()));
			pstmt.setDate(4, java.sql.Date.valueOf(article.getDateFinEncheres()));
			pstmt.setInt(5, article.getPrixInitial());
			pstmt.setString(6, article.getNomVendeur());

			if (article.getCategorie() != null) {
				pstmt.setLong(7, article.getCategorie().getId());
			} else {
				pstmt.setNull(7, Types.BIGINT);
			}

			pstmt.setString(8, article.getPhoto());

			Long noAdresseRetrait = getNoAdresseRetraitUtilisateur(article.getNomVendeur());
			if (noAdresseRetrait != null) {
				pstmt.setLong(9, noAdresseRetrait);
			} else {
				pstmt.setNull(9, Types.BIGINT); // Ou une autre valeur par défaut si nécessaire
			}

			int affectedRows = pstmt.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("La création de l'article a échoué, aucune ligne ajoutée.");
			}
			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					article.setId(generatedKeys.getLong(1));
				} else {
					throw new SQLException("La création de l'article a réussi, mais aucun ID n'a été obtenu.");
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Erreur lors de la création de l'article : " + e.getMessage(), e);
		}
	}

	private Long getNoAdresseRetraitUtilisateur(String idUtilisateur) {
		String sql = "SELECT no_adresse FROM UTILISATEURS WHERE pseudo = ?";
		try {
			return jdbcTemplate.queryForObject(sql, Long.class, idUtilisateur);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public ArticleAVendre getById(long noArticle) {
		String sql = "SELECT a.*, u.pseudo, u.nom, u.prenom, "
				+ "c.libelle, r.rue, r.code_postal, r.ville, r.no_adresse " + "FROM ARTICLES_A_VENDRE a "
				+ "JOIN UTILISATEURS u ON a.id_utilisateur = u.pseudo "
				+ "JOIN CATEGORIES c ON a.no_categorie = c.no_categorie "
				+ "LEFT JOIN ADRESSES r ON u.no_adresse = r.no_adresse " + "WHERE a.no_article = ?";
		try {
			return jdbcTemplate.queryForObject(sql, new AllRowMapper(), noArticle);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Categorie> getAllCategories() {
		String sql = "SELECT * FROM CATEGORIES";
		return jdbcTemplate.query(sql, new CategorieRowMapper());
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

	@Override
	public Categorie getCategorieById(Long id) {
		String sql = "SELECT * FROM CATEGORIES WHERE no_categorie = ?";
		try {
			return jdbcTemplate.queryForObject(sql, new CategorieRowMapper(), id);
		} catch (EmptyResultDataAccessException e) {
			throw new IllegalArgumentException("Catégorie non trouvée pour l'ID : " + id); // Lever une exception
		}
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
			article.setStatut(rs.getInt("statut_enchere"));
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

	static class CategorieRowMapper implements RowMapper<Categorie> { // Classe interne statique
		@Override
		public Categorie mapRow(ResultSet rs, int rowNum) throws SQLException {
			Categorie categorie = new Categorie();
			categorie.setId(rs.getLong("no_categorie"));
			categorie.setLibelle(rs.getString("libelle"));
			return categorie;
		}
	}

	// TODO @Alexis DAO filtrage par catégorie et mots clé
	@Override
	public List<ArticleAVendre> getByCategorie(long categorieId) {
		String sql = "SELECT * FROM ARTICLES_A_VENDRE WHERE no_categorie = ?";
		return jdbcTemplate.query(sql, new ArticleRowMapper(), categorieId);
	}

	@Override
	public List<ArticleAVendre> searchByMotCle(String motCle) {
		String sql = "SELECT * FROM ARTICLES_A_VENDRE WHERE nom_article LIKE ?";
		return jdbcTemplate.query(sql, new ArticleRowMapper(), "%" + motCle + "%");
	}

	@Override
	public List<ArticleAVendre> findByCategorieAndMotCle(long categorieId, String motCle) {
		String sql = "SELECT * FROM ARTICLES_A_VENDRE WHERE no_categorie = ? AND nom_article LIKE ?";
		return jdbcTemplate.query(sql, new ArticleRowMapper(), categorieId, "%" + motCle + "%");
	}

	// TODO @Alexis Filtrage par état de mes ventes

	@Override
	public List<ArticleAVendre> getToutesMesVentes(String pseudoVendeur) {
		String sql = "SELECT * FROM ARTICLES_A_VENDRE WHERE id_utilisateur = ? ";
		return jdbcTemplate.query(sql, new PerfectRowMapper(), pseudoVendeur);
	}

	@Override
	public List<ArticleAVendre> getMesVentesNonDebutees(String pseudoVendeur) {
		String sql = "SELECT * FROM ARTICLES_A_VENDRE WHERE id_utilisateur = ? AND statut_enchere = 0";
		return jdbcTemplate.query(sql, new PerfectRowMapper(), pseudoVendeur);
	}

	@Override
	public List<ArticleAVendre> getMesVentesEnCours(String pseudoVendeur) {
		String sql = "SELECT * FROM ARTICLES_A_VENDRE WHERE id_utilisateur = ? AND statut_enchere = 1";
		return jdbcTemplate.query(sql, new PerfectRowMapper(), pseudoVendeur);
	}

	@Override
	public List<ArticleAVendre> getMesVentesTerminees(String pseudoVendeur) {
		String sql = "SELECT * FROM ARTICLES_A_VENDRE WHERE id_utilisateur = ? AND statut_enchere = 2";
		return jdbcTemplate.query(sql, new PerfectRowMapper(), pseudoVendeur);
	}

	class PerfectRowMapper implements RowMapper<ArticleAVendre> {
		@Override
		public ArticleAVendre mapRow(ResultSet rs, int rowNum) throws SQLException {
			ArticleAVendre articleAVendre = new ArticleAVendre();
			articleAVendre.setId(rs.getLong("no_article"));
			articleAVendre.setNom(rs.getString("nom_article"));
			articleAVendre.setDescription(rs.getString("description"));
			articleAVendre.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
			articleAVendre.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
			articleAVendre.setStatut(rs.getInt("statut_enchere"));
			articleAVendre.setPrixInitial(rs.getInt("prix_initial"));
			articleAVendre.setPrixVente(rs.getInt("prix_vente"));

			Utilisateur vendeur = new Utilisateur();
			vendeur.setPseudo(rs.getString("id_utilisateur"));
			articleAVendre.setVendeur(vendeur);

			Adresse retrait = new Adresse();
			retrait.setId(rs.getLong("no_adresse_retrait"));
			articleAVendre.setRetrait(retrait);

			Categorie categorie = new Categorie();
			categorie.setId(rs.getLong("no_categorie"));
			articleAVendre.setCategorie(categorie);

			return articleAVendre;
		}

	}
}
