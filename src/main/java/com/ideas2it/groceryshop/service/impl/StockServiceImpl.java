/*
 * <p>
 *      Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.StockRequestDto;
import com.ideas2it.groceryshop.dto.StockResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.mapper.StockMapper;
import com.ideas2it.groceryshop.model.OrderDetail;
import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.model.Stock;
import com.ideas2it.groceryshop.model.StoreLocation;
import com.ideas2it.groceryshop.model.Order;
import com.ideas2it.groceryshop.repository.StockRepository;
import com.ideas2it.groceryshop.service.ProductService;
import com.ideas2it.groceryshop.service.StockService;
import com.ideas2it.groceryshop.service.StoreService;

import lombok.RequiredArgsConstructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Provide implementation for services to add, update, view and delete stock
 *     for different products on different location
 * </p>
 *
 * @author Mohammed Ammar
 * @since 05-11-2022
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final Logger logger = LogManager.getLogger(StockServiceImpl.class);
    private final StoreService storeService;
    private final StockRepository stockRepository;
    private final ProductService productService;

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto addStock(StockRequestDto stockRequest, Integer locationId,
                                       Integer productId) throws NotFoundException, ExistedException {
        logger.debug("Entered addStock method in StockServiceImpl");
        if (stockRepository.existsByStoreLocationIdAndProductId(locationId, productId)) {
            logger.error("stock already exist");
            throw new ExistedException("Stock already exists");
        }
        Stock stock = StockMapper.toStock(stockRequest);
        StoreLocation storeLocation = storeService.getStoreById(locationId);
        if (storeLocation == null) {
            logger.error("store not found");
            throw new NotFoundException("Store not found");
        }
        stock.setStoreLocation(storeLocation);
        Product product = productService.getProductByProductId(productId);
        if (product == null) {
            logger.error("product not found");
            throw new NotFoundException("Product not found");
        }
        stock.setProduct(product);
        stock.setUnit(product.getUnit());
        stockRepository.save(stock);
        logger.debug("Stock created successfully");
        return new SuccessResponseDto(201, "Stock created successfully");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StockResponseDto> getStockByProductId(Integer productId) throws NotFoundException {
        logger.debug("Entered getStockByProductId method in StockServiceImpl");
        List<Stock> stocks = stockRepository.findByProductId(productId);
        if (stocks.isEmpty()) {
            logger.error("No stock data found");
            throw new NotFoundException("No data found");
        }
        List<StockResponseDto> stockResponse = new ArrayList<>();
        for (Stock eachStock : stocks) {
            stockResponse.add(StockMapper.toStockResponse(eachStock));
        }
        return stockResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StockResponseDto getStockByProductAndLocation(Integer productId, Integer locationId)
                                                         throws NotFoundException {
        logger.debug("Entered getStockByProductAndLocation method in StockServiceImpl");
        Stock stock = stockRepository.findByProductIdAndStoreLocationId(productId, locationId);
        if (stock == null) {
            logger.error("stock not found");
            throw new NotFoundException("Stock not found");
        }
        return StockMapper.toStockResponse(stock);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto updateStockByProductAndLocation(StockRequestDto stockRequest,
                                                              Integer productId, Integer locationId)
                                                              throws NotFoundException {
        logger.debug("Entered updateStockByProductAndLocation method in StockServiceImpl");
        Integer rowsAffected
                = stockRepository.updateStockByProductAndLocation(stockRequest.getStock(),
                                                                  productId, locationId);
        if (rowsAffected == 0) {
            logger.error("product or location not found");
            throw new NotFoundException("Product or Location not found");
        }
        logger.debug("stock updated successfully");
        return new SuccessResponseDto(200, "Stock updated successfully");
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void removeStockByOrderDetails(Order order, Integer pinCode) {
        logger.debug("Entered removeStockByOrderDetails method in StockServiceImpl");
        StoreLocation store = storeService.getStoreByPinCode(pinCode);
        for (OrderDetail eachOrderDetail : order.getOrderDetails()) {
            stockRepository.decreaseStockByProductsAndLocation(eachOrderDetail.getQuantity(),
                                                               eachOrderDetail.getProduct(),
                                                               store.getId());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateStockByOrderDetails(Order order) {
        logger.debug("Entered updateStockByOrderDetails method in StockServiceImpl");
        Integer pinCode = order.getOrderDelivery().getShippingAddress().getPinCode();
        StoreLocation store = storeService.getStoreByPinCode(pinCode);
        for (OrderDetail eachOrderDetail : order.getOrderDetails()) {
            stockRepository.increaseStockByProductsAndLocation(eachOrderDetail.getQuantity(),
                                                               eachOrderDetail.getProduct(),
                                                               store.getId());
        }
    }
}