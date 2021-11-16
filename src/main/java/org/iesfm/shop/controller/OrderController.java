package org.iesfm.shop.controller;

import org.iesfm.shop.Client;
import org.iesfm.shop.Order;
import org.iesfm.shop.dao.OrderDAO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

    @RequestMapping(method = RequestMethod.GET, path = "/clients/{id}/orders")
    public List<Order> getOrders(@PathVariable("id") int idCliente){
        int orderClientId = 0;
        Client client = clientController.getClient(idCliente);
        orderClientId = client.getId();
        return orderDAO.list(orderClientId);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/orders")
    public void createOrder(@RequestBody Order order){
        orderDAO.insert(order);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/clients/{idCliente}/orders/{idOrder}")
    public void updateOrder(@PathVariable("idCliente")int idClient,@PathVariable("idArticle") int idArticle, @PathVariable("idOrder") int idOrder, @RequestBody Order order){
        clientController.getClient(idClient);
        articleController.list(idArticle);
        if(!orderDAO.update(order)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Order not found");
        } else {
            orderDAO.update(order);
        }
    }





}
