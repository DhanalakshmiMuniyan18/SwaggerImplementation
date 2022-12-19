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
 *     It holds the CartDetailResponse information(like productName, quantity, price etc.,) and
 *     also it is used to view cart details as response to user
 * </p>
 *
 * @author Mohammed Ammar
 * @since 03-11-2022
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartDetailResponseDto {

    private String productName;

    private String subCategory;

    private String category;

    private Integer quantity;

    private Float price;
}
