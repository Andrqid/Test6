package com.matthewz.objectboxdemo1.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Order {

    @Id
    private long id;

    private String goodName;

    private ToOne<Customer> customer;

    public Order() {
    }

    public Order(String goodName) {
        this.goodName = goodName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public ToOne<Customer> getCustomer() {
        return customer;
    }

    public void setCustomer(ToOne<Customer> customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("id=").append(id);
        sb.append(", goodName='").append(goodName).append('\'');
        sb.append(", customer=").append(customer == null || customer.getTarget() == null ? "null null" : customer.getTarget().getName());
        sb.append('}');
        return sb.toString();
    }
}
