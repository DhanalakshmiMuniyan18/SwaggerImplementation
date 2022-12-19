/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.mapper;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.groceryshop.dto.AddressRequestDto;
import com.ideas2it.groceryshop.dto.AddressResponseDto;
import com.ideas2it.groceryshop.dto.AddressUpdateRequestDto;
import com.ideas2it.groceryshop.model.Address;

/**
 * <p>
 *     Convert data transfer object into model and vice versa
 * </p>
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
public class AddressMapper {

    /**
     * <p>
     *      Convert address dto into address object and return address object
     * </p>
     *
     * @param addressRequestDto - Contains address detail
     * @return address - Contains address detail
     */
    public static Address addressDtoToAddress(AddressRequestDto addressRequestDto) {
        Address address = new Address();
        address.setStreet(addressRequestDto.getStreet());
        address.setArea(addressRequestDto.getArea());
        address.setPinCode(addressRequestDto.getPinCode());
        address.setLandMark(addressRequestDto.getLandMark());
        return address;
    }

    /**
     * <p>
     *     Convert address object into address response Dto
     *     and return AddressResponseDto
     * </p>
     *
     * @param address - Contains address detail
     * @return addressResponseDto - Contains address detail
     */
    public static AddressResponseDto addressResponseDto(Address address) {
        AddressResponseDto addressResponseDto = new AddressResponseDto();
        addressResponseDto.setId(address.getId());
        addressResponseDto.setStreet(address.getStreet());
        addressResponseDto.setArea(address.getArea());
        addressResponseDto.setPinCode(address.getPinCode());
        addressResponseDto.setLandMark(address.getLandMark());
        addressResponseDto.setCreatedAt(address.getCreatedAt());
        addressResponseDto.setModifiedAt(address.getModifiedAt());
        addressResponseDto.setModifiedBy(address.getModifiedBy());
        addressResponseDto.setIsActive(address.getIsActive());
        addressResponseDto.setIsDefault(address.getIsDefault());
        return addressResponseDto;
    }

    /**
     * <p>
     *     Convert list of Address object into list of address response Dto
     *     and return AddressResponseDto
     * </p>
     *
     * @param addressList - Contains list of address object
     * @return responseDtoList - Contains list of address response Dto
     */
    public static List<AddressResponseDto> addressResponseDtoList(List<Address> addressList) {
        List<AddressResponseDto> responseDtoList = new ArrayList<>();
        for(Address address : addressList) {
            responseDtoList.add(addressResponseDto(address));
        }
        return responseDtoList;
    }

    /**
     * <p>
     *     Convert addressUpdateRequestDto to Address object and return address object
     * </p>
     *
     * @param addressUpdateRequestDto - Contains address to be updated
     * @param address - Contains old address
     * @return Address - Contains updated address
     */
    public static Address addressUpdateRequestDtoToAddress
                                           (AddressUpdateRequestDto addressUpdateRequestDto,
                                           Address address){
        address.setStreet(addressUpdateRequestDto.getStreet());
        address.setArea(addressUpdateRequestDto.getArea());
        address.setIsDefault(addressUpdateRequestDto.getIsDefault());
        address.setPinCode(addressUpdateRequestDto.getPinCode());
        address.setLandMark(addressUpdateRequestDto.getLandMark());
        return address;
    }
}