package com.course.client.beans;

import java.util.List;

public class OrderBean {
    private Long id;
    private Double paidTotal;
    private List<OrderItemBean> orders;

    public OrderBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPaidTotal() {
        return paidTotal;
    }

    public void setPaidTotal(Double paidTotal) {
        this.paidTotal = paidTotal;
    }

    public List<OrderItemBean> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderItemBean> orders) {
        this.orders = orders;
    }

    public OrderBean(Long id, Double paidTotal, List<OrderItemBean> orders) {
        this.id = id;
        this.paidTotal = paidTotal;
        this.orders = orders;
    }

    //ajoute l'item à l'order, et actualise le paidTotal
    public void addOrderItem (OrderItemBean orderItemBean){

        this.orders.add(orderItemBean);
        this.paidTotal+=orderItemBean.getPrice() * orderItemBean.getQuantity();

    }


    //Avons nous vrmt besoin de cette fonction du coup?
    /* public void calculPaidTotal(){
        paidTotal= 0.0;
        for (OrderItemBean orderItemBean : orders){
            paidTotal += orderItemBean.getPrice()* orderItemBean.getQuantity();


        }
    }

     */
}
