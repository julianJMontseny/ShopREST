package org.iesfm.shop.dao.jdbc;

import org.iesfm.shop.Article;
import org.iesfm.shop.Tag;
import org.iesfm.shop.dao.ArticleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.*;

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
            "SELECT * FROM Article art" +
                    " INNER JOIN Tag t ON art.id = t.article_id" +
                    " WHERE t.name = :tag ";

    private static final String ARTICLE_TAGS = "SELECT name FROM Tag WHERE article_id = :article_id";

    private static final String INSERT_ARTICLE = "INSERT INTO Article (id,name,price) " +
                                                    " VALUES(:id,:name,:price)";

    private static final String INSERT_TAG = "INSERT INTO Tag(article_id, name) VALUES(:id,:tag)";

    private static final String UPDATE_ARTICLE = "UPDATE FROM Article SET name=:name, price=:price WHERE id=:id";

    private static final String DELETE_ARTICLE = "DELETE FROM Article WHERE id=:id";

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
        Map<String, Object> params = new HashMap<>();
        params.put("tag",tag);
        return jdbc.query(SELECT_ARTICLES,params,ARTICLE_ROW_MAPPER);
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
            Map<String,Object> insertArticleParams = new HashMap<>();
            insertArticleParams.put("id", article.getId());
            insertArticleParams.put("name", article.getName());
            insertArticleParams.put("price", article.getPrice());
            jdbc.update(INSERT_ARTICLE,insertArticleParams);

            for (String tag : article.getTags()){
                insertTag(article.getId(),tag);
            }
                    return true;
        }catch(DuplicateKeyException e){
            return false;
        }

    }

    //Este metodo inserta tags dadas a un article con un ID dado.
    private void insertTag(int articleId, String tag){
        Map<String,Object> insertTagParamas = new HashMap<>();
        insertTagParamas.put("id",articleId);
        insertTagParamas.put("tag",tag);
        jdbc.update(INSERT_TAG,insertTagParamas);
    }

    @Override
    public boolean update(Article article) {
        Map<String,Object> params = new HashMap<>();

        return false;
    }

    @Override
    public boolean delete(int id) {
        Map<String,Object> params = new HashMap<>();
        params.put("id", id);
        return jdbc.update(DELETE_ARTICLE,params) == 1;


    }
}
