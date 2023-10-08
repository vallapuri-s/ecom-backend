package com.thoughtworks.ecombackend.services;

import com.thoughtworks.ecombackend.dto.OrderDto;
import com.thoughtworks.ecombackend.models.Order;
import com.thoughtworks.ecombackend.models.Product;
import com.thoughtworks.ecombackend.models.User;
import com.thoughtworks.ecombackend.repositories.OrderRepository;
import com.thoughtworks.ecombackend.repositories.ProductRepository;
import com.thoughtworks.ecombackend.repositories.UserRepository;
import com.thoughtworks.ecombackend.utils.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(final OrderRepository orderRepository, final UserRepository userRepository,
                        final ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<OrderDto> findAll() {
        final List<Order> orders = orderRepository.findAll(Sort.by("id"));
        return orders.stream()
                .map((order) -> mapToDTO(order, new OrderDto()))
                .collect(Collectors.toList());
    }

    public OrderDto get(final String id) {
        return orderRepository.findById(id)
                .map(order -> mapToDTO(order, new OrderDto()))
                .orElseThrow(() -> new NotFoundException());
    }

    public String create(final OrderDto orderDTO) {
        final Order order = new Order();
        mapToEntity(orderDTO, order);
        return orderRepository.save(order).getId();
    }

    public void update(final String id, final OrderDto orderDTO) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());
        mapToEntity(orderDTO, order);
        orderRepository.save(order);
    }

    public void delete(final String id) {
        orderRepository.deleteById(id);
    }

    private OrderDto mapToDTO(final Order order, final OrderDto orderDTO) {
        orderDTO.setId(order.getId());
        orderDTO.setQuantity(order.getQuantity());
        orderDTO.setUser(order.getUser() == null ? null : order.getUser().getId());
      //  orderDTO.setProduct(order.getProduct() == null ? null : order.getProduct().getId());
        return orderDTO;
    }

    private Order mapToEntity(final OrderDto orderDTO, final Order order) {
        order.setQuantity(orderDTO.getQuantity());
        final User user = orderDTO.getUser() == null ? null : userRepository.findById(orderDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        order.setUser(user);
        final Product product = orderDTO.getProduct() == null ? null : productRepository.findById(orderDTO.getProduct())
                .orElseThrow(() -> new NotFoundException("product not found"));
      //  order.setProduct(product);
        return order;
    }

}
