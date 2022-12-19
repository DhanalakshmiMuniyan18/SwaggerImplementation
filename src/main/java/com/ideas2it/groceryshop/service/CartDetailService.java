/*
 * <p>
 *      Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

/**
 * <p>
 *     Provides service to delete products from cart details
 * </p>
 *
 * @author Mohammed Ammar
 * @since 05-11-2022
 * @version 1.0
 */
public interface CartDetailService {

    /**
     * <p>
     *     Deletes all products from user's cart based on user's id
     * </p>
     *
     * @param userId - To delete products from user's cart
     */
    void removeCartDetailsByUserId(Integer userId);
}
