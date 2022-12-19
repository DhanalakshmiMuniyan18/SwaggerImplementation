/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.StoreLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * <p>
 *     Provide services to insert, update, retrieve and delete Store Location
 *     (like area, pin code,. etc)
 * </p>
 *
 * @author Mohammed Ammar
 * @since 05-11-2022
 * @version 1.0
 */
@Repository
public interface StoreRepository extends JpaRepository<StoreLocation, Integer> {

    /**
     * <p>
     *     Retrieves active or deleted stores based on isActive
     * </p>
     *
     * @param isActive - To fetch active or inactive stores
     * @return list of stores  - Contains list of store details like area, pin code
     */
    List<StoreLocation> findByIsActive(Boolean isActive);

    /**
     * <p>
     *     Retrieves specific active or inactive store based on location id
     * </p>
     *
     * @param isActive - To fetch active or inactive store
     * @param id       - To fetch store by location
     * @return Store Location - Contains area and pin code
     */
    StoreLocation findByIsActiveAndId(Boolean isActive, Integer id);

    /**
     * <p>
     *     Deletes store location based on location id
     * </p>
     *
     * @param storeId - To delete store
     * @return - number of rows affected
     */
    @Modifying
    @Transactional
    @Query("UPDATE StoreLocation SET isActive = false WHERE id = ?1")
    Integer deleteStoreById(Integer storeId);

    /**
     * <p>
     *     To check if area or pin code is already exist in database or not
     * </p>
     *
     * @param area    - To check store by area
     * @param pinCode - To check store
     * @return true if exists else false
     */
    Boolean existsByAreaOrPinCode(String area, Integer pinCode);

    /**
     * <p>
     *     To check if location id is already exist and active or not in database
     * </p>
     *
     * @param locationId - To check for store by location
     * @param status     - To check for store by active or inactive
     * @return - true if store exist else false
     */
    Boolean existsByIdAndIsActive(Integer locationId, Boolean status);

    /**
     * <p>
     *     Retrieves active or deleted Store based on Pin code
     * </p>
     *
     * @param status  - To fetch store by active or deleted
     * @param pinCode - To fetch store
     * @return storeLocation - Contains area and pinCode
     */
    StoreLocation findByIsActiveAndPinCode(Boolean status, Integer pinCode);

    /**
     * <p>
     *     To check if area or pin code is already exist or not except the given id
     * </p>
     *
     * @param area    - To check store by area name
     * @param pinCode - To check store by pin code number
     * @param storeId - To check store exclude by location
     * @return - true if exist else false
     */
    Boolean existsByAreaOrPinCodeAndIdNot(String area, Integer pinCode, Integer storeId);
}
