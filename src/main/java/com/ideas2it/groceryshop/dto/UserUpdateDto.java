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
import javax.validation.constraints.NotNull;

/**
 * <p>
 *    It holds the UserUpdateDto information(like username, firstname, lastname and password)
 * </p>
 *
 * @version 1.0
 * @author Rohit A P
 * @since 15-11-2022
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class UserUpdateDto {

    @NotNull
    @NotBlank(message = "User Name Field cannot be empty")
    private String userName;

    @NotNull
    @NotBlank(message = "First Name Field cannot be empty")
    private String firstName;

    @NotNull
    @NotBlank(message = "Last Name Field cannot be empty")
    private String lastName;

    @NotNull
    @NotBlank(message = "Password Field cannot be empty")
    private String password;

    @NotNull
    @NotBlank(message = "Email number Field cannot be empty")
    private String email;
}