package com.dailycodework.dreamshops.controller;

import com.dailycodework.dreamshops.dto.OrderDto;
import com.dailycodework.dreamshops.enums.OrderStatus;
import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Order;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.order.IOrderService;
import com.dailycodework.dreamshops.service.payment.PaymentInfo;
import com.dailycodework.dreamshops.service.payment.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;
    private final PaymentService paymentService;
    private static final Logger logger = LogManager.getLogger(OrderController.class);

    @PostMapping("/user/place-order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
        try {
            Order order =  orderService.placeOrder(userId);
            OrderDto orderDto =  orderService.convertToDto(order);
            return ResponseEntity.ok(new ApiResponse("Items Order Success!", orderDto));
        } catch (Exception e) {
        	logger.error("OrderController failed "+e.getMessage());
        	return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error occurred!", e.getMessage()));
        }
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
        try {
            OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));
        } catch (ResourceNotFoundException e) {
        	logger.error("OrderController failed "+e.getMessage());
        	return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Oops!", e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}/order")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
        try {
            List<OrderDto> order = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));
        } catch (ResourceNotFoundException e) {
        	logger.error("OrderController failed "+e.getMessage());
        	return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Oops!", e.getMessage()));
        }
    }

    @PostMapping("/payment/create-payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo) throws StripeException {
        try {
            System.out.println("The body :" +paymentInfo);
            PaymentIntent paymentIntent = paymentService.createPaymentIntent(paymentInfo);
            String paymentString = paymentIntent.toJson();
            System.out.println("The payment string :" + paymentString);
            return ResponseEntity.ok(paymentString);
        } catch (StripeException e) {
        	logger.error("OrderController failed "+e.getMessage());
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @GetMapping("/user/getAllOrders")
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }
  
    @GetMapping("/user/{userId}")
    public List<OrderDto> getOrdersByUserId(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));           
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }
     
}
