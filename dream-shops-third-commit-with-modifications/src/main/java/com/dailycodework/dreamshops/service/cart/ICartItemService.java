package com.dailycodework.dreamshops.service.cart;

import com.dailycodework.dreamshops.model.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity,String selectedColor);
    void removeItemFromCart(Long cartId, Long productId,String selectedColor);
    void updateItemQuantity(Long cartId, Long productId, int quantity,String selectedColor);

    CartItem getCartItem(Long cartId, Long productId,String selectedColor);
}
