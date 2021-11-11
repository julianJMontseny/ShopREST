package org.iesfm.shop;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Order {
    private int id;
    private int clientId;
    private Date date;

    @JsonCreator
    public Order(
            @JsonProperty("id") int id,
            @JsonProperty("id")int clientId,
            @JsonProperty("id")Date date) {
        this.id = id;
        this.clientId = clientId;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && clientId == order.clientId && Objects.equals(date, order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, date);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", date=" + date +
                '}';
    }
}
