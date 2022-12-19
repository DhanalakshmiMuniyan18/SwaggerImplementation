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
 *     It holds the product response information(like name, price, unit, perHead etc.,)
 * </p>
 *
 * @author RUBAN
 * @version  1.0
 * @since 03-11-22
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private int id;

    private String name;

    private float price;

    private String unit;

    private int perHead;

    private Boolean isStockAvailable;

    private String categoryName;

    private String subCategoryName;

    private String image;
}
