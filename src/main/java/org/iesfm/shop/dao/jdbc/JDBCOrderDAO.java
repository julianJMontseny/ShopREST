package org.iesfm.shop.dao.jdbc;

import org.iesfm.shop.Order;
import org.iesfm.shop.dao.OrderDAO;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class JDBCOrderDAO implements OrderDAO {

    private NamedParameterJdbcTemplate jdbc;

    public JDBCOrderDAO(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Order> list() {
        return null;
    }

    @Override
    public List<Order> list(int clientId) {
        return null;
    }

    @Override
    public Order get(int orderId) {
        return null;
    }

    @Override
    public boolean insert(Order order) {
        return false;
    }

    @Override
    public boolean update(Order order) {
        return false;
    }

    @Override
    public boolean delete(int orderId) {
        return false;
    }
}
