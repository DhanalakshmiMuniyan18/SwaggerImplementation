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
 *     It holds the stockResponse information(like productId, availableStock, locationId etc.,)
 *     and also it is used to view stock details as response to user
 * </p>
 *
 * @author Mohammed Ammar
 * @since 03-11-2022
 * @version 1.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockResponseDto {

    private Integer id;

    private Integer availableStock;

    private Integer productId;

    private String productName;

    private String subCategory;

    private String category;

    private String area;

    private Integer pinCode;
}

