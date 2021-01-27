package com.course.orders.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private Long paidTotal;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", paidTotal=" + paidTotal +
                '}';
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

    public Order(Long id, Long paidTotal) {
        this.id = id;
        this.paidTotal = paidTotal;
    }

    public Order(){}
    
}
