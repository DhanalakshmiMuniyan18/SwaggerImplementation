/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.OrderDetailResponseDto;
import com.ideas2it.groceryshop.dto.OrderRequestDto;
import com.ideas2it.groceryshop.dto.OrderResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * <p>
 *     Tests whether its working as per our expectation.
 * </p>
 *
 * @author Dhanalakshmi.M
 * @version 1.0
 * @since 18-11-2022
 */
@SpringBootTest(classes = {OrderTestController.class})
public class OrderTestController {

    @Mock
    OrderService orderService;
    @InjectMocks
    OrderController orderController;

    /**
     * This method is used to test the placeOrder method
     *
     * @throws NotFoundException
     */
    @Test
    public void placeOrder() throws NotFoundException {
        SuccessResponseDto SuccessResponseDto = new SuccessResponseDto(202,
                "Order Placed Successfully");
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setAddressId(1);
         when(orderService.placeOrder(orderRequestDto)).thenReturn(SuccessResponseDto);
         assertEquals(200, SuccessResponseDto.getStatusCode());
    }

    /**
     * This method is used to test buyNow method
     *
     * @throws NotFoundException
     */
    @Test
    public void buyNow() throws NotFoundException {
        SuccessResponseDto SuccessResponseDto = new SuccessResponseDto(202,
                "Order Placed Successfully");
        OrderRequestDto orderRequestDto = new OrderRequestDto(5,1,1);
        when(orderService.buyNow(orderRequestDto)).thenReturn(SuccessResponseDto);
        assertEquals(200, SuccessResponseDto.getStatusCode());
    }



    /**
     * This method is used to test viewOrderByUserId method
     *
     * @throws NotFoundException
     */
    @Test
    public void viewOrderByUserId() throws NotFoundException {
        List<OrderDetailResponseDto> orderDetailsResponse = new ArrayList<>();
        orderDetailsResponse.add(new OrderDetailResponseDto("Fruits & Vegetables",
                "Fruits", "Apple", 2, 200f));
        List<OrderResponseDto> userOrderResponse = new ArrayList<>();
        userOrderResponse.add(new OrderResponseDto(1, new Date(2022/11/13), new Date(2022/11/15),
                230f, 5, orderDetailsResponse, true));
        Integer userId = 1;
        when(orderService.viewOrderByUserId(userId)).thenReturn(userOrderResponse);
        assertEquals(userId, userOrderResponse.get(0).getUserId());
    }

    /**
     * This method is used to test the cancelOrder method
     *
     * @throws NotFoundException
     */
    @Test
    public void cancelOrder() throws NotFoundException {
        Integer orderId = 1;
        SuccessResponseDto SuccessResponseDto = new SuccessResponseDto(202,
                "Order Cancelled Successfully");
        when(orderService.cancelOrderById(orderId)).thenReturn(SuccessResponseDto);
        assertEquals(200,SuccessResponseDto.getStatusCode());
    }

    /**
     * This method is used to test the viewOrderByDate method
     *
     * @throws NotFoundException
     */
    @Test
    public void viewOrdersByDate() throws NotFoundException {
        Date date = new Date(2022/11/13);
        List<OrderDetailResponseDto> orderDetailsResponse = new ArrayList<>();
        orderDetailsResponse.add(new OrderDetailResponseDto("Fruits & Vegetables",
                "Fruits", "Apple", 2, 200f));
        List<OrderResponseDto> userOrderResponse = new ArrayList<>();
        userOrderResponse.add(new OrderResponseDto(1, new Date(2022/11/13), new Date(2022/11/15),
                230f, 5, orderDetailsResponse, true));
        when(orderService.viewOrdersByDate(date)).thenReturn(userOrderResponse);
        assertEquals(date, userOrderResponse.get(0).getOrderedDate());
    }

    /**
     * This method is used to test the viewOrdersByIdAndDate method
     *
     * @throws NotFoundException
     * @throws ParseException
     */
    @Test
    public void viewOrdersByIdAndDate() throws NotFoundException, ParseException {
        Integer userId = 1;
        Date date = new Date(2022/11/13);
        List<OrderDetailResponseDto> orderDetailsResponse = new ArrayList<>();
        orderDetailsResponse.add(new OrderDetailResponseDto("Fruits & Vegetables",
                "Fruits", "Apple", 2, 200f));
        List<OrderResponseDto> userOrderResponse = new ArrayList<>();
        userOrderResponse.add(new OrderResponseDto(1, new Date(2022/11/13), new Date(2022/11/15),
                230f, 5, orderDetailsResponse, true));
        when(orderService.viewOrdersByIdAndDate(date, userId)).thenReturn(userOrderResponse);
        assertEquals(userId, userOrderResponse.get(0).getUserId());
    }
    
}
