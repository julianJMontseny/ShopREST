package org.iesfm.shop.controller;

import org.iesfm.shop.Article;
import org.iesfm.shop.dao.ArticleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ArticleController {


    private ArticleDAO articleDAO;

    public ArticleController(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/articles")
    public List<Article> list(@RequestParam(name="tag", required = false) String tag) {
        if(tag != null){
            return articleDAO.list(tag);
        } else {
            return articleDAO.list();
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/articles/{id}")
    public Article list(@PathVariable("id") int id) {
        Article article = articleDAO.get(id);
        if (article != null) {
            return article;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el artículo");
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/articles")
    public void insert(@RequestBody Article article) {
        if (!articleDAO.insert(article)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe el artículo");
        } else {
            articleDAO.insert(article);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/articles/{id}")
    public void update(@RequestBody Article article) {
        if (!articleDAO.update(article)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el artículo");
        } else {
            articleDAO.update(article);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE,path = "/articles/{id}")
    public void delete(@PathVariable("id") int id){
        if(!articleDAO.delete(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el artículo");
        } else {
            articleDAO.delete(id);
        }
    }


}
