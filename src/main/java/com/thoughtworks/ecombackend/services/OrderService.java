package com.thoughtworks.ecombackend.services;

import com.thoughtworks.ecombackend.dto.LineItemDto;
import com.thoughtworks.ecombackend.dto.OrderDto;
import com.thoughtworks.ecombackend.models.LineItem;
import com.thoughtworks.ecombackend.models.Order;
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

    public OrderDto get(final Long id) {
        return orderRepository.findById(id)
                .map(order -> mapToDTO(order, new OrderDto()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final OrderDto orderDTO) {
        final Order order = new Order();
        mapToEntity(orderDTO, order);
        return orderRepository.save(order).getId();
    }

    public void update(final Long id, final OrderDto orderDTO) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderDTO, order);
        orderRepository.save(order);
    }

    public void delete(final Long id) {
        orderRepository.deleteById(id);
    }

    private OrderDto mapToDTO(final Order order, final OrderDto orderDTO) {
        orderDTO.setId(order.getId());
        orderDTO.setUserId(order.getUser().getId());
        orderDTO.setLineItems(mapLineItemsToDTO(order));
        return orderDTO;
    }

    private void mapToEntity(final OrderDto orderDTO, final Order order) {
        final User user = orderDTO.getUserId() == null ? null : userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("user not found"));
        order.setUser(user);
        order.setLineItems(mapLineItemsToEntity(orderDTO, order));
    }

    // write method to map line items from Order to OrderDto object and return it
    private List<LineItemDto> mapLineItemsToDTO(Order order) {
        return order.getLineItems().stream().map(lineItem -> new LineItemDto(
                lineItem.getId(),
                lineItem.getProduct().getId(),
                lineItem.getQuantity())).collect(Collectors.toList());
    }

    private List<LineItem> mapLineItemsToEntity(OrderDto orderDto, Order order) {
        return orderDto.getLineItems().stream().map(lineItem -> new LineItem(
                        lineItem.getId(),
                        lineItem.getQuantity(),
                        lineItem.getProductId() == null ? null : productRepository.findById(lineItem.getProductId())
                                .orElseThrow(() -> new NotFoundException("product not found")),
                        order))
                .collect(Collectors.toList());
    }
}
