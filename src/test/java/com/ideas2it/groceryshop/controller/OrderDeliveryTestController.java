/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.AddressResponseDto;
import com.ideas2it.groceryshop.dto.OrderDeliveryResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.OrderDeliveryService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

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
@SpringBootTest(classes = {OrderDeliveryTestController.class})
public class OrderDeliveryTestController {

    @Mock
    OrderDeliveryService orderDeliveryService;
    @InjectMocks
    OrderDeliveryController orderDeliveryController;

    /**
     * <p>
     *     Tests statusUpdate method
     * </p>
     *
     * @throws ExistedException
     * @throws NotFoundException
     */
    @Test
    public void statusUpdate() throws ExistedException, NotFoundException {
        SuccessResponseDto SuccessResponseDto = new SuccessResponseDto(202,
                "Order Delivered Successfully");
        when(orderDeliveryService.statusUpdate(1)).thenReturn(SuccessResponseDto);
        assertEquals(200, SuccessResponseDto.getStatusCode());
    }

    /**
     * <p>
     *     Tests the getDeliveryOrder method
     * </p>
     *
     * @throws NotFoundException
     */
    @Test
    public void getDeliveryOrder() throws NotFoundException {
        Integer userId = 1;
        AddressResponseDto addressResponseDto = new AddressResponseDto(1,"1st Street",
                "AnnaNagar", 600029, "VR Mall", new Date(2022/11/13),
                new Date(2022/11/13), 1, 1, true, true);
        OrderDeliveryResponseDto orderDeliveryResponseDto = new OrderDeliveryResponseDto(userId, 1,
                200f, 6, addressResponseDto, true);
        when(orderDeliveryService.getDeliveryOrder(userId)).thenReturn(orderDeliveryResponseDto);
        assertEquals(userId, orderDeliveryResponseDto.getUserId());
    }

}
