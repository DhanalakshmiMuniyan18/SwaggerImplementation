/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * <p>
 *     It holds the CartDetailRequest information(like productId, quantity etc.,) and
 *     also it is used to get product details from user to add into the cart
 * </p>
 *
 * @author Mohammed Ammar
 * @since 03-11-2022
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartDetailRequestDto {

    @NotNull(message = "Product id cannot not be empty")
    private Integer productId;

    @NotNull(message = "Quantity cannot not be empty")
    private Integer quantity;
}
