/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.StockRequestDto;
import com.ideas2it.groceryshop.dto.StockResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.StockService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * <p>
 *     This is StockController Test Class
 *     used to test methods available in
 *     StoreController
 * </p>
 * @author Mohammed Ammar
 * @since 15-11-2022
 * @version 1.0
 */
@SpringBootTest
public class StockControllerTest {

    @InjectMocks
    private StockController stockController;

    @Mock
    private StockService stockService;

    /**
     * <p>
     *     This method is used to test createStock method
     *     in StockController
     * </p>
     * @throws ExistedException throws if stock already available
     *                  for given product and location
     * @throws NotFoundException if product or location not found
     */
    @Test
    public void testCreateStock() throws ExistedException, NotFoundException {
        Integer locationId = 1;
        Integer productId = 1;
        StockRequestDto stockRequestDto
                = new StockRequestDto(100);
        SuccessResponseDto successDto
                = new SuccessResponseDto(201,
                                 "Stock Created Successfully");
        when(stockService.addStock(stockRequestDto,
                                    locationId,
                                    productId)).thenReturn(successDto);
        assertEquals(successDto.getStatusCode(),
                stockController.createStock(stockRequestDto,
                                            locationId,
                                            productId).getStatusCode());
    }

    /**
     * <p>
     *     This method is used to test updateStock method
     *     in StockController
     * </p>
     * @throws NotFoundException throws if stock not found for
     *                  given product and location
     */
    @Test
    public void testUpdateStock() throws NotFoundException {
        Integer locationId = 1;
        Integer productId = 1;
        StockRequestDto stockRequestDto
                = new StockRequestDto(100);
        SuccessResponseDto successDto
                = new SuccessResponseDto(200,
                                 "Stock Updated Successfully");
        when(stockService.updateStockByProductAndLocation(stockRequestDto,
                                                          locationId,
                                                          productId)).thenReturn(successDto);
        assertEquals(successDto.getStatusCode(),
                stockController.updateStockByProductAndLocation(stockRequestDto,
                                                                    locationId,
                                                                    productId).getStatusCode());
    }

    /**
     * <p>
     *     This method is used to test viewStockByProduct
     *     in StockController
     * </p>
     * @throws NotFoundException throws if stocks not found for given product
     */
    @Test
    public void testViewStockByProduct() throws NotFoundException {
        Integer productId = 1;
        List<StockResponseDto> stocks = new ArrayList<>();
        stocks.add(new StockResponseDto(1, 100, 1,
                                        "Apple", "Fruits",
                            "Fruits & Vegetable", "Guindy", 60001));
        stocks.add(new StockResponseDto(1, 100, 1,
                "Apple", "Fruits",
                "Fruits & Vegetable", "Anna Nagar", 60002));
        when(stockService.getStockByProductId(productId)).thenReturn(stocks);
        assertEquals(stocks.size(), stockController.viewStockByProduct(productId).size());
    }

    /**
     * <p>
     *     This method is used to test
     *     viewStockByProductAndLocation method
     *     in StockController
     * </p>
     * @throws NotFoundException throws if stock not found
     *                  for given product and location
     */
    @Test
    public void testViewStockByProductAndLocation() throws NotFoundException {
        Integer productId = 1;
        Integer locationId = 1;
        StockResponseDto stock = new StockResponseDto(1, 100, 1,
                "Apple", "Fruits",
                "Fruits & Vegetable", "Guindy", 60001);
        when(stockService.getStockByProductAndLocation(productId,
                                                      locationId)).thenReturn(stock);
        assertEquals(productId,
                stockController.getStockByProductAndLocation(productId,
                                                            locationId).getProductId());
    }

}
