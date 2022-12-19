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
 *     It holds the StoreResponse information(like pinCode, area etc.,) and
 *     also it is used to view store details as response to user
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
public class StoreResponseDto {

    private Integer id;

    private Integer pinCode;

    private String area;
}
