package org.iesfm.shop.controller;

import org.iesfm.shop.Client;
import org.iesfm.shop.dao.ClientDAO;
import org.iesfm.shop.dao.inmemory.InMemoryClientDAO;
import org.iesfm.shop.dao.jdbc.JDBCClientDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@PropertySource("shop.properties")
public class ClientController {

    private ClientDAO clientDAO;

    public ClientController(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/clients")
    public List<Client> getAllClients() {
        return clientDAO.list();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/clients/{id}")
    public Client getClient(@PathVariable("id") int id) {
        Client client = clientDAO.get(id);
        if (client != null) {
            return client;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/clients")
    public void createClient(@RequestBody Client client) {
        if (!clientDAO.insert(client)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Client already exists");
        } else {
            clientDAO.insert(client);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/clients/{id}")
    public void updateClient(@RequestBody Client client) {
        if (!clientDAO.update(client)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
        } else {
            clientDAO.update(client);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/clients/{id}")
    public void deleteClient(@PathVariable("id") int id) {
        if (clientDAO.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
        } else {
            clientDAO.delete(id);
        }
    }
}
