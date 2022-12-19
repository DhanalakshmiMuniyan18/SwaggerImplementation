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
 *     It holds the sub category response information(like sub category name etc.,)
 * </p>
 *
 * @author RUBAN 03-11-2022
 * @version  1.0
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubCategoryResponseDto {

    private int id;

    private String name;

    private String categoryName;
}
