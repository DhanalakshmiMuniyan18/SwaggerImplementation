/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.StoreRequestDto;
import com.ideas2it.groceryshop.dto.StoreResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.model.StoreLocation;

import java.util.List;

/**
 * <p>
 *     Provides services to add, update, view and delete Store Location
 *     (like area, pin code,. etc)
 * </p>
 *
 * @author Mohammed Ammar
 * @since 05-11-2022
 * @version 1.0
 */
public interface StoreService {

    /**
     * <p>
     *     Creates store location based on given location detail
     * </p>
     *
     * @param storeLocationRequest - Contains area and pin code to create store
     * @return                     - Success message and status code
     * @throws ExistedException    - If area or location already exist
     */
    SuccessResponseDto addStore(StoreRequestDto storeLocationRequest) throws ExistedException;

    /**
     * <p>
     *     Gets all active stores location detail
     * </p>
     *
     * @return                   - List of area, pin code, etc., for available stores
     * @throws NotFoundException - If no store location found
     */
    List<StoreResponseDto> getStores() throws NotFoundException;

    /**
     * <p>
     *      Removes store based on given store location id
     * </p>
     *
     * @param storeId            - To remove store for given store
     * @return                   - Success message and status code
     * @throws NotFoundException - If store not found
     */
    SuccessResponseDto removeStore(Integer storeId) throws NotFoundException;


    /**
     * <p>
     *     Gets particular store detail as response dto based on given location id
     * </p>
     *
     * @param storeId            - To get particular store detail
     * @return                   - Success message and status code
     * @throws NotFoundException - If store not found
     */
    StoreResponseDto getStoreResponseById(Integer storeId) throws NotFoundException;

    /**
     * <p>
     *     Gets particular active store based on given location id
     * </p>
     *
     * @param storeId            - To get particular store location
     * @return                   - Store detail like area and pin code
     * @throws NotFoundException - If store not found
     */
    StoreLocation getStoreById(Integer storeId) throws NotFoundException;

    /**
     * <p>
     *     Updates store location detail with given new detail
     *     based on given location id
     * </p>
     *
     * @param storeLocationRequest - Contains store location detail to update
     * @param storeId              - To update store detail for given store
     * @return                     - Success message and status code
     * @throws NotFoundException   - If store not found
     * @throws ExistedException    - If given new details already exist
     */
    SuccessResponseDto modifyStore(StoreRequestDto storeLocationRequest, Integer storeId)
                                   throws NotFoundException, ExistedException;

    /**
     * <p>
     *     Gets store details based on given pinCode
     * </p>
     *
     * @param pinCode        - To get store details for given pin code
     * @return storeLocation - Store details like area and pinCode
     */
    StoreLocation getStoreByPinCode(Integer pinCode);
}
