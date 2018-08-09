package com.matthewz.objectboxdemo1.entity;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class Customer {
    @Id
    private long id;

    private String name;

    @Backlink
    private ToMany<Order> orderList;

    public Customer(String name) {
        this.name = name;
        orderList = new ToMany<>(this, Customer_.orderList);
    }

    public Customer() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ToMany<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(ToMany<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Customer{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", orderList=");
        if(orderList.isEmpty()) {
            sb.append("is empty");
        }else {
            for (Order order : orderList) {
                sb.append(order);
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
