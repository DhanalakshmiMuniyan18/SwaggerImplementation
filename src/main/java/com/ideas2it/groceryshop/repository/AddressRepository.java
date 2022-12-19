/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ideas2it.groceryshop.model.Address;

/**
 * <p>
 *     Provides service to store and retrieve data
 *     from Address entity.
 * </p>
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    /**
     * <p>
     *     Retrieves addresses of user by user id.
     * </p>
     *
     * @param isActive - To check active or inactive address.
     * @param id       - To get user addresses.
     * @return         - list of address.
     */
    List<Address> findByIsActiveAndUserId(Boolean isActive, Integer id);

    /**
     * <p>
     *     Deletes address by address id.
     * </p>
     *
     * @param id - To deactivate address.
     */
    @Modifying
    @Transactional
    @Query("update Address set isActive = false where id = ?1")
    void disableAddress(Integer id);

    /**
     * <p>
     *     Retrieves active address by address id.
     * </p>
     *
     * @param isActive - To check active or inactive address.
     * @param id       - To get address.
     * @return         - Contains address details.
     */
    Optional<Address> findByIsActiveAndId(Boolean isActive, Integer id);

    /**
     * <p>
     *     Gets address by active status, address id
     *     and user id.
     * </p>
     *
     * @param isActive  - To check active or inactive address.
     * @param addressId - To get address.
     * @param userId    - To get user address.
     * @return Address  - Contains address details.
     */
    Optional<Address> findByIsActiveAndIdAndUserId(Boolean isActive, Integer addressId,
                                                   Integer userId);
}