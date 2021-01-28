package com.course.client.beans;

import java.util.List;

public class OrderBean {
    private Long id;
    private Long paidTotal;
    private List<OrderItemBean> orders;

    public OrderBean() {
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

    public List<OrderItemBean> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderItemBean> orders) {
        this.orders = orders;
    }

    public OrderBean(Long id, Long paidTotal, List<OrderItemBean> orders) {
        this.id = id;
        this.paidTotal = paidTotal;
        this.orders = orders;
    }


    public void addOrderItem (OrderItemBean orderItem){
        this.orders.add(orderItem);
    }

}
