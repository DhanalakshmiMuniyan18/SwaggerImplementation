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

/**
 * <p>
 *      It holds the OrderDetailsResponseDto information(like categoryName, subCategoryName,
 *      productName, quantity, price) and also it is used to view order details
 *      as response to user.
 * </p>
 *
 * @author   Dhanalakshmi M
 * @version  1.0
 * @since    18-11-2022
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponseDto {

    private String categoryName;

    private String subCategoryName;

    private String productName;

    private Integer quantity;

    private Float price;
}
