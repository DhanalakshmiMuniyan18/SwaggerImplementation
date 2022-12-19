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
import javax.validation.constraints.NotBlank;

/**
 * <p>
 *     It holds the product request information(like name, unit, price etc.,)
 * </p>
 *
 * @author RUBAN
 * @version  1.0
 * @since 03-11-22
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {

    private int categoryId;

    @NotBlank(message = "Name cannot be Empty")
    private String name;

    private float price;

    private String unit;

    private int perHead;

    private int subCategoryId;

    private String image;
}
