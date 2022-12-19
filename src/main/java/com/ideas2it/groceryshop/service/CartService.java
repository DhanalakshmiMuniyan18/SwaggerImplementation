/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.CartRequestDto;
import com.ideas2it.groceryshop.dto.CartResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.model.Cart;

/**
 * <p>
 *     Provides services to add, update, view, delete products
 *     from and to cart based on currently logged-in user
 * </p>
 *
 * @author Mohammed Ammar
 * @since 05-11-2022
 * @version 1.0
 */
public interface CartService {

    /**
     * <p>
     *     Creates new cart if cart is inactive else update
     *     the cart based on given product details
     * </p>
     *
     * @param cartRequest        - Contains product id and quantity to add into cart
     * @return                   - Success message and status Code
     * @throws NotFoundException - If product not found
     * @throws ExistedException  - If product already exist in cart
     */
    SuccessResponseDto addOrModifyCart(CartRequestDto cartRequest) throws NotFoundException,
                                                                          ExistedException;

    /**
     * <p>
     *     Gets the cart of currently logged-in user
     * </p>
     *
     * @return CartResponse      - Contains product details as cart detail, it's total price
     * @throws NotFoundException - If cart is inactive or not found
     */
    CartResponseDto getCart() throws NotFoundException;

    /**
     * <p>
     *     Removes all products from cartDetail and to remove cart
     *     based on currently logged-in user detail
     * </p>
     *
     * @return                   - Success message and status code
     * @throws NotFoundException - If cart not found
     */
    SuccessResponseDto removeCart() throws NotFoundException;

    /**
     * <p>
     *     Removes particular product from cartDetails based on given product id
     *     and to update cart with new calculated price
     * </p>
     *
     * @param productId          - To remove product from cart
     * @return                   - Success message and status code
     * @throws NotFoundException - If cart or given product id not found
     */
    SuccessResponseDto removeProductFromCart(Integer productId) throws NotFoundException;

    /**
     * <p>
     *     Updates quantity of particular product and to update
     *     new calculated price in active cart of currently logged-in user
     * </p>
     *
     * @param cartRequest        - Contains product id and quantity to add into cart
     * @return                   - Success message and status code
     * @throws NotFoundException - If cart or given product id not found
     */
    SuccessResponseDto updateCart(CartRequestDto cartRequest) throws NotFoundException;

    /**
     * <p>
     *     Gets active cart of currently logged-in user
     * </p>
     *
     * @return - Contains product details and it's total price
     */
    Cart getActiveCartOfCurrentUser();
}
