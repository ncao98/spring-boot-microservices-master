package com.course.orders.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private Double paidTotal;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> orders;

    public Order() {
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

    public List<OrderItem> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderItem> orders) {
        this.orders = orders;
    }

    public Order(Long id, Double paidTotal, List<OrderItem> orders) {
        this.id = id;
        this.paidTotal = paidTotal;
        this.orders = orders;
    }

    //ajoute l'item à l'order, et actualise le paidTotal
    public void addOrderItem (OrderItem orderItem){

        this.orders.add(orderItem);
        this.paidTotal += orderItem.getPrice()* orderItem.getQuantity();
    }


    //Avons nous vrmt besoin de cette fonction du coup?
    /* public void calculPaidTotal(){
        paidTotal= 0.0;
        for (OrderItem orderItem : orders){
            paidTotal += orderItem.getPrice()* orderItem.getQuantity();


        }
    }

     */
}
