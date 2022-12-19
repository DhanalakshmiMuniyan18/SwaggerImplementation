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

/**
 * <p>
 *      It holds the OrderRequestDto information(like quantity, productId, addressId) and
 *      also it is used to get order details.
 * </p>
 *
 * @author   Dhanalakshmi M
 * @version  1.0
 * @since    18-11-2022
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

    private Integer quantity;

    private Integer productId;

    private Integer addressId;
}
