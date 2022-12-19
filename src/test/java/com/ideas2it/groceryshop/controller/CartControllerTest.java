/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import com.ideas2it.groceryshop.dto.*;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.CartService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * <p>
 *     This is Test Class for Cart Controller
 *     used to test methods in controller
 * </p>
 * @author Mohammed Ammar
 * @since 15-11-2022
 * @version 1.0
 */
@SpringBootTest
public class CartControllerTest {

    @InjectMocks
    CartController cartController;

    @Mock
    CartService cartService;

    /**
     * <p>
     *     This method is used to test createCart() method
     *     in cart controller
     * </p>
     * @throws ExistedException - exception will be thrown if product already exists in Cart
     * @throws NotFoundException - exception will be thrown if product not found
     */
    @Test
    public void testCreateCart() throws ExistedException, NotFoundException {
        CartDetailRequestDto cartDetail
                = new CartDetailRequestDto(1, 10);
        CartRequestDto cartRequestDto
                = new CartRequestDto(cartDetail);
        SuccessResponseDto successDto
                = new SuccessResponseDto(201,"Cart created successfully");
        when(cartService.addOrModifyCart(cartRequestDto)).thenReturn(successDto);
        SuccessResponseDto result = cartController.createOrUpdateCart(cartRequestDto);
        assertEquals(successDto.getStatusCode(), result.getStatusCode());
    }

    /**
     * <p>
     *     This method is used to test viewCart method
     *     in Cart Controller
     * </p>
     * @throws NotFoundException - exception will be thrown if Cart not found (if it's admin userId)
     */
    @Test
    public void testViewCart() throws NotFoundException {
        CartResponseDto cartResponseDto = new CartResponseDto();
        cartResponseDto.setId(1);
        cartResponseDto.setCreatedAt(new Date(2022-12-2));
        cartResponseDto.setTotalPrice(200F);
        CartDetailResponseDto cartDetail = new CartDetailResponseDto();
        cartDetail.setProductName("GoodDay");
        cartDetail.setSubCategory("Cookies");
        cartDetail.setCategory("Biscuit");
        cartDetail.setPrice(100F);
        cartDetail.setQuantity(2);
        cartResponseDto.setCartDetails(List.of(cartDetail));
        when(cartService.getCart()).thenReturn(cartResponseDto);
        CartResponseDto result = cartController.viewCart();
        assertEquals(cartResponseDto.getId(), result.getId());
    }

    /**
     * <p>
     *     This method is used to test updateCart() method,
     *     in Cart Controller
     * </p>
     * @throws NotFoundException - exception will be thrown if Cart not found
     */
    @Test
    public void testUpdateCart() throws NotFoundException {
        CartDetailRequestDto cartDetail
                = new CartDetailRequestDto(1, 10);
        CartRequestDto cartRequestDto
                = new CartRequestDto(cartDetail);
        SuccessResponseDto successDto
                = new SuccessResponseDto(200,"Cart Updated successfully");
        when(cartService.updateCart(cartRequestDto)).thenReturn(successDto);
        SuccessResponseDto result = cartController.updateCart(cartRequestDto);
        assertEquals(successDto.getStatusCode(), result.getStatusCode());
    }

    /**
     * <p>
     *     This method is used to test deleteCart()
     *     method in Cart Controller
     * </p>
     * @throws NotFoundException - exception will be thrown if Cart not found
     */
    @Test
    public void testDeleteCart() throws NotFoundException {
        SuccessResponseDto successDto
                = new SuccessResponseDto(200,"Cart deleted successfully");
        when(cartService.removeCart()).thenReturn(successDto);
        SuccessResponseDto result = cartController.deleteCart();
        assertEquals(successDto.getStatusCode(), result.getStatusCode());
    }

    /**
     * <p>
     *     This method is used to test deleteProductFromCart()
     *     method in Cart Controller
     * </p>
     * @throws NotFoundException - exception will be thrown if Cart not found
     */
    @Test
    public void testDeleteProductFromCart() throws NotFoundException {
        Integer productId = 1;
        SuccessResponseDto successDto
                = new SuccessResponseDto(200,
                                 "Product from Cart deleted successfully");
        when(cartService.removeProductFromCart(productId)).thenReturn(successDto);
        SuccessResponseDto result = cartController.deleteProductFromCart(productId);
        assertEquals(successDto.getStatusCode(), result.getStatusCode());
    }
}
