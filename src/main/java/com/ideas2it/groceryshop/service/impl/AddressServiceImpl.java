/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.groceryshop.dto.AddressResponseDto;
import com.ideas2it.groceryshop.dto.AddressRequestDto;
import com.ideas2it.groceryshop.dto.AddressUpdateRequestDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.repository.AddressRepository;
import com.ideas2it.groceryshop.service.AddressService;
import com.ideas2it.groceryshop.service.UserService;
import com.ideas2it.groceryshop.mapper.AddressMapper;
import com.ideas2it.groceryshop.model.Address;

/**
 * <p>
 *     Provides implementation services for business logic, create, update,
 *     delete and view addresses of user.
 *     Data transfer object(Dto) are converted into model object using mapper
 *     for storing in database and vice versa.
 * </p>
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserService userService;
    private final Logger logger = LogManager.getLogger(AddressServiceImpl.class);

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository,
                              UserService userService) {
        this.addressRepository = addressRepository;
        this.userService = userService;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public SuccessResponseDto addAddress(AddressRequestDto addressRequestDto) {
        logger.debug("Entered addAddress method");
        Address address = AddressMapper.addressDtoToAddress(addressRequestDto);
        List<Address> addresses =
                addressRepository.findByIsActiveAndUserId(true,
                        userService.getCurrentUser().getId());
        if(addresses.isEmpty()) {
            address.setIsDefault(Boolean.TRUE);
        } else {
            address.setIsDefault(Boolean.FALSE);
        }
        address.setUser(userService.getCurrentUser());
        addressRepository.save(address);
        logger.debug("Address added successfully");
        return new SuccessResponseDto(201,
                "Address added successfully");
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<AddressResponseDto> getAddressesByUserId() throws NotFoundException {
        logger.debug("Entered getAddressesByUserId method");
        Integer userId = userService.getCurrentUser().getId();
        List<Address> address =
                addressRepository.findByIsActiveAndUserId(true, userId);
        if(address.isEmpty()){
            logger.debug("Address not found");
            throw new NotFoundException("Address not found");
        }
        List<AddressResponseDto> addressResponseDtoList =
               AddressMapper.addressResponseDtoList(address);
        logger.debug("Got list of users");
        return addressResponseDtoList;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public SuccessResponseDto deleteAddressById(Integer id) throws NotFoundException {
        logger.debug("Entered deleteAddressById method");
        Optional<Address> address =
                addressRepository.findByIsActiveAndId(true, id);
        if(address.isEmpty()) {
            logger.debug("Address not found");
            throw new NotFoundException("Address not found");
        }
        addressRepository.disableAddress(id);
        logger.debug("Address deleted successfully");
        return new SuccessResponseDto(200,"Address deleted successfully");
    }

    /**
     *{@inheritDoc}
     */
    public SuccessResponseDto updateAddressByAddressId
            (AddressUpdateRequestDto addressUpdateRequestDto,
             Integer id) throws NotFoundException{
        logger.debug("Entered updateAddressByAddressId method");
        Optional<Address> address =
                addressRepository.findByIsActiveAndIdAndUserId(true,
                id, userService.getCurrentUser().getId());
        if(address.isEmpty()) {
            logger.debug("Address not found");
            throw new NotFoundException("Address not found");
        }
        Address updatedAddress = AddressMapper.addressUpdateRequestDtoToAddress
                                       (addressUpdateRequestDto, address.get());
        addressRepository.save(updatedAddress);
        logger.debug("Address Updated successfully");
        return new SuccessResponseDto(200,
                "Address updated successfully");
    }

    /**
     *{@inheritDoc}
     */
    public Optional<Address> getAddressByAddressId(Integer id) {
        logger.debug("getAddressByAddressId");
        return addressRepository.findByIsActiveAndIdAndUserId(true, id,
                userService.getCurrentUser().getId());
    }
}