package org.iesfm.shop.dao;


import org.iesfm.shop.dao.inmemory.InMemoryArticleDAO;
import org.iesfm.shop.dao.inmemory.InMemoryClientDAO;
import org.iesfm.shop.dao.inmemory.InMemoryOrderDAO;
import org.iesfm.shop.dao.jdbc.JDBCArticleDAO;
import org.iesfm.shop.dao.jdbc.JDBCClientDAO;
import org.iesfm.shop.dao.jdbc.JDBCOrderDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
//@PropertySource("shop.properties")
public class ShopConfiguration {

//    @Bean
//    public NamedParameterJdbcTemplate jdbc(DataSource dataSource) {
//        return new NamedParameterJdbcTemplate(dataSource);
//    }
    // Estan comentados porque no estamos utilizando Queries. Ni JDBC. Estamos usando InMemory.

    @Bean
    public ArticleDAO inMemoryArticleDAO(){
        return new InMemoryArticleDAO();
    }

    @Bean
    public ClientDAO inMemoryClientDAO(){
        return new InMemoryClientDAO();
    }

    @Bean
    public OrderDAO inMemoryOrderDAO(){
        return new InMemoryOrderDAO();
    }

//    @Bean
//    public ArticleDAO articleDAO(NamedParameterJdbcTemplate jdbc) {
//        return new JDBCArticleDAO(jdbc);
//    }
//
//    @Bean
//    public ClientDAO clientDAO(NamedParameterJdbcTemplate jdbc){
//        return new JDBCClientDAO(jdbc);
//    }
//
//    @Bean
//    public OrderDAO orderDAO(NamedParameterJdbcTemplate jdbc){
//        return new JDBCOrderDAO(jdbc);
//    }

//    @Bean
//    public DataSource dataSource(@Value("${database.url}") String url,
//                                 @Value("${database.username}") String username,
//                                 @Value("${database.password}") String password) {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setUrl(url);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//        return dataSource;
//    }


}
