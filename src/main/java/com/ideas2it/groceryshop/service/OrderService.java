/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.ideas2it.groceryshop.dto.OrderDetailResponseDto;
import com.ideas2it.groceryshop.dto.OrderRequestDto;
import com.ideas2it.groceryshop.dto.OrderResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.NotFoundException;


/**
 * <p>
 *     Provides services to place order directly by using buy now and by cart,
 *     view order by using various filter option and cancel order
 * </p>
 *
 * @author Dhanalakshmi M
 * @version 1.0
 * @since 18-11-2022
 */
public interface OrderService {

    /**
     * <p>
     *     creates order by getting the order Request
     * </p>
     *
     * @param orderRequest       - Contains addressId which helps to place order
     * @return                   - Success message and Status code
     * @throws NotFoundException - If cart not found.
     */
    SuccessResponseDto placeOrder(OrderRequestDto orderRequest) throws NotFoundException;

    /**
     * <p>
     *     Creates order directly without adding it to cart by using direct buy now option
     * </p>
     *
     * @param orderRequest       - Contains quantity, productId, addressId
     * @return                   - Success message and Status code
     * @throws NotFoundException - If Address not found.
     */
    SuccessResponseDto buyNow(OrderRequestDto orderRequest) throws NotFoundException ;

    /**
     * <p>
     *     Gets Order by using orderId
     * </p>
     *
     * @param orderId            - To get order.
     * @return                   - userId, orderedDate, expectedDeliveryDate,
     *                             totalPrice, totalQuantity, orderDetails, isDelivered
     * @throws NotFoundException - If order not found.
     */
    OrderResponseDto viewOrderById(Integer orderId) throws NotFoundException;

    /**
     * <p>
     *     Gets list of active orders
     * </p>
     *
     * @return                   - List of active orders which contains userId,
     *                             orderedDate, expectedDeliveryDate, totalPrice,
     *                             totalQuantity, orderDetails, isDelivered
     * @throws NotFoundException - If order not found.
     */
    List<OrderResponseDto> viewAllActiveOrders() throws NotFoundException;

    /**
     * <p>
     *     Gets list of cancelled orders
     * </p>
     *
     * @return                   - List of cancelled orders which contains userId,
     *                             orderedDate, expectedDeliveryDate, totalPrice,
     *                             totalQuantity, orderDetails, isDelivered
     * @throws NotFoundException - If cancelled orders not found.
     */
    List<OrderResponseDto> viewAllCancelledOrders() throws NotFoundException;

    /**
     * <p>
     *     Gets list of orders of a specific userId
     * </p>
     *
     * @return                   - Contains userId, orderedDate, expectedDeliveryDate,
     *                             totalPrice, totalQuantity, orderDetails, isDelivered
     * @throws NotFoundException - If order not found.
     */
    List<OrderResponseDto> viewOrderByUserId(Integer userId) throws NotFoundException;

    /**
     * <p>
     *     Cancels order of a current user by using orderId
     * </p>
     *
     * @param orderId            - To cancel order
     * @return                   - Success message and Status code
     * @throws NotFoundException - If order not found
     */
    SuccessResponseDto cancelOrderById(Integer orderId) throws NotFoundException;

    /**
     * <p>
     *     Gets list of order of particular product using productId
     * </p>
     *
     * @param productId          - To get list of order placed for specific product
     * @return                   - Contains list of orders of particular product
     *                             which contains categoryName, subCategoryName,
     *                             productName, quantity, price
     * @throws NotFoundException - If order not found.
     */
    List<OrderDetailResponseDto> viewOrdersByProductId(Integer productId) throws NotFoundException;

    /**
     * <p>
     *     Gets list of orders by orderedDate
     * </p>
     *
     * @param orderedDate        - To get orders of particular date
     * @return                   - List of orders which contains userId,
     *                             orderedDate, expectedDeliveryDate, totalPrice,
     *                             totalQuantity, orderDetails, isDelivered
     * @throws NotFoundException - If order not found
     * @throws ParseException    - If date format is invalid
     */
    List<OrderResponseDto> viewOrdersByDate(Date orderedDate) throws NotFoundException;

    /**
     * <p>
     *     Gets list of orders of a specific user
     *     as per mentioned orderedDate and userId
     * </p>
     *
     * @param orderedDate        - To get order of particular date
     * @param userId             - To get order of particular user
     * @return                   - List of order which contains userId,
     *                             orderedDate, expectedDeliveryDate, totalPrice,
     *                             totalQuantity, orderDetails, isDelivered
     * @throws NotFoundException - If order not found
     * @throws ParseException    - If date format is invalid
     */
    List<OrderResponseDto> viewOrdersByIdAndDate(Date orderedDate, Integer userId) throws NotFoundException;
}
