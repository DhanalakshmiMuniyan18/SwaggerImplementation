/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.StockRequestDto;
import com.ideas2it.groceryshop.dto.StockResponseDto;
import com.ideas2it.groceryshop.model.Stock;

/**
 * <p>
 *     Converts Stock Entity to Stock DTO and vice versa
 * </p>
 *
 * @author Mohammed Ammar
 * @since 02-11-2022
 * @version 1.0
 */
public class StockMapper {

    /**
     * <p>
     *     Converts StockRequest to Stock Entity
     * </p>
     *
     * @param stockRequest - Contains stock to add
     * @return             - Stock detail to add stock
     */
    public static Stock toStock(StockRequestDto stockRequest) {
        Stock stock = new Stock();
        stock.setAvailableStock(stockRequest.getStock());
        return stock;
    }

    /**
     * <p>
     *     Converts stock entity to Stock Response
     * </p>
     *
     * @param stock - Contains stock details like available stock
     * @return      - Stock details like stock, product name, location
     */
    public static StockResponseDto toStockResponse(Stock stock) {
        StockResponseDto stockResponse = new StockResponseDto();
        stockResponse.setId(stock.getId());
        stockResponse.setAvailableStock(stock.getAvailableStock());
        stockResponse.setArea(stock.getStoreLocation().getArea());
        stockResponse.setPinCode(stock.getStoreLocation().getPinCode());
        stockResponse.setProductId(stock.getProduct().getId());
        stockResponse.setProductName(stock.getProduct().getName());
        stockResponse.setSubCategory(stock.getProduct().getSubCategory().getName());
        stockResponse.setCategory(stock.getProduct().getSubCategory().getCategory().getName());
        return stockResponse;
    }
}
