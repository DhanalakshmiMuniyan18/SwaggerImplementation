/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.OrderDeliveryResponseDto;
import com.ideas2it.groceryshop.model.OrderDelivery;

/**
 * <p>
 *     Converts entity to dto and vice versa
 * </p>
 *
 * @author   Dhanalakshmi.M
 * @version  1.0
 * @since    18-11-2022
 */
public class OrderDeliveryMapper {

    /**
     * <p>
     *     Converts orderDelivery Entity to OrderDeliveryResponseDto
     * </p>
     *
     * @param orderDelivery - Contains userId, orderId, shippingAddress, orderStatus,
     *                        totalPrice, totalQuantity
     * @return              - userId, orderId, totalPrice, shippingAddress,
     *                        orderStatus, totalQuantity
     */
    public static OrderDeliveryResponseDto toOrderDeliveryDto(OrderDelivery orderDelivery) {
        OrderDeliveryResponseDto orderDeliveryResponse = new OrderDeliveryResponseDto();
        orderDeliveryResponse.setOrderId(orderDelivery.getOrder().getId());
        orderDeliveryResponse.setShippingAddress(AddressMapper.addressResponseDto
                                                (orderDelivery.getShippingAddress()));
        orderDeliveryResponse.setUserId(orderDelivery.getOrder().getUser().getId());
        orderDeliveryResponse.setOrderStatus(orderDelivery.getOrder().getIsActive());
        orderDeliveryResponse.setTotalPrice(orderDelivery.getOrder().getTotalPrice());
        orderDeliveryResponse.setTotalQuantity(orderDelivery.getOrder().getTotalQuantity());
        return orderDeliveryResponse;
    }
}
