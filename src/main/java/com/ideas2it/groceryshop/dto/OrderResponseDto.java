/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *      It holds the OrderDeliveryResponseDto information(like userId, orderedDate,
 *      expectedDeliveryDate, totalPrice, totalQuantity, orderStatus, orderDetails,
 *      isDelivered) and also it is used to view order as response to user.
 * </p>
 *
 * @author   Dhanalakshmi M
 * @version  1.0
 * @since    18-11-2022
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderResponseDto {

    private Integer userId;

    private Date orderedDate;

    private Date expectedDeliveryDate;

    private Float totalPrice;

    private Integer totalQuantity;

    private Boolean orderStatus;

    private List<OrderDetailResponseDto> orderDetails;

    private Boolean isDelivered;
}
