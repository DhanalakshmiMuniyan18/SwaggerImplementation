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
 *      It holds the OrderDeliveryRequestDto information(like isDelivered, orderId,
 *      shippingAddressId) and also it is used to get order delivery details.
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
public class OrderDeliveryRequestDto {

    private Boolean isDelivered;

    private Integer order_id;

    private Integer shippingAddressId;
}
