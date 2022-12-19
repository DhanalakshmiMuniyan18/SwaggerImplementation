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
 *    It holds the LoginRequest information(like username and password)
 * </p>
 *
 * @version 1.0
 * @author Rohit A P
 * @since 11-11-2022
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    @NotNull
    @NotBlank(message = "UserName/MobileNumber Field cannot be empty")
    private String userNameOrMobileNumber;

    @NotNull
    @NotBlank(message = "password Field cannot be empty")
    private String password;
}