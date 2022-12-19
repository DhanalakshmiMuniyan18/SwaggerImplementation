/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.StoreRequestDto;
import com.ideas2it.groceryshop.dto.StoreResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.StoreService;
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
 *     This is test class for StoreLocationController
 *     It has method to test StoreLocationController
 *     methods
 * </p>
 * @author Mohammed Ammar
 * @since 15-11-2022
 * @version 1.0
 */
@SpringBootTest
public class StoreLocationControllerTest {

    @InjectMocks
    private StoreLocationController storeLocationController;

    @Mock
    private StoreService storeService;

    /**
     * <p>
     *     This method is used to test createStore
     *     method in StoreLocationController
     * </p>
     * @throws ExistedException throws if store already exist
     */
    @Test
    public void testCreateStore() throws ExistedException {
        StoreRequestDto storeRequestDto
                = new StoreRequestDto(600001, "Guindy");
        SuccessResponseDto successDto
                = new SuccessResponseDto(201,
                                 "Store Created Successfully");
        when(storeService.addStore(storeRequestDto)).thenReturn(successDto);
        SuccessResponseDto result = storeLocationController.createStore(storeRequestDto);
        assertEquals(successDto.getStatusCode(), result.getStatusCode());
    }

    /**
     * <p>
     *     This method is used to test
     *     viewStores method in StoreLocationController
     * </p>
     * @throws NotFoundException throws if stores not found
     */
    @Test
    public void testViewStores() throws NotFoundException {
        List<StoreResponseDto> stores = new ArrayList<>();
        StoreResponseDto storeOne=
                new StoreResponseDto(1, 600001, "Guindy");
        StoreResponseDto storeTwo =
                new StoreResponseDto(1, 600002, "Anna Nagar");
        stores.add(storeOne);
        stores.add(storeTwo);
        when(storeService.getStores()).thenReturn(stores);
        assertEquals(stores.size(), storeLocationController.viewStores().size());
    }

    /**
     * <p>
     *     This method is used to test
     *     viewStoreById method in
     *     StoreLocationController
     * </p>
     * @throws NotFoundException throws if store not found for given id
     */
    @Test
    public void testViewStoreById() throws NotFoundException {
        Integer storeId = 1 ;
        StoreResponseDto store=
                new StoreResponseDto(1, 600001, "Guindy");
        when(storeService.getStoreResponseById(storeId)).thenReturn(store);
        assertEquals(storeId, storeLocationController.viewStoreById(storeId).getId());
    }

    /**
     * <p>
     *     This method is used to test updateStore
     *     method in StoreLocationController
     * </p>
     * @throws ExistedException throws if new given data is already exist
     * @throws NotFoundException throws if store not found for given id
     */
    @Test
    public void testUpdateStore() throws ExistedException, NotFoundException {
        Integer storeId = 1;
        StoreRequestDto storeRequestDto
                = new StoreRequestDto(600001, "Guindy");
        SuccessResponseDto successDto
                = new SuccessResponseDto(200,
                                 "Store Updated Successfully");
        when(storeService.modifyStore(storeRequestDto, storeId)).thenReturn(successDto);
        SuccessResponseDto result = storeLocationController.updateStore(storeRequestDto,
                                                                        storeId);
        assertEquals(successDto.getStatusCode(), result.getStatusCode());
    }

    /**
     * <p>
     *     This method is used to test deleteStore method
     *     in StoreLocationController
     * </p>
     * @throws NotFoundException throws if store not found for given id
     */
    @Test
    public void testDeleteStore() throws NotFoundException {
        Integer storeId = 1;
        SuccessResponseDto successDto
                = new SuccessResponseDto(200, "Store Deleted Successfully");
        when(storeService.removeStore(storeId)).thenReturn(successDto);
        SuccessResponseDto result = storeLocationController.deleteStore(storeId);
        assertEquals(successDto.getStatusCode(), result.getStatusCode());
    }
}
