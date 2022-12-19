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
 *    It holds the AddressResponseDto information(like street, landmark etc.,)
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
public class AddressResponseDto {

    private Integer id;
    private String street;
    private String area;
    private Integer pinCode;
    private String landMark;
    private Date createdAt;
    private Date ModifiedAt;
    private Integer createdBy;
    private Integer modifiedBy;
    private Boolean isActive;
    private Boolean isDefault;
}
