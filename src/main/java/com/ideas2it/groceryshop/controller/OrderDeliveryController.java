/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import lombok.RequiredArgsConstructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.ideas2it.groceryshop.dto.OrderDeliveryResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.OrderDeliveryService;

/**
 * <p>
 *     Provides Apis like get order and update the delivery status
 * </p>
 *
 * @author   Dhanalakshmi M
 * @version  1.0
 * @since    18-11-2022
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderDeliveryController {
    private final OrderDeliveryService orderDeliveryService;
    private final Logger logger = LogManager.getLogger(OrderDeliveryController.class);

    /**
     * <p>
     *     Retrieves the Delivery Order by using orderId
     * </p>
     *
     * @param orderId            - To pick order
     * @return                   - userId, orderId, totalPrice, totalQuantity,
     *                             shippingAddress, orderStatus
     * @throws NotFoundException - If order not found
     */
    @GetMapping("/{orderId}/orderDelivery")
    public OrderDeliveryResponseDto getDeliveryOrder(@PathVariable Integer orderId)
                                                     throws NotFoundException {
        logger.debug("Entered getDeliveryOrder method in OrderController");
        return orderDeliveryService.getDeliveryOrder(orderId);
    }

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
    @PutMapping("/{orderId}/statusUpdate")
    public SuccessResponseDto statusUpdate(@PathVariable Integer orderId) throws ExistedException,
                                           NotFoundException {
        logger.debug("Entered statusUpdate method in OrderController");
        return orderDeliveryService.statusUpdate(orderId);
    }
}
