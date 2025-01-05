package com.dailycodework.dreamshops.service.order;

import com.dailycodework.dreamshops.dto.OrderDto;
import com.dailycodework.dreamshops.enums.OrderStatus;
import com.dailycodework.dreamshops.model.Order;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
     List<Order> getAllOrders() ;
     List<Order> getOrdersByUserId(Long userId);
     Optional<Order> getOrderById(Long id);
     Order updateOrderStatus(Long id, OrderStatus status);
    OrderDto convertToDto(Order order);
  void  cancelOrder(Long id);
}
