package com.spring.course.services;

import com.spring.course.entities.Order;
import com.spring.course.repositories.OrderRepository;
import com.spring.course.services.exceptions.DatabaseException;
import com.spring.course.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElseThrow(() -> new ResourceNotFoundException(id));
    }
    
    public Order insert(Order order) {
        return orderRepository.save(order);
    }
    
    public void deleteById(Long id) {
        try {
            if (orderRepository.existsById(id)) {
                orderRepository.deleteById(id);
            } else {
                throw new ResourceNotFoundException(id);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Order update(Long id, Order newOrder) {
        try {
            Order entity = orderRepository.getReferenceById(id);
            updateData(entity, newOrder);
            return orderRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Order entity, Order newOrder) {
        entity.setMoment(newOrder.getMoment());
        entity.setOrderStatus(newOrder.getOrderStatus());
        entity.setClient(newOrder.getClient());
    }
}
