/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ideas2it.groceryshop.dto.OrderDetailResponseDto;
import com.ideas2it.groceryshop.dto.OrderRequestDto;
import com.ideas2it.groceryshop.dto.OrderResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.mapper.OrderDetailMapper;
import com.ideas2it.groceryshop.mapper.OrderMapper;
import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.model.OrderDetail;
import com.ideas2it.groceryshop.model.User;
import com.ideas2it.groceryshop.model.Address;
import com.ideas2it.groceryshop.model.CartDetail;
import com.ideas2it.groceryshop.model.Order;
import com.ideas2it.groceryshop.model.OrderDelivery;
import com.ideas2it.groceryshop.model.Cart;
import com.ideas2it.groceryshop.repository.OrderRepository;
import com.ideas2it.groceryshop.service.OrderService;
import com.ideas2it.groceryshop.service.ProductService;
import com.ideas2it.groceryshop.service.StockService;
import com.ideas2it.groceryshop.service.UserService;

/**
 * <p>
 *     Provides services like place order by using direct by now and by using cart, view order
 *    by using various filter options and cancel order
 * </p>
 *
 * @author Dhanalakshmi
 * @version 1.0v
 * @since 2022-11-18
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final AddressServiceImpl addressService;
    private final CartServiceImpl cartService;
    private final ProductService productService;
    private final UserService userService;
    private final StockService stockService;
    private final Logger logger = LogManager.getLogger(OrderService.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto placeOrder(OrderRequestDto orderRequest)
                                         throws NotFoundException {
        logger.debug("Entered placeOrder method in OrderServiceImpl");
        Cart cart = cartService.getActiveCartOfCurrentUser();
        if(cart != null) {
            Order order = new Order();
            order.setCart(cart);
            order.setUser(cart.getUser());
            order.setTotalQuantity(cart.getTotalQuantity());
            order.setTotalPrice(cart.getTotalPrice());
            List<CartDetail> cartDetails = cart.getCartDetails();
            List<OrderDetail> orderDetails = cartDetailsToOrderDetails(cartDetails);
            order.setOrderDetails(orderDetails);
            OrderDelivery orderDelivery = orderDelivery(orderRequest);
            order.setOrderDelivery(orderDelivery);
            orderRepository.save(order);
            cartService.removeCart();
            stockService.removeStockByOrderDetails(order, orderDelivery.getShippingAddress().getPinCode());
            logger.debug("Order placed successfully");
          return new SuccessResponseDto(200, "Order placed successfully");
        } else {
            logger.debug("Cart not found");
            throw new NotFoundException("Cart not found");
        }
    }

    /**
     * <p>
     *     Converts list of cart details to list of order details
     * </p>
     *
     * @param cartDetails - Contains list of quantity, price, productId
     * @return OrderDetails - Contains list of quantity, price, product
     */
    private List<OrderDetail> cartDetailsToOrderDetails(List<CartDetail> cartDetails) {
        logger.debug("Entered cartDetailsToOrderDetails method in OrderServiceImpl");
        List<OrderDetail> orderDetails = new ArrayList<>();
        for(CartDetail cartDetail : cartDetails) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(cartDetail.getQuantity());
            orderDetail.setPrice(cartDetail.getPrice());
            orderDetail.setProduct(cartDetail.getProduct());
            orderDetails.add(orderDetail);
        }
        return orderDetails;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto buyNow(OrderRequestDto orderRequest) throws NotFoundException {
        logger.debug("Entered buyNow method in OrderServiceImpl");
        User user = userService.getCurrentUser();
        Order order = new Order();
        List<OrderDetail> orderDetails = setOrderDetails(orderRequest);
        order.setOrderDetails(orderDetails);
        order.setTotalPrice(orderDetails.get(0).getPrice());
        order.setTotalQuantity(orderDetails.get(0).getQuantity());
        order.setUser(user);
        OrderDelivery orderDelivery = orderDelivery(orderRequest);
        order.setOrderDelivery(orderDelivery);
        orderRepository.save(order);
        stockService.removeStockByOrderDetails(order,
                orderDelivery.getShippingAddress().getPinCode());
        logger.debug("Order placed successfully");
        return new SuccessResponseDto(200, "Order placed successfully");
    }

    /**
     * <p>
     *     Adds orderDelivery details
     * </p>
     *
     * @param orderRequest - Contains quantity, productId, addressId, userId
     * @return OrderDelivery - Contains expectedDeliveryDate, shippingAddress of an order
     * @throws NotFoundException - If address not found.
     */
    private OrderDelivery orderDelivery(OrderRequestDto orderRequest)
                                        throws NotFoundException {
        logger.debug("Entered orderDelivery method in OrderServiceImpl");
        OrderDelivery orderDelivery = new OrderDelivery();
        Optional<Address> address = addressService.getAddressByAddressId(orderRequest.getAddressId());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 3);
        if(address.isEmpty()) {
            logger.debug("Address not found");
            throw new NotFoundException("Address not found");
        }
        orderDelivery.setExpectedDeliveryDate(calendar.getTime());
        orderDelivery.setShippingAddress(address.get());
        return orderDelivery;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderResponseDto viewOrderById(Integer orderId) throws NotFoundException {
        logger.debug("Entered viewOrderById method in OrderServiceImpl");
        Integer userId = userService.getCurrentUser().getId();
        Optional<Order> order = orderRepository.findByIdAndUserId(orderId, userId);
        if (order.isEmpty()) {
            logger.debug("No record found");
            throw new NotFoundException("No record found");
        }
        return OrderMapper.toOrderResponseDto(order.get());
    }

    /**
     * <p>
     *     Calculates the total price and set the orderDetails
     * </p>
     *
     * @param orderRequest - Contains quantity, productId, addressId, userId of an order
     * @return OrderDetails - Contains quantity, price, product
     */
    private List<OrderDetail> setOrderDetails(OrderRequestDto orderRequest) {
        logger.debug("Entered setOrderDetails method in OrderServiceImpl");
        OrderDetail orderDetail = OrderDetailMapper.toOrderDetail(orderRequest);
        List<OrderDetail> orderDetails = new ArrayList<>();
        Integer productId = orderRequest.getProductId();
        Product product = productService.getProductByProductId(productId);
        orderDetail.setPrice(orderDetail.getQuantity() * product.getPrice());
        orderDetail.setProduct(product);
        orderDetails.add(orderDetail);
        return orderDetails;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderResponseDto> viewAllActiveOrders() throws NotFoundException {
        logger.debug("Entered viewAllActiveOrders method in OrderServiceImpl");
        List<Order> orders = orderRepository.findByIsActive(true);
        if(orders.isEmpty()) {
            logger.debug("No record found");
            throw new NotFoundException("No record found");
        }
        return OrderMapper.toOrdersDtoList(orders);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderResponseDto> viewAllCancelledOrders() throws NotFoundException {
        logger.debug("Entered viewAllCancelledOrders method in OrderServiceImpl");
        List<Order> orders = orderRepository.findByIsActive(false);
        if(orders.isEmpty()) {
            logger.debug("No record found");
            throw new NotFoundException("No record found");
        }
        return OrderMapper.toOrdersDtoList(orders);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderResponseDto> viewOrderByUserId(Integer userId) throws NotFoundException {
        logger.debug("Entered viewOrderByUserId method in OrderServiceImpl");
        List<Order> orders = orderRepository.findByUserId(userId);
        if(orders.isEmpty()) {
            logger.debug("No record found");
            throw new NotFoundException("No record found");
        }
        return OrderMapper.toOrdersDtoList(orders);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto cancelOrderById(Integer orderId) throws NotFoundException {
        logger.debug("Entered cancelOrderById method in OrderServiceImpl");
        Integer userId = userService.getCurrentUser().getId();
        Optional<Order> order = orderRepository.findByIdAndIsActiveAndUserIdAndOrderDeliveryIsDelivered
                (orderId, true , userId, false);
        if(order.isPresent()) {
            Integer isCancelled = orderRepository.cancelOrderById(orderId, userId);
            if (isCancelled != 0) {
                stockService.updateStockByOrderDetails(order.get());
                logger.debug("Order cancelled successfully");
            }
            return new SuccessResponseDto(200, "Order cancelled successfully");
        } else {
            logger.debug("Order not found");
            throw new NotFoundException("Order not found");
        }
    }

    /**
     * {@inheritDoc}
     */
   @Override
    public List<OrderDetailResponseDto> viewOrdersByProductId(Integer productId) throws NotFoundException {
       logger.debug("Entered viewOrdersByProductId method in OrderServiceImpl");
        List<OrderDetail> orders = orderRepository.findByProductId(productId);
        if(orders.isEmpty()) {
            logger.debug("No record found");
            throw new NotFoundException("No record found");
        }
        return OrderDetailMapper.toOrderDetailDtoList(orders);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderResponseDto> viewOrdersByDate(Date orderedDate) throws NotFoundException {
        logger.debug("Entered viewOrdersByDate method in OrderServiceImpl");
        List<Order> orders =  orderRepository.findByOrderedDate(orderedDate);
        if(orders.isEmpty()) {
            logger.debug("No record found");
            throw new NotFoundException("No record found");
        }
        return OrderMapper.toOrdersDtoList(orders);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderResponseDto> viewOrdersByIdAndDate(Date orderedDate, Integer userId)
            throws NotFoundException {
        logger.debug("Entered viewOrdersByIdAndDate method in OrderServiceImpl");
        List<Order> orders =  orderRepository.findByOrderedDateAndUserId(orderedDate, userId);
        if(orders.isEmpty()) {
            logger.debug("No record found");
            throw new NotFoundException("No record found");
        }
        return OrderMapper.toOrdersDtoList(orders);
    }
}
