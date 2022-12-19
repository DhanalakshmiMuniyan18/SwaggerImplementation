/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.OrderDeliveryResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;

/**
 * <p>
 *    Provides services like get order and update delivery status
 * </p>
 *
 * @author Dhanalakshmi.M
 * @version 1.0
 * @since 18-11-2022
 */
public interface OrderDeliveryService {

    /**
     * <p>
     *     Updates delivery status once the order is delivered
     * </p>
     *
     * @param orderId            - To update delivery status
     * @return                   - Success message and Status code
     * @throws NotFoundException - If order not found.
     * @throws ExistedException  - If order already delivered
     */
    SuccessResponseDto statusUpdate(Integer orderId) throws ExistedException, NotFoundException;

    /**
     * <p>
     *     Gets the DeliveryOrder using orderId
     * </p>
     *
     * @param orderId            - To pick order
     * @return                   - Contains userId, orderId, totalPrice, totalQuantity,
     *                             shippingAddress, orderStatus
     * @throws NotFoundException - If order not found
     */
    OrderDeliveryResponseDto getDeliveryOrder(Integer orderId) throws NotFoundException;
}
