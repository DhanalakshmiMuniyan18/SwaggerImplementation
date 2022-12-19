/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.StockRequestDto;
import com.ideas2it.groceryshop.dto.StockResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.model.Order;

import java.util.List;

/**
 * <p>
 *     Provides services to add, update, view and delete stock
 *     for different products on different location
 * </p>
 *
 * @author Mohammed Ammar
 * @since 05-11-2022
 * @version 1.0
 */
public interface StockService {

    /**
     * <p>
     *     Creates Stock for particular product on particular location
     *     based on product id and location id
     * </p>
     *
     * @param stockRequest       - Contains number of stock to add
     * @param locationId         - To add stock on given location
     * @param productId          - To add stock for given product
     * @return                   - Success message and status code
     * @throws NotFoundException - If given store id or product id not found
     * @throws ExistedException  - If stock already exist for given product and location
     */
    SuccessResponseDto addStock(StockRequestDto stockRequest, Integer locationId, Integer productId)
                                throws NotFoundException, ExistedException;

    /**
     * <p>
     *     Gets available Stock details for particular product
     *     available on different locations
     * </p>
     *
     * @param productId          - To view stock details on different location
     * @return                   - List of available stock for multiple location
     *                             for given product id
     * @throws NotFoundException - If stock not found for given product id
     */
    List<StockResponseDto> getStockByProductId(Integer productId) throws NotFoundException;

    /**
     * <p>
     *     Gets available stock detail for particular product
     *     on particular location based on product id and location id
     * </p>
     *
     * @param productId          - To view stock detail for given product
     * @param locationId         - To view stock detail on given location
     * @return                   - Available stock detail for given product and location
     * @throws NotFoundException - If stock not found for given id
     */
    StockResponseDto getStockByProductAndLocation(Integer productId, Integer locationId)
                                                  throws NotFoundException;


    /**
     * <p>
     *     Updates stock for particular product on particular location
     *     based on product id and location id
     * </p>
     *
     * @param stockRequest       - Contains stock details to update
     * @param productId          - To update stock for this product
     * @param locationId         - To update stock on this location
     * @return                   - Success message and status code
     * @throws NotFoundException - If stock not found for product id and location id
     */
    SuccessResponseDto updateStockByProductAndLocation(StockRequestDto stockRequest, Integer productId,
                                                       Integer locationId) throws NotFoundException;

    /**
     * <p>
     *     Decreases stock for products available in order detail
     *     based on given pin code
     * </p>
     *
     * @param order   - Contains ordered product details to decrease stock
     * @param pinCode - To reduce stock for given pin code
     */
    void removeStockByOrderDetails(Order order, Integer pinCode);

    /**
     * <p>
     *     Increases stock for products available in order detail,
     *     when user cancelled the order
     * </p>
     *
     * @param order - Contains cancelled product details to increase stock
     */
    void updateStockByOrderDetails(Order order);
}
