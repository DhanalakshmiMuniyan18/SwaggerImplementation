/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

import java.util.List;

import com.ideas2it.groceryshop.dto.AddressResponseDto;
import com.ideas2it.groceryshop.dto.AddressRequestDto;
import com.ideas2it.groceryshop.dto.AddressUpdateRequestDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.NotFoundException;

/**
 * <p>
 *     Provides service for business logic, create, update,
 *     delete and view addresses of user.
 * </p>
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
public interface AddressService {

    /**
     * <p>
     *     Creates address to currently logged-in user.
     * </p>
     *
     * @param addressRequestDto - Contains address details.
     */
    SuccessResponseDto addAddress(AddressRequestDto addressRequestDto);

    /**
     * <p>
     *     Gets all address of existing user.
     * </p>
     *
     * @return addressResponseDtoList - Contains list of user address.
     * @throws NotFoundException      - If no address found.
     */
    List<AddressResponseDto> getAddressesByUserId() throws NotFoundException;

    /**
     * <p>
     *     Deletes address of user by address id.
     * </p>
     *
     * @param id                 - To delete address.
     * @throws NotFoundException - If no address found.
     */
    SuccessResponseDto deleteAddressById(Integer id) throws NotFoundException;

    /**
     * <p>
     *     Updates address of user by address id.
     * </p>
     *
     * @param addressUpdateRequestDto - contains updated address details.
     * @param id                      - To update Address.
     * @return                        - Contains success message and status code.
     * @throws NotFoundException      - If address not found.
     */
    SuccessResponseDto updateAddressByAddressId
                       (AddressUpdateRequestDto addressUpdateRequestDto, Integer id)
                        throws NotFoundException;
}