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
 *      It holds the OrderDeliveryResponseDto information(like userId, orderId,
 *      totalPrice, totalQuantity, shippingAddress, orderStatus) and also it is
 *      used to view order delivery as response to user.
 * </p>
 *
 * @author   Dhanalakshmi M
 * @version  1.0
 * @since    18-11-2022
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDeliveryResponseDto {

    private Integer userId;

    private Integer orderId;

    private Float totalPrice;

    private Integer totalQuantity;

    private AddressResponseDto shippingAddress;

    private Boolean orderStatus;
}

