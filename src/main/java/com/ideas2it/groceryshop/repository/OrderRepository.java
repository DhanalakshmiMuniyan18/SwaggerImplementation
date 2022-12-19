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
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ideas2it.groceryshop.model.OrderDetail;
import com.ideas2it.groceryshop.model.Order;

/**
 * <p>
 *     Provides services like place order, view order and cancel order
 * </p>
 *
 * @author Dhanalakshmi.M
 * @version 1.0
 * @since 18-11-2022
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {

    /**
     * <p>
     *     Retrieves list of active orders
     * </p>
     *
     * @param status - To fetch active or inactive order
     * @return       - List of orders which contains orderedDate, totalPrice,
     *                 totalQuantity, isActive, orderDetails, cart, user, orderDelivery
     */
    List<Order> findByIsActive(Boolean status);

    /**
     * <p>
     *     Retrieves list of order using user id
     * </p>
     *
     * @param userId - To fetch list of order
     * @return Order - List of orders which contains orderedDate, totalPrice, totalQuantity,
     *                 isActive, orderDetails, cart, user, orderDelivery
     */
    List<Order> findByUserId(Integer userId);

    /**
     * <p>
     *     Updates the order status to cancel the order
     * </p>
     *
     * @param orderId  - To cancel order
     * @return Integer - 1 rows affected if order is cancelled successfully
     *                   0 rows affected if order is not cancelled
     */
    @Modifying
    @Transactional
    @Query(value = "update Order o set o.isActive = false where o.id = ?1 and o.user.id = ?2")
    Integer cancelOrderById(Integer orderId, Integer userId);

    /**
     * <p>
     *    Retrieves order by using orderId and userId
     * </p>
     *
     * @param orderId - To fetch order
     * @param userId  - To fetch user order
     * @return        - orderedDate, totalPrice, totalQuantity, isActive,
     *                  orderDetails, cart, user, orderDelivery
     */
    Optional<Order> findByIdAndUserId(Integer orderId, Integer userId);

    /**
     * <p>
     *     Retrieves order by orderId, isActive, userId and isDelivered
     * </p>
     *
     * @param orderId     - To fetch order
     * @param isActive    - To fetch active or inactive order
     * @param userId      - To fetch user order
     * @param isDelivered - To fetch order not delivered
     * @return            - orderedDate, totalPrice, totalQuantity, isActive,
     *                      orderDetails, cart, user, orderDelivery
     */
    Optional<Order> findByIdAndIsActiveAndUserIdAndOrderDeliveryIsDelivered(Integer orderId,
                                                                            Boolean isActive,
                                                                            Integer userId,
                                                                            Boolean isDelivered);

    /**
     * <p>
     *     Retrieves list of order details of specific product
     * </p>
     *
     * @param productId - To fetch list of orders
     * @return          - List of orders which contains quantity, price, product
     */
    @Query(value = "Select o from OrderDetail o where o.product.id = ?1")
    List<OrderDetail> findByProductId(Integer productId);

    /**
     * <p>
     *     Retrieves orders of particular date
     * </p>
     *
     * @param orderedDate - To fetch list of orders
     * @return            - List of orders which contains orderedDate, totalPrice,
     *                      totalQuantity, isActive, orderDetails, cart, user, orderDelivery
     */
    @Query("Select o from Order o where date(o.orderedDate) = ?1")
    List<Order> findByOrderedDate(Date orderedDate);

    /**
     * <p>
     *     Retrieves orders by using userId and orderedDate
     * </p>
     *
     * @param orderedDate - To fetch order
     * @param userId      - To fetch user order
     * @return            - List of orders which contains orderedDate, totalPrice,
     *                      totalQuantity, isActive, orderDetails, cart, user, orderDelivery
     */
    @Query("Select o from Order o where Date(o.orderedDate) = ?1 AND o.user.id = ?2")
    List<Order> findByOrderedDateAndUserId(Date orderedDate, Integer userId);
}
