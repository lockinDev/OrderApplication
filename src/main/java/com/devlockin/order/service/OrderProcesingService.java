package com.devlockin.order.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devlockin.order.entity.Order;
import com.devlockin.order.exception.OrderNotFoundException;



@RestController
@RequestMapping("/orders")
public class OrderProcesingService {

    private Map<String, Order> orders = new HashMap<>();

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        String orderId = UUID.randomUUID().toString();
        order.setOrderId(orderId);
        orders.put(orderId, order);
        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable String id) throws OrderNotFoundException {

        if(orders.containsKey(id)){
            return new ResponseEntity<Order>(orders.get(id), HttpStatus.OK);
        }
        else {
            throw new OrderNotFoundException();
        }
    }
    

    
}