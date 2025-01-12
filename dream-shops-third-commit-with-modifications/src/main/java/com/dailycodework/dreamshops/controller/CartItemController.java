package com.dailycodework.dreamshops.controller;

import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Cart;
import com.dailycodework.dreamshops.model.User;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.cart.CartItemService;
import com.dailycodework.dreamshops.service.cart.ICartItemService;
import com.dailycodework.dreamshops.service.cart.ICartService;
import com.dailycodework.dreamshops.service.user.IUserService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final IUserService userService;

    private static final Logger logger = LogManager.getLogger(CartItemController.class);

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity,@RequestParam String selectedColor) {
        try {
              User user = userService.getAuthenticatedUser();
              Cart cart= cartService.initializeNewCart(user);
            cartItemService.addItemToCart(cart.getId(), productId, quantity,selectedColor);
            return ResponseEntity.ok(new ApiResponse("Item added to cart successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }catch (JwtException e){
        	logger.error("CartItemController failed "+e.getMessage());
            return  ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/cart/{cartId}/item/{productId}/{selectedColor}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long productId,@RequestParam String selectedColor) {
        try {
            cartItemService.removeItemFromCart(cartId, productId,selectedColor);
            return ResponseEntity.ok(new ApiResponse("Remove Item Success", null));
        } catch (ResourceNotFoundException e) {
        	logger.error("CartItemController failed "+e.getMessage());
        	return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/cart/{cartId}/item/{productId}/update")
    public  ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                           @PathVariable Long productId,
                                                           @RequestParam Integer quantity, @RequestParam String selectedColor) {
        try {
            cartItemService.updateItemQuantity(cartId, productId, quantity,selectedColor);
            return ResponseEntity.ok(new ApiResponse("Update Item Success", null));
        } catch (ResourceNotFoundException e) {
        	logger.error("CartItemController failed "+e.getMessage());
        	return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }
}
