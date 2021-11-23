package org.iesfm.shop.dao.jdbc;

import org.iesfm.shop.Article;
import org.iesfm.shop.Tag;
import org.iesfm.shop.dao.ArticleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.*;

public class JDBCArticleDAO implements ArticleDAO {


    private NamedParameterJdbcTemplate jdbc;


    public JDBCArticleDAO(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final String UPDATE_ARTICLE =
    "UPDATE Article SET VALUE id = :id, name = :name, price = :price WHERE id = :id";

    private static final String INSERT_ARTICLE =
            "INSERT INTO Article(id,name,price) VALUES(:id,:name,:price)";

    private static final String SELECT_ARTICLES =
            "SELECT * FROM Article";

    private static final String SELECT_ARTICLES_BY_ID =
            "SELECT * FROM Article WHERE article_id = :id";

    private static final String SELECT_ARTICLES_TAG =
            "SELECT * FROM Article art INNER JOIN Tag t ON :id = ";

    private static final String INSERT_TAGS =
            "INSERT INTO Tag(articleId, name) VALUES(:articleId,:name)";

    private static final String ARTICLE_TAGS = "SELECT name FROM Tag WHERE article_id = :article_id";

    private static final String DELETE_ARTICLE = "DELETE FROM Article WHERE id = :id";

    private final RowMapper<Article> ARTICLE_ROW_MAPPER =
            ((rs,rowNum) -> new Article(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    getArticleTags(rs.getInt("id"))
            ));

    private Set<String> getArticleTags(int articleId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", articleId);
        return new HashSet<>(jdbc.query(ARTICLE_TAGS,params,
                (rs,rowNum) -> rs.getString("name")));
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
        return jdbc.query(SELECT_ARTICLES,ARTICLE_ROW_MAPPER);
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
        try{
            Map<String,Object> params = new HashMap<>();
            params.put("id",article.getId());
            params.put("name",article.getName());
            params.put("price",article.getPrice());
            params.put("tag",getArticleTags(article.getId()));
            jdbc.update(INSERT_ARTICLE,params);
            for(String tag : article.getTags()){
                Map<String, Object> tagsParams = new HashMap<>();
                tagsParams.put("article_id",article.getId());
                tagsParams.put("name",tag);
                jdbc.update(INSERT_TAGS,tagsParams);

            }
            return true;
        }catch(DuplicateKeyException e){
            return false;
        }

    }

    @Override
    public boolean update(Article article) {
        try{
            Map<String, Object> params = new HashMap<>();
            params.put("id",article.getId());
            params.put("name",article.getName());
            params.put("price",article.getPrice());
            jdbc.update(UPDATE_ARTICLE,params);
            return true;
        }catch(EmptyResultDataAccessException e){
            return false;
        }

    }

    @Override
    public boolean delete(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id",id);
        try{
            jdbc.update(DELETE_ARTICLE,params);
            return true;
        }catch(EmptyResultDataAccessException e){
            return false;
        }

    }
}
