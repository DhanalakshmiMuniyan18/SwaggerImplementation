/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *    It holds the UserRequestDto information(like username, firstname, lastname and etc..)
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
public class UserRequestDto {

    @NotNull
    @NotBlank(message = "Username Field cannot be empty")
    private String userName;

    @NotNull
    @NotBlank(message = "First name Field cannot be empty")
    private String firstName;

    @NotNull
    @NotBlank(message = "Last name Field cannot be empty")
    private String lastName;

    @NotNull(message = "Mobile Number Field cannot be empty")
    @Pattern(regexp = "^[6-9][0-9]{9}", message = "Enter valid mobile number")
    private String mobileNumber;

    @NotNull
    @NotBlank(message = "Email Field cannot be empty")
    private String email;

    @NotNull
    @NotBlank(message = "Password Field cannot be empty")
    private String password;

    @NotNull
    @NotBlank(message = "Role Field cannot be empty")
    private String role;
}