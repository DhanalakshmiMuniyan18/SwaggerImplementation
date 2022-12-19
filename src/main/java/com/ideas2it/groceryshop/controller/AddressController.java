/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.groceryshop.dto.AddressResponseDto;
import com.ideas2it.groceryshop.dto.AddressRequestDto;
import com.ideas2it.groceryshop.dto.AddressUpdateRequestDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.AddressService;

/**
 * <p>
 *    Provides APIs to create, delete, view and update address.
 * </p>
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
@RestController
@RequestMapping("api/v1/users/addresses")
public class AddressController {

    private final AddressService addressService;
    private final Logger logger = LogManager.getLogger(AddressController.class);

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * <p>
     *     Creates address for currently logged-in user.
     * </p>
     *
     * @param addressRequestDto  - Contains address.
     * @return                   - Success message and status code.
     */
    @PostMapping
    public SuccessResponseDto createAddress(@Valid @RequestBody
                                            AddressRequestDto addressRequestDto) {
        logger.debug("Entered createAddress method");
        return addressService.addAddress(addressRequestDto);
    }

    /**
     * <p>
     *     Gets all address of a currently logged-in user.
     * </p>
     *
     * @return                   - List of address.
     * @throws NotFoundException - If no address found.
     */
    @GetMapping
    public List<AddressResponseDto> viewAddressesByUserId() throws NotFoundException {
        logger.debug("Entered viewAddressesByUserId method");
        return addressService.getAddressesByUserId();
    }

    /**
     * <p>
     *     Removes address using address id.
     * </p>
     *
     * @param id                 - Address id.
     * @return                   - Contains success message and status code.
     * @throws NotFoundException - If no address found.
     */
    @DeleteMapping("/{addressId}")
    public SuccessResponseDto deleteAddressById(@Valid @PathVariable("addressId")
                                                Integer id) throws NotFoundException {
        logger.debug("Entered deleteAddressById method");
        return addressService.deleteAddressById(id);
    }

    /**
     * <p>
     *     Updates address of currently logged-in user by address id.
     * </p>
     *
     * @return                    - Contains success message and status code.
     * @throws NotFoundException  - If address not found.
     */
    @PutMapping("/{addressId}")
    public SuccessResponseDto updateAddressByAddressId
                              (@RequestBody AddressUpdateRequestDto addressUpdateRequestDto,
                              @PathVariable("addressId") Integer id) throws NotFoundException {
        logger.debug("Entered UpdateAddressByAddressId method");
        return addressService.updateAddressByAddressId
                (addressUpdateRequestDto, id);
    }
}