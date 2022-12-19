/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.StoreRequestDto;
import com.ideas2it.groceryshop.dto.StoreResponseDto;
import com.ideas2it.groceryshop.model.StoreLocation;

/**
 * <p>
 *     Converts StoreLocation Entity to StoreLocation DTO and vice versa
 * </p>
 *
 * @author Mohammed Ammar
 * @since 02-11-2022
 * @version 1.0
 */
public class StoreLocationMapper {

    /**
     * <p>
     *     Converts StoreLocation Request to StoreLocation Entity
     * </p>
     *
     * @param storeLocationRequest - Contains area, pin code
     * @return                     - Store details like area and pin code
     */
    public static StoreLocation toStoreLocation(StoreRequestDto storeLocationRequest) {
        StoreLocation storeLocation = new StoreLocation();
        storeLocation.setArea(storeLocationRequest.getArea());
        storeLocation.setPinCode(storeLocationRequest.getPinCode());
        return storeLocation;
    }

    /**
     * <p>
     *     Converts StoreLocation Entity to StoreLocation Response
     * </p>
     *
     * @param storeLocation - Contains store location details
     * @return              - Store response details like area, pin code
     */
    public static StoreResponseDto toStoreLocationResponse(StoreLocation storeLocation) {
        StoreResponseDto stores = new StoreResponseDto();
        stores.setId(storeLocation.getId());
        stores.setArea(storeLocation.getArea());
        stores.setPinCode(storeLocation.getPinCode());
        return stores;
    }
}
