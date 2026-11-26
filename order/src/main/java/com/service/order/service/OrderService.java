package com.service.order.service;

import com.service.order.model.Customer;
import com.service.order.model.Order;
import com.service.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestClient restClient;

    public OrderService(OrderRepository orderRepository, RestClient restClient) {
        this.orderRepository = orderRepository;
        this.restClient = restClient;
    }

    private Customer fetchCustomerFromService(Long customerId) {
        if (customerId == null) {
            return null;
        }
        return restClient.get()
                .uri("/api/customers/{id}", customerId)
                .retrieve()
                .body(Customer.class);
    }

    public Order createOrder(Order order) {
        Customer customer = fetchCustomerFromService(order.getCustomerId());
        if (customer == null) {
            return null;
        }
        Order savedOrder = orderRepository.save(order);
        savedOrder.setCustomer(customer);
        return savedOrder;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        orders.forEach(order -> order.setCustomer(fetchCustomerFromService(order.getCustomerId())));
        return orders;
    }

    public Order getOrderById(String id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setCustomer(fetchCustomerFromService(order.getCustomerId()));
        }
        return order;
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        Customer customer = fetchCustomerFromService(customerId);
        if (customer == null) {
            return List.of();
        }
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        orders.forEach(order -> order.setCustomer(customer));
        return orders;
    }

    public Customer getCustomerForOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return null;
        }
        return fetchCustomerFromService(order.getCustomerId());
    }
}