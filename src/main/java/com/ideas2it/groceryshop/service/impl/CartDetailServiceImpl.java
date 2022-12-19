/*
 * <p>
 *      Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.repository.CartDetailsRepository;
import com.ideas2it.groceryshop.service.CartDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 *     Provide implementation for services to delete products
 *     from cart based on currently logged-in user
 * </p>
 *
 * @author Mohammed Ammar
 * @since 05-11-2022
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class CartDetailServiceImpl implements CartDetailService {
    private final CartDetailsRepository cartDetailsRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeCartDetailsByUserId(Integer userId) {
        cartDetailsRepository.deleteCartDetailsByUserId(userId);
    }
}
