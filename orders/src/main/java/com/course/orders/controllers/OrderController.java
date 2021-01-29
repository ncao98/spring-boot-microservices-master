package com.course.orders.controllers;

import com.course.orders.domain.Orderz;
import com.course.orders.domain.OrderItem;
import com.course.orders.repositories.OrderItemRepository;
import com.course.orders.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @PostMapping(value = "/order")
    public ResponseEntity<Orderz> createNewOrder(@RequestBody Orderz orderData)
    {
        Orderz orderz = orderRepository.save(new Orderz());

        if (orderz == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't create a new order");

        return new ResponseEntity<Orderz>(orderz, HttpStatus.CREATED);
    }



    @GetMapping(value = "/order/{id}")
    public Optional<Orderz> getOrder(@PathVariable Long id)
    {
        Optional<Orderz> orderz = orderRepository.findById(id);

        if (orderz == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get order");

        return orderz;
    }

    @PostMapping(value = "/order/{id}")
    @Transactional
    public ResponseEntity<OrderItem> addOrderItemToOrder(@PathVariable Long id, @RequestBody OrderItem orderItem)
    {
        Orderz orderz = orderRepository.getOne(id);

        if (orderz == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get order");


        orderz.addOrderItem(orderItem);

        orderRepository.save(orderz);

        return new ResponseEntity<>(orderItem, HttpStatus.CREATED);
    }
}

