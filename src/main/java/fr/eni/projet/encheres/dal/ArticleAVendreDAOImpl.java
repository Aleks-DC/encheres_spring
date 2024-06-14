package fr.eni.projet.encheres.dal;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
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
        KeyHolder keyHolder = new GeneratedKeyHolder(); // Pour récupérer l'ID généré

        String sql = "INSERT INTO ARTICLES_A_VENDRE (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, id_utilisateur, no_categorie, no_adresse_retrait) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(
                connection -> connection.prepareStatement(sql, new String[] { "no_article" })
        );
 
        articleAVendre.setNoArticle(keyHolder.getKey().intValue()); // Mettre à jour l'ID de l'article
        return articleAVendre;
    }

    @Override
    public ArticleAVendre getById(int id) {
        String sql = "SELECT * FROM ARTICLES_A_VENDRE WHERE no_article = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ArticleAVendre.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null; // Ou lancer une exception spécifique si l'article n'est pas trouvé
        }
    }
    

    @Override
    public List<ArticleAVendre> getAll() {
        String sql = "SELECT * FROM ARTICLES_A_VENDRE";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ArticleAVendre.class));
    }

    @Override
    public void update(ArticleAVendre articleAVendre) {
        String sql = "UPDATE ARTICLES_A_VENDRE SET nom_article = ?, description = ?, date_debut_encheres = ?, date_fin_encheres = ?, prix_initial = ?, id_utilisateur = ?, no_categorie = ?, no_adresse_retrait = ? WHERE no_article = ?";
        jdbcTemplate.update(sql, 
            articleAVendre.getNom(),
            articleAVendre.getDescription(),
            articleAVendre.getDateDebutEncheres(),
            articleAVendre.getDateFinEncheres(),
            articleAVendre.getPrixInitial(),
            articleAVendre.getVendeur().getPseudo(),
            articleAVendre.getCategorie().getId(),
            articleAVendre.getRetrait().getId(),
            articleAVendre.getNoArticle()); 
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM ARTICLES_A_VENDRE WHERE no_article = ?";
        jdbcTemplate.update(sql, id);
    }
}
