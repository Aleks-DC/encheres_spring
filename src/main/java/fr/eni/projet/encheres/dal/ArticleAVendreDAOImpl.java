package fr.eni.projet.encheres.dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.projet.encheres.bo.ArticleAVendre;

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
                    "INSERT INTO ARTICLES_A_VENDRE (nom_article, description, photo, date_debut_encheres, date_fin_encheres, prix_initial, id_utilisateur, no_categorie, no_adresse_retrait) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
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
    public ArticleAVendre getById(int noArticle) {
        String sql = "SELECT * FROM ARTICLES_A_VENDRE WHERE no_article = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ArticleAVendre.class), noArticle);
    }

    @Override
    public List<ArticleAVendre> getAll() {
        String sql = "SELECT * FROM ARTICLES_A_VENDRE";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ArticleAVendre.class));
    }

    @Override
    public void update(ArticleAVendre articleAVendre) {
        String sql = "UPDATE ARTICLES_A_VENDRE SET nom_article = ?, description = ?, photo = ?, date_debut_encheres = ?, date_fin_encheres = ?, prix_initial = ?, id_utilisateur = ?, no_categorie = ?, no_adresse_retrait = ? WHERE no_article = ?";
        jdbcTemplate.update(sql,
        		articleAVendre.getNom(),
        		articleAVendre.getDescription(),
                java.sql.Date.valueOf(articleAVendre.getDateDebutEncheres()),
                java.sql.Date.valueOf(articleAVendre.getDateFinEncheres()),
                articleAVendre.getPrixInitial(),
                articleAVendre.getVendeur(),
                articleAVendre.getCategorie(),
                articleAVendre.getRetrait(),
                articleAVendre.getNoArticle());
    }

    @Override
    public void delete(int noArticle) {
        String sql = "DELETE FROM ARTICLES_A_VENDRE WHERE no_article = ?";
        jdbcTemplate.update(sql, noArticle);
    }
}
