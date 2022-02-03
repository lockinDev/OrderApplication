package com.lockinDev.inventory.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/inventory")
public class InventoryService {

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> updateItems(@RequestBody Item[] items) {

		if (items == null || items.length == 0) {
			return ResponseEntity.badRequest().build();
		}
		for (Item item : items) {
			if (item != null) {
				System.out.println(item.getCode());
			}
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}