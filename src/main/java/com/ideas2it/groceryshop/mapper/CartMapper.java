/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.CartDetailResponseDto;
import com.ideas2it.groceryshop.dto.CartRequestDto;
import com.ideas2it.groceryshop.dto.CartResponseDto;
import com.ideas2it.groceryshop.model.Cart;
import com.ideas2it.groceryshop.model.CartDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Converts Cart Entity to Cart DTO and vice versa
 * </p>
 *
 * @author Mohammed Ammar
 * @since 02-11-2022
 * @version 1.0
 */
public class CartMapper {

    /**
     * <p>
     *     Converts Cart Entity to CartResponse
     * </p>
     *
     * @param cart - Contains list of cart details, total price
     * @return     - Cart details like product details, total price etc
     */
    public static CartResponseDto toCartResponse(Cart cart) {
        CartResponseDto cartResponse = new CartResponseDto();
        cartResponse.setId(cart.getId());
        cartResponse.setTotalPrice(cart.getTotalPrice());
        cartResponse.setCreatedAt(cart.getCreatedAt());
        List<CartDetailResponseDto> cartDetailsResponse = new ArrayList<>();
        for (CartDetail cartDetail : cart.getCartDetails()) {
            cartDetailsResponse.add(CartDetailMapper.toCartDetailResponse(cartDetail));
        }
        cartResponse.setCartDetails(cartDetailsResponse);
        return cartResponse;
    }

    /**
     * <p>
     *     Converts cartRequest to Cart entity
     * </p>
     *
     * @param cartRequest - Contains product id and quantity
     * @return            - Cart contains cart details
     */
    public static Cart toCart(CartRequestDto cartRequest) {
        Cart cart = new Cart();
        cart.setTotalQuantity(cartRequest.getCartDetail().getQuantity());
        cart.setCartDetails(new ArrayList<>());
        return cart;
    }
}
