/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *    It holds the UserResponseDto information(like username, firstname, lastname and etc..)
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {

    private Integer id;
    private String userName;
    private String firstName;
    private String lastName;
    private Long mobileNumber;
    private String email;
    private Date createdAt;
    private Date ModifiedAt;
    private Integer createdBy;
    private Integer modifiedBy;
    private Boolean isActive;
    private String role;
}