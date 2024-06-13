package fr.eni.projet.encheres.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.projet.encheres.bo.ArticleAVendre;

@Repository
public class ArticleAVendreDAO {

    private final JdbcTemplate jdbcTemplate;

    public ArticleAVendreDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ArticleAVendre creer(ArticleAVendre articleAVendre) {
        String sql = "INSERT INTO ARTICLES_A_VENDRE (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, id_utilisateur, no_categorie, no_adresse_retrait) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                articleAVendre.getNom(),
                articleAVendre.getDescription(),
                articleAVendre.getDateDebutEncheres(),
                articleAVendre.getDateFinEncheres(),
                articleAVendre.getPrixInitial(),
                articleAVendre.getVendeur().getPseudo(),
                articleAVendre.getCategorie().getId(),
                articleAVendre.getRetrait().getId());

        return articleAVendre;
    }
}
