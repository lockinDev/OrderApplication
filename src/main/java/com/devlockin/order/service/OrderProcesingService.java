package com.devlockin.order.service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devlockin.order.entity.Order;
import com.devlockin.order.exception.OrderNotFoundException;

@RestController
@RequestMapping("/orders")
public class OrderProcesingService {

	private Map<String, Order> orders = new HashMap<>();
	@Value("${inventory.service}")
	private String inventoryURL;

	@PostMapping
	public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
		if (order != null) {
			
			System.out.println(order);
			
			RestTemplate restTemplate = new RestTemplate();
			URI uri = URI.create(inventoryURL);
			restTemplate.put(uri, order.getItems());

			order.setOrderId(UUID.randomUUID().toString());
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(order.getOrderId()).toUri();

			return ResponseEntity.created(location).build();
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Order> getOrder(@PathVariable String id) throws OrderNotFoundException {

		if (orders.containsKey(id)) {
			return new ResponseEntity<Order>(orders.get(id), HttpStatus.OK);
		} else {
			throw new OrderNotFoundException();
		}
	}

}