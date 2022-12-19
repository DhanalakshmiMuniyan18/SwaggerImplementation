/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.dto.ProductResponseDto;
import com.ideas2it.groceryshop.model.Product;

/**
 * <p>
 *     Converts product dto to product entity and vice versa.
 * </p>
 *
 * @author RUBAN
 * @version 1.0
 * @since  03-11-22
 */
public class ProductMapper {

    /**
     * <p>
     *     Converts product request Dto to product entity.
     * </p>
     * @param productRequestDto - Contains name, unit, perHead, price, image.
     * @return product - product details.
     */
    public static Product toProduct(ProductRequestDto productRequestDto) {
        Product product = new Product();
        product.setUnit(productRequestDto.getUnit());
        product.setPerHead(productRequestDto.getPerHead());
        product.setName(productRequestDto.getName());
        product.setImage(productRequestDto.getImage());
        product.setPrice(productRequestDto.getPrice());
        return product;
    }

    /**
     * <p>
     *     Converts product entity to product response dto.
     * </p>
     * @param product - Contains name, unit, perHead, price, image.
     * @return productResponseDto - Contains product response details.
     */
    public static ProductResponseDto toProductDto(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setUnit(product.getUnit());
        productResponseDto.setPerHead(product.getPerHead());
        productResponseDto.setName(product.getName());
        productResponseDto.setImage(product.getImage());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setCategoryName(product.getSubCategory().getCategory().getName());
        productResponseDto.setSubCategoryName(product.getSubCategory().getName());
        productResponseDto.setIsStockAvailable(true);
        return productResponseDto;
    }
}
