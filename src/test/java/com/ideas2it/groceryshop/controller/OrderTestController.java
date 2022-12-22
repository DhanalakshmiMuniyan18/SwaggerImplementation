/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ideas2it.groceryshop.dto.OrderDetailResponseDto;
import com.ideas2it.groceryshop.dto.OrderRequestDto;
import com.ideas2it.groceryshop.dto.OrderResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

@SpringBootTest
public class OrderTestController {

    private MockMvc mockmvc;

    ObjectMapper objectMapper = new ObjectMapper();
    private final OrderService orderService = mock(OrderService.class);
    @InjectMocks
    OrderController orderController;

    @BeforeEach
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
    public void placeOrder() throws Exception {
        SuccessResponseDto SuccessResponseDto = new SuccessResponseDto(200,
                "Order Placed Successfully");
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setAddressId(1);
         when(orderService.placeOrder(orderRequestDto)).thenReturn(SuccessResponseDto);
        mockmvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/user/orders/placeOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderRequestDto)))
                .andExpect(status().isOk());
    }

    /**
     * This method is used to test buyNow method
     *
     * @throws NotFoundException
     */
    @Test
    public void buyNow() throws Exception {
        SuccessResponseDto SuccessResponseDto = new SuccessResponseDto(200,
                "Order Placed Successfully");
        OrderRequestDto orderRequestDto = new OrderRequestDto(5,
                1,1);
        when(orderService.buyNow(orderRequestDto)).thenReturn(SuccessResponseDto);
        this.mockmvc.perform(post("/api/v1/user/orders/buyNow")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
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
        when(orderService.viewAllActiveOrders()).thenReturn(userOrderResponse);
        mockmvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/user/orders/activeOrders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderStatus", is(true)));
    }

    /**
     * This method is used to view all cancelled orders
     *
     * @throws NotFoundException
     */
    @Test
    public void viewAllCancelledOrders() throws Exception {
        List<OrderDetailResponseDto> orderDetailsResponse = new ArrayList<>();
        orderDetailsResponse.add(new OrderDetailResponseDto(
                "Fruits & Vegetables","Fruits",
                "Apple", 2, 200f));
        List<OrderResponseDto> userOrderResponse = new ArrayList<>();
        userOrderResponse.add(new OrderResponseDto(1, new Date(2022/11/13),
                new Date(2022/11/15),230f, 5,false,
                orderDetailsResponse, false));
        when(orderService.viewAllCancelledOrders()).thenReturn(userOrderResponse);
        mockmvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/user/orders/cancelledOrders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderStatus", is(false)));
    }

    /**
     * This method is used to test viewOrderById method
     *
     * @throws NotFoundException
     */
    @Test
    public void viewOrderById() throws Exception {
        Integer orderId = 1;
        List<OrderDetailResponseDto> orderDetailsResponse = Collections.singletonList
                (new OrderDetailResponseDto("Fruits & Vegetables",
                        "Fruits","Apple", 2, 200f));
        OrderResponseDto orderResponseDto = new OrderResponseDto(1,
                new Date(2022/11/13), new Date(2022/11/15),
                230f, 5,true,
                orderDetailsResponse, false);
        when(orderService.viewOrderById(orderId)).thenReturn(orderResponseDto);
        mockmvc.perform(MockMvcRequestBuilders.get("/api/v1/user/orders/{orderId}",
                                orderId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * This method is used to test viewOrderByUserId method
     *
     * @throws NotFoundException
     */
    @Test
    public void viewOrderByUserId() throws Exception {
        List<OrderDetailResponseDto> orderDetailsResponse = Collections.singletonList
                (new OrderDetailResponseDto("Fruits & Vegetables",
                        "Fruits","Apple",
                        2, 200f));
        List<OrderResponseDto> userOrderResponse = Collections.singletonList(new OrderResponseDto
                (1, new Date(2022/11/13), new Date(2022/11/15),230f,
                        5,true, orderDetailsResponse, false));
        Integer userId = 1;
        when(orderService.viewOrderByUserId(userId)).thenReturn(userOrderResponse);
        mockmvc.perform(MockMvcRequestBuilders.get("/api/v1/user/orders/{userId}",
                                userOrderResponse.get(0).getUserId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * This method is used to test the cancelOrder method
     *
     * @throws NotFoundException
     */
    @Test
    public void cancelOrder() throws Exception {
        Integer orderId = 1;
        SuccessResponseDto SuccessResponseDto = new SuccessResponseDto(200,
                "Order Cancelled Successfully");
        when(orderService.cancelOrderById(orderId)).thenReturn(SuccessResponseDto);
        mockmvc.perform(MockMvcRequestBuilders.put(
                "/api/v1/user/orders/{orderId}/cancelOrder",
                orderId))
                .andExpect(status().isOk());
    }

    @Test
    public void viewOrderByProductId() throws Exception {
        Integer productId = 1;
        List<OrderDetailResponseDto> orderDetailsResponse = Collections.singletonList
                (new OrderDetailResponseDto("Fruits & Vegetables",
                "Fruits", "Apple", 2, 200f));

        when(orderService.viewOrdersByProductId(productId)).thenReturn(orderDetailsResponse);
        mockmvc.perform(MockMvcRequestBuilders.get(
                "/api/v1/user/orders/{productId}", productId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * This method is used to test the viewOrderByDate method
     *
     * @throws NotFoundException
     */
    @Test
    public void viewOrdersByDate() throws Exception {
        List<OrderDetailResponseDto> orderDetailsResponse = Collections.singletonList
                (new OrderDetailResponseDto(
                "Fruits & Vegetables","Fruits",
                "Apple", 2, 200f));
        List<OrderResponseDto> userOrderResponse = new ArrayList<>();
        userOrderResponse.add(new OrderResponseDto(1, new Date(2022/11/13),
                new Date(2022/12/12),230f, 5, true,
                orderDetailsResponse, true));
        when(orderService.viewOrdersByDate(new Date("2022/12/12"))).
                thenReturn(userOrderResponse);
        mockmvc.perform(MockMvcRequestBuilders.get(
                "/api/v1/user/orders/date/{date1}",
                userOrderResponse.get(0).getOrderedDate())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].orderedDate",
                        is(userOrderResponse.get(0).getOrderedDate())));
    }

    /**
     * This method is used to test the viewOrdersByIdAndDate method
     *
     * @throws NotFoundException
     * @throws ParseException
     */
    @Test
    public void viewOrdersByIdAndDate() throws Exception, ParseException {
        Integer userId = 1;
        Date date = new Date(2022/11/13);
        List<OrderDetailResponseDto> orderDetailsResponse = Collections.singletonList(
                new OrderDetailResponseDto(
                "Fruits & Vegetables","Fruits",
                "Apple", 2, 200f));
        List<OrderResponseDto> userOrderResponse = Collections.singletonList
                (new OrderResponseDto(1, date, date,
                        230f,5,
                        true, orderDetailsResponse, false));
        System.out.println(userOrderResponse);
        when(orderService.viewOrdersByIdAndDate(date, userId))
                .thenReturn(userOrderResponse);
        mockmvc.perform(MockMvcRequestBuilders.get
                                ("/date/{date}/user/{userId}",
                        ("2022/11/13"), userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}

