package org.iesfm.shop.controller;

import org.iesfm.shop.Order;
import org.iesfm.shop.dao.OrderDAO;
import org.iesfm.shop.dao.inmemory.InMemoryOrderDAO;
import org.iesfm.shop.dao.jdbc.JDBCOrderDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class OrderController {

    private OrderDAO orderDAO;
    private ArticleController articleController;
    private ClientController clientController;

    public OrderController(OrderDAO orderDAO, ArticleController articleController, ClientController clientController) {
        this.orderDAO = orderDAO;
        this.articleController = articleController;
        this.clientController = clientController;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/orders")
    public List<Order> getOrders(){
        return orderDAO.list();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/orders")
    public void createOrder(int idClient, int idArticle, @RequestBody Order order){
        articleController.list(idArticle);
        clientController.getClient(idClient);
        orderDAO.insert(order);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/clients/{idCliente}/orders/{idOrder}")
    public void updateOrder( int idClient, int idArticle, @RequestBody Order order){
        clientController.getClient(idClient);
        articleController.list(idArticle);
        if(!orderDAO.update(order)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Order not found");
        } else {
            orderDAO.update(order);
        }
    }





}
