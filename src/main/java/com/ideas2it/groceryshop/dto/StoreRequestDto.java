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

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 *     It holds the StoreRequest information(like area, pinCode etc.,) and
 *     also it is used to get store details from user to add into the store
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
public class StoreRequestDto {

    @NotNull(message = "Pin code cannot be empty")
    private Integer pinCode;

    @NotEmpty(message = "Area cannot be empty")
    private String area;
}
