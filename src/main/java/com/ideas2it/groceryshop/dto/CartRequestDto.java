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
 *     It holds the CartRequest information(like CartDetails etc.,) and
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
public class CartRequestDto {

    @NotNull(message = "Cart details cannot be empty")
    private CartDetailRequestDto cartDetail;
}
