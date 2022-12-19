/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * <p>
 *     Provide services to insert, update, retrieve and delete cart
 * </p>
 *
 * @author Mohammed Ammar
 * @since 05-11-2022
 * @version 1.0
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    /**
     * <p>
     *     Retrieves active or deleted cart of specific user by user's id,
     * </p>
     * @param userId - To fetch cart
     * @param isActive - To fetch active or deleted cart
     * @return Cart - Contains product detail added to cart
     */
    Optional<Cart> findByUserIdAndIsActive(Integer userId,
                                           Boolean isActive);


    /**
     * <p>
     *     Deletes cart of specific user based on user's id
     * </p>
     * @param userId - To delete cart
     */
    @Modifying
    @Transactional
    @Query("Update Cart c set c.isActive = false where c.user.id = ?1")
    void deleteCartByUserId(Integer userId);
}
