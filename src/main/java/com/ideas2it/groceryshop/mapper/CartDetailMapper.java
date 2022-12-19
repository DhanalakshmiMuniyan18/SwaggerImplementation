/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.CartDetailRequestDto;
import com.ideas2it.groceryshop.dto.CartDetailResponseDto;
import com.ideas2it.groceryshop.model.CartDetail;
import com.ideas2it.groceryshop.model.Product;

/**
 * <p>
 *     Converts CartDetail Entity to CartDetail DTO and vice versa
 * </p>
 *
 * @author Mohammed Ammar
 * @since 02-11-2022
 * @version 1.0
 */
public class CartDetailMapper {

    /**
     * <p>
     *     Converts CartDetailRequest to CartDetail
     * </p>
     *
     * @param cartDetailRequest - Contains product id and quantity
     * @return                  - cart details like quantity, etc
     */
    public static CartDetail toCartDetail(CartDetailRequestDto cartDetailRequest) {
        CartDetail cartDetail = new CartDetail();
        cartDetail.setQuantity(cartDetailRequest.getQuantity());
        return cartDetail;
    }

    /**
     * <p>
     *     Converts CartDetail to CartDetailResponse
     * </p>
     *
     * @param cartDetail - Contains products added to cart
     * @return           - cart details like product name, total price, etc
     */
    public static CartDetailResponseDto toCartDetailResponse(CartDetail cartDetail) {
        CartDetailResponseDto cartDetailResponse = new CartDetailResponseDto();
        Product product = cartDetail.getProduct();
        cartDetailResponse.setProductName(product.getName());
        cartDetailResponse.setSubCategory(product.getSubCategory().getName());
        cartDetailResponse.setCategory(product.getSubCategory().getCategory().getName());
        cartDetailResponse.setQuantity(cartDetail.getQuantity());
        cartDetailResponse.setPrice(cartDetail.getPrice());
        return cartDetailResponse;
    }
}
