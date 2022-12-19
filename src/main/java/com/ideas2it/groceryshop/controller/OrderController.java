/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import com.ideas2it.groceryshop.dto.OrderDetailResponseDto;
import com.ideas2it.groceryshop.dto.OrderRequestDto;
import com.ideas2it.groceryshop.dto.OrderResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.OrderService;
import com.ideas2it.groceryshop.service.impl.UserServiceImpl;

/**
 * <p>
 *     Provides Apis to place order directly by using buy now and by cart option,
 *     view order by using various filter option and cancel order
 * </p>
 *
 * @author   Dhanalakshmi M
 * @version  1.0
 * @since    18-11-2022
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserServiceImpl userService;
    private final Logger logger = LogManager.getLogger(OrderController.class);

    /**
     * <p>
     *     creates an order by getting the order request.
     * </p>
     *
     * @param orderRequest       - Contains addressId, which aids in order placement
     * @return                   - Success message and Status code
     * @throws NotFoundException - If cart not found.
     */
    @PostMapping("/placeOrder")
    public SuccessResponseDto placeOrder(@RequestBody OrderRequestDto orderRequest)
                                         throws NotFoundException {
        logger.debug("Entered placeOrder method in OrderController");
        return orderService.placeOrder(orderRequest);
    }

    /**
     * <p>
     *     Creates order directly without adding it to cart by using direct buy now option
     * </p>
     *
     * @param orderRequest        - quantity, productId, addressId
     * @return                    - Success message and Status code
     * @throws NotFoundException  - If Address not found.
     */
    @PostMapping("/buyNow")
    public SuccessResponseDto buyNow(@RequestBody OrderRequestDto orderRequest) throws NotFoundException {
        logger.debug("Entered buyNow method in OrderController");
        return orderService.buyNow(orderRequest);
    }

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
    @GetMapping("/activeOrders")
    public List<OrderResponseDto> viewAllActiveOrders() throws NotFoundException {
        logger.debug("Entered viewAllActiveOrders method in OrderController");
       return orderService.viewAllActiveOrders();
    }

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
    @GetMapping("/cancelledOrders")
    public List<OrderResponseDto> viewAllCancelledOrders() throws NotFoundException {
        logger.debug("Entered viewAllCancelledOrders method in OrderController");
        return orderService.viewAllCancelledOrders();
    }

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
    @GetMapping("/{orderId}")
    public OrderResponseDto viewOrderById(@PathVariable Integer orderId) throws NotFoundException {
        logger.debug("Entered viewOrderById method in OrderController");
        return orderService.viewOrderById(orderId);
    }

    /**
     * <p>
     *     Gets list of orders of specific user
     * </p>
     *
     * @return                   - Contains list of orders which contains
     *                             userId, orderedDate, expectedDeliveryDate,
     *                             totalPrice, totalQuantity, orderDetails, isDelivered
     * @throws NotFoundException - If order not found.
     */
    @GetMapping
    public List<OrderResponseDto> viewOrderByUserId() throws NotFoundException {
        Integer userId = userService.getCurrentUser().getId();
        logger.debug("Entered viewOrderByUserId method in OrderController");
        return orderService.viewOrderByUserId(userId);
    }

    /**
     * <p>
     *     Cancels order of current user by using orderId
     * </p>
     *
     * @param orderId             - To cancel order
     * @return                    - Success message and Status code
     * @throws NotFoundException  - If order not found
     */
    @PutMapping("/{orderId}/cancelOrder")
    public SuccessResponseDto cancelOrder(@PathVariable Integer orderId) throws NotFoundException {
        logger.debug("Entered cancelOrder method in OrderController");
        return orderService.cancelOrderById(orderId);
    }

    /**
     * <p>
     *     Gets list of order of particular product using productId
     * </p>
     *
     * @param productId          - To get list of orders placed for specific product
     * @return                   - Contains list of orders of particular product
     *                             which contains categoryName, subCategoryName,
     *                             productName, quantity, price
     * @throws NotFoundException - If order not found.
     */
    @GetMapping("/products/{productId}")
    public List<OrderDetailResponseDto> viewOrderByProductId(@PathVariable Integer productId)
                                                              throws NotFoundException {
        logger.debug("Entered viewOrderByProductId method in OrderController");
        return orderService.viewOrdersByProductId(productId);
    }

    /**
     * <p>
     *     Gets list of orders by orderedDate
     * </p>
     *
     * @param orderedDate        - To get orders of particular date
     * @return OrderResponseDto  - List of orders which contains userId,
     *                             orderedDate, expectedDeliveryDate, totalPrice,
     *                             totalQuantity, orderDetails, isDelivered
     * @throws NotFoundException - If order not found
     * @throws ParseException    - If date format is invalid
     */
    @GetMapping("/date/{orderedDate}")
    public List<OrderResponseDto> viewOrdersByDate(@PathVariable String orderedDate)
                                                   throws NotFoundException, ParseException {
        logger.debug("Entered viewOrdersByDate method in OrderController");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(orderedDate);
        return orderService.viewOrdersByDate(date);
    }

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
    @GetMapping("/date/{orderedDate}/user/{userId}")
    public List<OrderResponseDto> viewOrdersByIdAndDate(@PathVariable String orderedDate,
                                                        @PathVariable Integer userId)
                                                        throws NotFoundException, ParseException {
        logger.debug("Entered viewOrdersByIdAndDate method in OrderController");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(orderedDate);
        return orderService.viewOrdersByIdAndDate(date, userId);
    }
}
