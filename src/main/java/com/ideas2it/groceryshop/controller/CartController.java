/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import lombok.RequiredArgsConstructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import com.ideas2it.groceryshop.dto.CartRequestDto;
import com.ideas2it.groceryshop.dto.CartResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.CartService;

/**
 * <p>
 *     Provides APIs to add, update, view, delete products
 *     from and to cart based on currently logged-in user
 * </p>
 *
 * @author Mohammed Ammar
 * @since 05-11-2022
 * @version 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user/carts")
public class CartController {
    private final Logger logger = LogManager.getLogger(CartController.class);
    private final CartService cartService;

    /**
     * <p>
     *     Creates cart or updates an existing cart if cart is inactive for currently
     *     logged-in user with given product details
     * </p>
     *
     * @param cartRequest        - Contains product id and quantity to add into cart
     * @return                   - Success message and status Code
     * @throws NotFoundException - If product not found
     * @throws ExistedException  - If product already exist in cart
     */
    @PostMapping
    public SuccessResponseDto createOrUpdateCart(@Valid @RequestBody CartRequestDto cartRequest)
                                                 throws NotFoundException, ExistedException {
        logger.debug("Entered createCart method in CartController");
        return cartService.addOrModifyCart(cartRequest);
    }

    /**
     * <p>
     *     View the cart of currently logged-in user
     * </p>
     *
     * @return                   - Product details as cart detail, it's total price
     * @throws NotFoundException - If cart is inactive or not found
     */
    @GetMapping
    public CartResponseDto viewCart() throws NotFoundException {
        logger.debug("Entered viewCart method in CartController");
        return cartService.getCart();
    }

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
    @PutMapping
    public SuccessResponseDto updateCart(@Valid @RequestBody CartRequestDto cartRequest)
                                         throws NotFoundException {
        logger.debug("Entered updateCart method in CartController");
        return cartService.updateCart(cartRequest);
    }

    /**
     * <p>
     *     Removes all products from cartDetails and to delete cart
     *     based on currently logged-in user detail
     * </p>
     *
     * @return - Success message and status code
     */
    @DeleteMapping
    public SuccessResponseDto deleteCart() throws NotFoundException {
        logger.debug("Entered deleteCart method in CartController");
        return cartService.removeCart();
    }

    /**
     * <p>
     *     Removes particular product from cartDetails based on given product id
     *     and to update cart with new calculated price
     * </p>
     *
     * @param productId          - Product's id to remove product from cart
     * @return                   - Success message and status code
     * @throws NotFoundException - If cart or given product id not found
     */
    @DeleteMapping("/products/{productId}")
    public SuccessResponseDto deleteProductFromCart(@PathVariable Integer productId)
                                                    throws NotFoundException {
        logger.debug("Entered deleteProductFromCart method in CartController");
        return cartService.removeProductFromCart(productId);
    }
}