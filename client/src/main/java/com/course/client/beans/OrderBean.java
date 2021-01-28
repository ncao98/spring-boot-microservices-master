package com.course.client.beans;

import java.util.List;

public class OrderBean {
    private Long id;
    private Long paidTotal;
    private List<OrderItem> orders;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPaidTotal() {
        return paidTotal;
    }

    public void setPaidTotal(Long paidTotal) {
        this.paidTotal = paidTotal;
    }

    public List<OrderItem> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderItem> orders) {
        this.orders = orders;
    }

    public Order(Long id, Long paidTotal, List<OrderItem> orders) {
        this.id = id;
        this.paidTotal = paidTotal;
        this.orders = orders;
    }


    public void addOrderItem (OrderItem orderItem){
        this.orders.add(orderItem);
    }

}
