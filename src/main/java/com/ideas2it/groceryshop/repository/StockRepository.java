/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * <p>
 *     Provide services to insert, update, retrieve and delete stock
 *     for different products and different location
 * </p>
 *
 * @author Mohammed Ammar
 * @since 05-11-2022
 * @version 1.0
 */
public interface StockRepository extends JpaRepository<Stock, Integer> {

    /**
     * <p>
     *     Retrieves all Stocks of specific product which available in multiple location
     *     based on Product Id
     * </p>
     *
     * @param productId - To fetch stock
     * @return list of stock - contains list of stock for different location
     */
    List<Stock> findByProductId(Integer productId);

    /**
     * <p>
     *     Retrieves stock details of specific product available on specific location
     *     based on location id and product id
     * </p>
     *
     * @param productId  - To fetch stock for given product
     * @param locationId - To fetch stock for given location
     * @return Stock details - contains stocks for product id and location id
     */
    Stock findByProductIdAndStoreLocationId(Integer productId, Integer locationId);

    /**
     * <p>
     *     Updates stock of specific product on any specific location based on
     *     location id and product id
     * </p>
     *
     * @param stock      - To update stock with given number
     * @param productId  - To update stock for given product
     * @param locationId - To update stock for given location
     * @return number of rows affected
     */
    @Query("UPDATE Stock s SET s.availableStock = s.availableStock + ?1 "
            + "where s.product.id = ?2 AND s.storeLocation.id = ?3")
    @Modifying
    @Transactional
    Integer updateStockByProductAndLocation(Integer stock, Integer productId, Integer locationId);

    /**
     * <p>
     *     Decreases stock of product that user has ordered based on
     *     product id, quantity and location id
     * </p>
     *
     * @param quantity   - To decrease stock
     * @param product    - To update stock for given product
     * @param locationId - To update stock for given location
     */
    @Query("UPDATE Stock s SET s.availableStock = s.availableStock - ?1 "
            + "where s.product = ?2 AND s.storeLocation.id = ?3")
    @Modifying
    @Transactional
    void decreaseStockByProductsAndLocation(Integer quantity, Product product, Integer locationId);

    /**
     * <p>
     *     To check if stock is available or not for specific product on particular location
     *     based on product id and location id
     * </p>
     *
     * @param locationId - To check stock for this location
     * @param productId  - To check stock for this product
     * @return true if exists else false
     */
    Boolean existsByStoreLocationIdAndProductId(Integer locationId, Integer productId);

    /**
     * <p>
     *     To check if Stock is greater than expected number or not
     *     based on product, location and expectedNumber
     * </p>
     *
     * @param locationId     - To check stock for given location
     * @param productId      - To check stock for given product
     * @param expectedNumber - To check stock greater than expected number
     * @return true if stock is greater that expected number
     */
    Boolean existsByStoreLocationIdAndProductIdAndAvailableStockGreaterThan(Integer locationId,
                                                                            Integer productId,
                                                                            Integer expectedNumber);

    /**
     * <p>
     *     Increases stock of product that user has cancelled
     *     based on product, location and quantity Ordered
     * </p>
     *
     * @param quantity   - To increase stock
     * @param locationId - To increase stock in given location
     * @param product    - To increase stock for given product
     */
    @Query("UPDATE Stock s SET s.availableStock = s.availableStock + ?1 "
            + "where s.product = ?2 AND s.storeLocation.id = ?3")
    @Modifying
    @Transactional
    void increaseStockByProductsAndLocation(Integer quantity, Product product, Integer locationId);
}
