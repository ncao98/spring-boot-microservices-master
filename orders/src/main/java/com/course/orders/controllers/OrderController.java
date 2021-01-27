package com.course.orders.controllers;

import com.course.orders.domain.Order;
import com.course.orders.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@RestController
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @PostMapping(value = "/order")
    public ResponseEntity<Order> createNewOrder(@RequestBody Order orderData)
    {
        Order order = orderRepository.save(new Order());

        if (order == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't create a new order");

        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }


    @PostMapping(value = "/order/{id}")
    public void addProductToCart(@PathVariable Long id, @RequestBody Order order)
    {
        Order order = orderRepository.getOne(id);

        if (order == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get order");


        cart.addProduct(cartItem);

        cartRepository.save(cart);

        return new ResponseEntity<CartItem>(cartItem, HttpStatus.CREATED);
    }
}

