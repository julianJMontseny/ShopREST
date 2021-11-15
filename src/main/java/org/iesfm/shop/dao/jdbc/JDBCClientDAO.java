package org.iesfm.shop.dao.jdbc;

import org.iesfm.shop.Client;
import org.iesfm.shop.dao.ClientDAO;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class JDBCClientDAO implements ClientDAO {

    private NamedParameterJdbcTemplate jdbc;

    public JDBCClientDAO(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Client> list() {
        return null;
    }

    @Override
    public Client get(int id) {
        return null;
    }

    @Override
    public boolean insert(Client client) {
        return false;
    }

    @Override
    public boolean update(Client client) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
