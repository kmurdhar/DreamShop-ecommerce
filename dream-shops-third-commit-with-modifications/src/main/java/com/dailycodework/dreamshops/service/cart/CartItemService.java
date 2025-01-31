package com.dailycodework.dreamshops.service.cart;

 import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Cart;
import com.dailycodework.dreamshops.model.CartItem;
import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.repository.CartItemRepository;
import com.dailycodework.dreamshops.repository.CartRepository;
import com.dailycodework.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService  implements ICartItemService{
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final IProductService productService;
    private final ICartService cartService;
    private static final Logger logger = LogManager.getLogger(CartItemService.class);

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity,String selectedColor) {
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        System.out.println("\n\n=====================================================================================");
        System.out.println("The product Id:" + productId);
        System.out.println("The product:" + product);
        System.out.println("\n\n=====================================================================================");

        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId) && item.getSelectedColor().equalsIgnoreCase(selectedColor)  )
                .findFirst().orElse(new CartItem());
        
        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
            cartItem.setSelectedColor(selectedColor);
        }
        else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId,String selectedColor) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId,selectedColor);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity,String selectedColor) {
        Cart cart = cartService.getCart(cartId);
        cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId)  &&  item.getSelectedColor().equalsIgnoreCase(selectedColor))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });
        BigDecimal totalAmount = cart.getItems()
                .stream().map(CartItem ::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId,String selectedColor) {
        Cart cart = cartService.getCart(cartId);
        return  cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId) &&  item.getSelectedColor().equalsIgnoreCase(selectedColor) )
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    }
}
