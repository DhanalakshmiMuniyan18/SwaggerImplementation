/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *    It holds the AddressRequestDto information(like street, landmark etc.,)
 * </p>
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class AddressRequestDto {

    @NotNull
    @NotBlank(message = "Street Field cannot be empty")
    private String street;

    @NotNull
    @NotBlank(message = "Area Field cannot be empty")
    private String area;

    @NotNull(message = "PinCode Field cannot be empty")
    private Integer pinCode;

    @NotNull
    @NotBlank(message = "landMark Field cannot be empty")
    private String landMark;
}