package com.spring.course.resources;

import com.spring.course.entities.OrderItem;
import com.spring.course.services.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/orderitem")
public class OrderItemResource {

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public ResponseEntity<List<OrderItem>> findAll() {
        List<OrderItem> allOrdersItem = orderItemService.findAll();
        return ResponseEntity.ok().body(allOrdersItem);
    }
}
