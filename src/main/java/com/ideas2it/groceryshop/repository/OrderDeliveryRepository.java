/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

import com.ideas2it.groceryshop.model.OrderDelivery;

/**
 * <p>
 *     Provides services like get order and update order status
 * </p>
 *
 * @author Dhanalakshmi.M
 * @version 1.0
 * @since 18-11-2022
 */
public interface OrderDeliveryRepository extends JpaRepository<OrderDelivery, Integer> {

    /**
     * <p>
     *     Retrieves order using orderId
     * </p>
     *
     * @param orderId - To fetch order
     * @return        - isDelivered, deliveryDate,
     *                  expectedDeliveryDate, userOrder, shippingAddress
     */
    OrderDelivery findByOrderId(Integer orderId);

    /**
     * <p>
     *     updates the delivery status and delivery date
     * </p>
     *
     * @param orderId - To update delivery status
     */
    @Modifying
    @Transactional
    @Query("Update OrderDelivery od set od.isDelivered = true, od.deliveryDate = CURRENT_TIMESTAMP " +
            "where od.id = (select o.orderDelivery.id from Order o where o.id = ?1)")
    void updateStatus(Integer orderId);
}
