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
 *     It holds the Category request information(like category name etc.,)
 * </p>
 *
 * @author RUBAN
 * @version  1.0
 * @since 03-11-22
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    private Integer parentId;
}

