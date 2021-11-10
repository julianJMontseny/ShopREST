package org.iesfm.shop.dao;

import org.iesfm.shop.controller.ArticleController;
import org.iesfm.shop.dao.inmemory.InMemoryArticleDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShopConfiguration {

    @Bean
    public ArticleDAO articleDAO(){
        return new InMemoryArticleDAO();
    }

}
