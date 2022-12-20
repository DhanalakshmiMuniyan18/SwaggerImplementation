/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ideas2it.groceryshop.dto.OrderDetailResponseDto;
import com.ideas2it.groceryshop.dto.OrderRequestDto;
import com.ideas2it.groceryshop.dto.OrderResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.OrderService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>
 *     Tests whether its working as per our expectation.
 * </p>
 *
 * @author Dhanalakshmi.M
 * @version 1.0
 * @since 18-11-2022
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OrderTestController {

    private MockMvc mockmvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();
    @Mock
    OrderService orderService;
    @InjectMocks
    OrderController orderController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockmvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    /**
     * This method is used to test the placeOrder method
     *
     * @throws NotFoundException
     */
    @Test
    public void placeOrder() throws NotFoundException {
        SuccessResponseDto SuccessResponseDto = new SuccessResponseDto(200,
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
        SuccessResponseDto SuccessResponseDto = new SuccessResponseDto(200,
                "Order Placed Successfully");
        OrderRequestDto orderRequestDto = new OrderRequestDto(5,
                1,1);
        when(orderService.buyNow(orderRequestDto)).thenReturn(SuccessResponseDto);
        assertEquals(200, SuccessResponseDto.getStatusCode());
    }

    /**
     * This method is used to Test viewAllActiveOrders
     *
     * @throws NotFoundException
     */
    @Test
    public void viewAllActiveOrders() throws Exception {
        List<OrderDetailResponseDto> orderDetailsResponse = new ArrayList<>();
        orderDetailsResponse.add(new OrderDetailResponseDto(
                "Fruits & Vegetables","Fruits",
                "Apple", 2, 200f));
        List<OrderResponseDto> userOrderResponse = new ArrayList<>();
        userOrderResponse.add(new OrderResponseDto(1,
                new Date(2022/11/13), new Date(2022/11/15),
                230f, 5,true,
                orderDetailsResponse, false));
        Mockito.when(orderService.viewAllActiveOrders()).thenReturn(userOrderResponse);
        mockmvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/user/orders/activeOrders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect((ResultMatcher) jsonPath("$[0].orderStatus", is(true)));
    }

    /**
     * This method is used to view all cancelled orders
     *
     * @throws NotFoundException
     */
    @Test
    public void viewAllCancelledOrders() throws NotFoundException {
        List<OrderDetailResponseDto> orderDetailsResponse = new ArrayList<>();
        orderDetailsResponse.add(new OrderDetailResponseDto(
                "Fruits & Vegetables","Fruits",
                "Apple", 2, 200f));
        List<OrderResponseDto> userOrderResponse = new ArrayList<>();
        userOrderResponse.add(new OrderResponseDto(1, new Date(2022/11/13),
                new Date(2022/11/15),230f, 5,false,
                orderDetailsResponse, false));
        when(orderService.viewAllActiveOrders()).thenReturn(userOrderResponse);
        assertEquals(false, userOrderResponse.get(0).getOrderStatus());
    }

    /**
     * This method is used to test viewOrderById method
     *
     * @throws NotFoundException
     */
    @Test
    public void viewOrderById() throws NotFoundException {
        Integer orderId = 1;
        List<OrderDetailResponseDto> orderDetailsResponse = new ArrayList<>();
        orderDetailsResponse.add(new OrderDetailResponseDto(
                "Fruits & Vegetables","Fruits",
                "Apple", 2, 200f));
        OrderResponseDto orderResponseDto = new OrderResponseDto(1,
                new Date(2022/11/13), new Date(2022/11/15),
                230f, 5,true,
                orderDetailsResponse, false);
        when(orderService.viewOrderById(orderId)).thenReturn(orderResponseDto);
    }

    /**
     * This method is used to test viewOrderByUserId method
     *
     * @throws NotFoundException
     */
    @Test
    public void viewOrderByUserId() throws NotFoundException {
        List<OrderDetailResponseDto> orderDetailsResponse = new ArrayList<>();
        orderDetailsResponse.add(new OrderDetailResponseDto(
                "Fruits & Vegetables","Fruits",
                "Apple", 2, 200f));
        List<OrderResponseDto> userOrderResponse = new ArrayList<>();
        userOrderResponse.add(new OrderResponseDto(1, new Date(2022/11/13),
                new Date(2022/11/15),230f, 5,true,
                orderDetailsResponse, false));
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
        SuccessResponseDto SuccessResponseDto = new SuccessResponseDto(200,
                "Order Cancelled Successfully");
        when(orderService.cancelOrderById(orderId)).thenReturn(SuccessResponseDto);
        assertEquals(200,SuccessResponseDto.getStatusCode());
    }

    @Test
    public void viewOrderByProductId() throws NotFoundException {
        Integer productId = 1;
        List<OrderDetailResponseDto> orderDetailsResponse = new ArrayList<>();
        orderDetailsResponse.add(new OrderDetailResponseDto("Fruits & Vegetables",
                "Fruits", "Apple", 2, 200f));
        when(orderService.viewOrdersByProductId(productId)).thenReturn(orderDetailsResponse);
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
        orderDetailsResponse.add(new OrderDetailResponseDto(
                "Fruits & Vegetables","Fruits",
                "Apple", 2, 200f));
        List<OrderResponseDto> userOrderResponse = new ArrayList<>();
        userOrderResponse.add(new OrderResponseDto(1, new Date(2022/11/13),
                new Date(2022/11/15),230f, 5, true,
                orderDetailsResponse, true));
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
        orderDetailsResponse.add(new OrderDetailResponseDto(
                "Fruits & Vegetables","Fruits",
                "Apple", 2, 200f));
        List<OrderResponseDto> userOrderResponse = new ArrayList<>();
        userOrderResponse.add(new OrderResponseDto(1, new Date(2022/11/13),
                new Date(2022/11/15),230f, 5, true,
                orderDetailsResponse, false));
        when(orderService.viewOrdersByIdAndDate(date, userId)).thenReturn(userOrderResponse);
        assertEquals(userId, userOrderResponse.get(0).getUserId());
    }

}

