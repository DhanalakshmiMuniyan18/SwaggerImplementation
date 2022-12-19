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

import java.util.Date;
import java.util.List;

/**
 * <p>
 *     It holds the CartResponse information(like totalPrice, products etc.,) and
 *     also it is used to view cart as response to user
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
public class CartResponseDto {

    private Date createdAt;

    private Integer id;

    private Float totalPrice;

    private List<CartDetailResponseDto> cartDetails;
}
