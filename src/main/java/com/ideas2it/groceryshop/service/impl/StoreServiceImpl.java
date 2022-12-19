/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.StoreRequestDto;
import com.ideas2it.groceryshop.dto.StoreResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.mapper.StoreLocationMapper;
import com.ideas2it.groceryshop.model.StoreLocation;
import com.ideas2it.groceryshop.repository.StoreRepository;
import com.ideas2it.groceryshop.service.StoreService;

import lombok.RequiredArgsConstructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Provides implementation for services to add, update, view and delete
 *     Store Location (like area, pin code,. etc)
 * </p>
 * @author Mohammed Ammar
 * @since 05-11-2022
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final Logger logger = LogManager.getLogger(StoreServiceImpl.class);
    private final StoreRepository storeRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto addStore(StoreRequestDto storeLocationRequest) throws ExistedException {
        logger.debug("Entered addStore method in StoreServiceImpl");
        String area = storeLocationRequest.getArea();
        Integer pinCode = storeLocationRequest.getPinCode();
        if (storeRepository.existsByAreaOrPinCode(area, pinCode)) {
            logger.error("area or pin code already exist");
            throw new ExistedException("Area or PinCode already exists");
        }
        StoreLocation storeLocation = StoreLocationMapper.toStoreLocation(storeLocationRequest);
        storeRepository.save(storeLocation);
        logger.debug("store created successfully");
        return new SuccessResponseDto(201, "Store created successfully");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StoreResponseDto> getStores() throws NotFoundException {
        logger.debug("Entered getStores method in StoreServiceImpl");
        List<StoreResponseDto> storesResponse = new ArrayList<>();
        List<StoreLocation> stores = storeRepository.findByIsActive(true);
        if (stores.isEmpty()) {
            logger.error("store not found");
            throw new NotFoundException("Store not found");
        }
        for (StoreLocation eachStore : stores) {
            storesResponse.add(StoreLocationMapper.toStoreLocationResponse(eachStore));
        }
        return storesResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto removeStore(Integer storeId) throws NotFoundException {
        logger.debug("Entered removeStore method in StoreServiceImpl");
        Integer rowsAffected = storeRepository.deleteStoreById(storeId);
        if (rowsAffected == 0) {
            logger.error("store not found");
            throw new NotFoundException("Store not found");
        }
        logger.debug("store deleted successfully");
        return new SuccessResponseDto(200, "Store deleted successfully");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreResponseDto getStoreResponseById(Integer storeId) throws NotFoundException {
        logger.debug("Entered getStoreById method in StoreServiceImpl");
        StoreLocation storeLocation = storeRepository.findByIsActiveAndId(true, storeId);
        if (storeLocation == null) {
            logger.error("store not found");
            throw new NotFoundException("Store not found");
        }
        return StoreLocationMapper.toStoreLocationResponse(storeLocation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreLocation getStoreById(Integer storeId) throws NotFoundException {
        logger.debug("Entered getStoreById method in StoreServiceImpl");
        StoreLocation storeLocation = storeRepository.findByIsActiveAndId(true, storeId);
        if (storeLocation == null) {
            logger.error("store not found");
            throw new NotFoundException("Store not found");
        }
        return storeLocation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto modifyStore(StoreRequestDto storeLocationRequest,
                                          Integer storeId)
                                          throws NotFoundException, ExistedException {
        logger.debug("Entered modifyStore method in StoreServiceImpl");
        StoreLocation storeLocation = storeRepository.findByIsActiveAndId(true, storeId);
        if (storeLocation == null) {
            logger.error("store not found");
            throw new NotFoundException("Store not found");
        }
        String area = storeLocationRequest.getArea();
        Integer pinCode = storeLocationRequest.getPinCode();
        if(storeRepository.existsByAreaOrPinCodeAndIdNot(area, pinCode, storeId)) {
            logger.error("area or pin code already exist");
            throw new ExistedException("Area or PinCode already exists");
        }
        storeLocation.setArea(storeLocationRequest.getArea());
        storeLocation.setPinCode(storeLocationRequest.getPinCode());
        storeRepository.save(storeLocation);
        logger.debug("store updated successfully");
        return new SuccessResponseDto(200, "Store updated successfully");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreLocation getStoreByPinCode(Integer pinCode) {
        return storeRepository.findByIsActiveAndPinCode(true, pinCode);
    }
}
