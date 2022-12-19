/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.mapper;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.groceryshop.dto.OrderRequestDto;
import com.ideas2it.groceryshop.dto.OrderDetailResponseDto;
import com.ideas2it.groceryshop.model.OrderDetail;
import com.ideas2it.groceryshop.model.Product;

/**
 * <p>
 *     Converts entity to dto and vice versa
 * </p>
 *
 * @author   Dhanalakshmi.M
 * @version  1.0
 * @since    18-11-2022
 */
public class OrderDetailMapper {

    /**
     * <p>
     *     Converts OrderRequestDto to OrderDetail
     * </p>
     *
     * @param orderRequest - Contains quantity
     * @return             - quantity
     */
    public static OrderDetail toOrderDetail(OrderRequestDto orderRequest) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setQuantity(orderRequest.getQuantity());
        return orderDetail;
    }

    /**
     * <p>
     *     Converts OrderDetail to OrderDetailResponseDto
     * </p>
     *
     * @param orderDetail - contains quantity, price and product
     * @return            - quantity, price, productName,
     *                      categoryName, subCategoryName
     */
    public static OrderDetailResponseDto toOrderDetailDto(OrderDetail orderDetail) {
        OrderDetailResponseDto orderDetailsResponse = new OrderDetailResponseDto();
        Product product = orderDetail.getProduct();
        orderDetailsResponse.setQuantity(orderDetail.getQuantity());
        orderDetailsResponse.setPrice(orderDetail.getPrice());
        orderDetailsResponse.setProductName(orderDetail.getProduct().getName());
        orderDetailsResponse.setCategoryName(product.getSubCategory().getCategory().getName());
        orderDetailsResponse.setSubCategoryName(product.getSubCategory().getName());
        return orderDetailsResponse;
    }

    /**
     * <p>
     *     Converts list of OrderDetails to list of OrderDetailsResponseDto
     * </p>

     * @param orderDescription - list of orderDetail which contains quantity, price and product
     * @return                 - list of OrderDetailResponses which contains
     *                           quantity, price and product
     */
    public static List<OrderDetailResponseDto> toOrderDetailDtoList
                                                (List<OrderDetail> orderDescription) {
        List<OrderDetailResponseDto> orderDetails = new ArrayList<>();
        for (OrderDetail orderDetail : orderDescription) {
            OrderDetailResponseDto orderDetailsResponseDto = toOrderDetailDto(orderDetail);
            orderDetails.add(orderDetailsResponseDto);
        }
        return orderDetails;
    }

}
