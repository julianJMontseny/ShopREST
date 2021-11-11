package org.iesfm.shop.dao.jdbc;

import org.iesfm.shop.Article;
import org.iesfm.shop.Tag;
import org.iesfm.shop.dao.ArticleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JDBCArticleDAO implements ArticleDAO {


    private NamedParameterJdbcTemplate jdbc;


    public JDBCArticleDAO(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final String SELECT_ARTICLES =
            "SELECT * FROM Article";

    private static final String SELECT_ARTICLES_BY_ID =
            "SELECT * FROM Article WHERE article_id = :id";

    private static final String SELECT_ARTICLES_TAG =
            "SELECT * FROM Article art INNER JOIN Tag t ON :id = ";

    private Set<String> getArticleTags(int articleId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", articleId);
        return null;
    }



    @Override
    public List<Article> list() {
        Map<String, Object> params = new HashMap<>();
        return jdbc.query(SELECT_ARTICLES, params, (rs, rowNum) -> new Article(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        getArticleTags(rs.getInt("id"))
                ));
    }

    @Override
    public List<Article> list(String tag) {
        return null;
    }

    @Override
    public Article get(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbc.queryForObject(SELECT_ARTICLES_BY_ID, params
                , (rs, rowNum) -> new Article(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        getArticleTags(rs.getInt("id"))
                ));
    }

    @Override
    public boolean insert(Article article) {
        return false;
    }

    @Override
    public boolean update(Article article) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
