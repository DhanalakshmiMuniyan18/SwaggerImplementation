/*
 * <p>
 *      Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.CartDetailRequestDto;
import com.ideas2it.groceryshop.dto.CartRequestDto;
import com.ideas2it.groceryshop.dto.CartResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.mapper.CartDetailMapper;
import com.ideas2it.groceryshop.mapper.CartMapper;
import com.ideas2it.groceryshop.model.Cart;
import com.ideas2it.groceryshop.model.CartDetail;
import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.model.User;
import com.ideas2it.groceryshop.repository.CartRepository;
import com.ideas2it.groceryshop.service.CartDetailService;
import com.ideas2it.groceryshop.service.CartService;
import com.ideas2it.groceryshop.service.ProductService;
import com.ideas2it.groceryshop.service.UserService;

import lombok.RequiredArgsConstructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 *     Provide implementation for services to add, update, view and delete products
 *     from and to cart based on currently logged-in user
 * </p>
 *
 * @author Mohammed Ammar
 * @since 05-11-2022
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final Logger logger = LogManager.getLogger(CartServiceImpl.class);
    private final CartRepository cartRepository;
    private final UserService userService;
    private final ProductService productService;
    private final CartDetailService cartDetailService;

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto addOrModifyCart(CartRequestDto cartRequest)
                                              throws NotFoundException, ExistedException {
        logger.debug("Entered addOrModifyCart method in CartServiceImpl");
        Cart cart = getActiveCartOfCurrentUser();
        if (cart == null) {
            cart = CartMapper.toCart(cartRequest);
            cart.setUser(userService.getCurrentUser());
        }
        List<CartDetail> cartDetails = addProductToCartDetail(cartRequest.getCartDetail(),
                                                              cart.getCartDetails());
        cart.setCartDetails(cartDetails);
        cart.setTotalPrice(calculateTotalPrice(cartDetails));
        cart.setTotalQuantity(calculateTotalQuantity(cartDetails));
        cartRepository.save(cart);
        logger.debug("product added to cart");
        return new SuccessResponseDto(201, "Product added to cart successfully");
    }

    /**
     * <p>
     *     Finds product by id and to verify if already exist in cart
     *     then add product details and calculated price to Cart
     * </p>
     *
     * @param cartDetailRequest - Contains product id and quantity to add into cart
     * @param cartDetails - Contains existing list of products added to cart as cartDetail
     * @return CartDetails - Contains updated list of products added to cart as cartDetail
     * @throws NotFoundException - If product not found
     * @throws ExistedException - If product already exist in cart
     */
    private List<CartDetail> addProductToCartDetail(CartDetailRequestDto cartDetailRequest,
                                                    List<CartDetail> cartDetails)
                                                    throws NotFoundException, ExistedException {
        logger.debug("Entered addProductToCartDetail private method in cartServiceImpl");
        for (CartDetail eachCartDetail : cartDetails) {
            if (eachCartDetail.getProduct().getId() == cartDetailRequest.getProductId()) {
                logger.error("already added to cart");
                throw new ExistedException("Already added to cart");
            }
        }
        Product product = productService.getProductByProductId(cartDetailRequest.getProductId());
        if (product == null) {
            logger.error("product not found");
            throw new NotFoundException("Product not found");
        }
        CartDetail cartDetail = CartDetailMapper.toCartDetail(cartDetailRequest);
        cartDetail.setProduct(product);
        cartDetail.setPrice(product.getPrice() * cartDetail.getQuantity());
        cartDetails.add(cartDetail);
        return cartDetails;
    }

    /**
     * <p>
     *     Calculates total price of all products available in cart
     * </p>
     *
     * @param cartDetails - Contains list of products added to cart
     * @return totalPrice - Calculated price of all products added to cart
     */
    private Float calculateTotalPrice(List<CartDetail> cartDetails) {
        logger.debug("Entered calculateTotalPrice private method in cartServiceImpl");
        Float totalPrice = 0F;
        for (CartDetail eachCartDetail : cartDetails) {
            totalPrice += eachCartDetail.getPrice();
        }
        return totalPrice;
    }

    /**
     * <p>
     *     Calculates total quantity of products added into cart
     * </p>
     *
     * @param cartDetails - Contains list of products added to cart
     * @return totalQuantity - Calculated quantity of all products added to cart
     */
    private Integer calculateTotalQuantity(List<CartDetail> cartDetails) {
        logger.debug("Entered calculateTotalQuantity private method in cartServiceImpl");
        Integer totalQuantity = 0;
        for (CartDetail eachCartDetail : cartDetails) {
            totalQuantity += eachCartDetail.getQuantity();
        }
        return totalQuantity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CartResponseDto getCart() throws NotFoundException {
        logger.debug("Entered getCart method in cartServiceImpl");
        Cart cart = getActiveCartOfCurrentUser();
        if (cart == null) {
            throw new NotFoundException("Cart Not Found");
        }
        return CartMapper.toCartResponse(cart);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto removeCart() {
        logger.debug("Entered removeCart method in cartServiceImpl");
        User user = userService.getCurrentUser();
        cartDetailService.removeCartDetailsByUserId(user.getId());
        cartRepository.deleteCartByUserId(user.getId());
        logger.debug("cart deleted successfully");
        return new SuccessResponseDto(200, "Cart deleted successfully");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto removeProductFromCart(Integer productId) throws NotFoundException {
        logger.debug("Entered removeProductFromCart method in cartServiceImpl");
        Cart cart = getActiveCartOfCurrentUser();
        if (cart == null) {
            logger.error("cart not found");
            throw new NotFoundException("Cart not found");
        }
        Cart updatedCart = deleteProduct(productId, cart);
        cartRepository.save(updatedCart);
        logger.debug("product from cart deleted successfully");
        return new SuccessResponseDto(200, "Product from cart deleted successfully");
    }

    /**
     * <p>
     *     Deletes particular product from cart based on given product id
     *     and to set new calculated price and quantity
     * </p>
     *
     * @param productId - To delete product from cart
     * @param cart - Contains list of products added to cart
     * @return cart - Modified cart after deletion
     */
    private Cart deleteProduct(Integer productId, Cart cart) throws NotFoundException {
        logger.debug("Entered deleteProduct private method in cartServiceImpl");
        boolean isDeleted = false;
        for (CartDetail eachCartDetail : cart.getCartDetails()) {
            if (eachCartDetail.getProduct().getId() == productId) {
                int itemsInCart = cart.getCartDetails().size();
                if (itemsInCart == 1) {
                    cart.setIsActive(false);
                }
                eachCartDetail.setIsActive(false);
                isDeleted = true;
                cart.setTotalPrice(cart.getTotalPrice() - eachCartDetail.getPrice());
                cart.setTotalQuantity(cart.getTotalQuantity() - eachCartDetail.getQuantity());
            }
        }
        if (!isDeleted) {
            logger.error("product not found");
            throw new NotFoundException("Product not found");
        }
        return cart;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto updateCart(CartRequestDto cartRequest) throws NotFoundException {
        logger.debug("Entered updateCartByUser method in cartServiceImpl");
        Integer newQuantity = cartRequest.getCartDetail().getQuantity();
        Integer productId = cartRequest.getCartDetail().getProductId();
        Cart cart = getActiveCartOfCurrentUser();
        if (cart == null) {
            logger.error("cart not found");
            throw new NotFoundException("Cart not found");
        }
        List<CartDetail> cartDetails = cart.getCartDetails();
        cartDetails.stream()
                   .filter(eachCartDetail -> eachCartDetail.getProduct().getId() == productId)
                   .forEach(updatedCartDetail -> {
                                Float productPrice = updatedCartDetail.getProduct().getPrice();
                                updatedCartDetail.setQuantity(newQuantity);
                                updatedCartDetail.setPrice(productPrice * newQuantity);
                            });
        cart.setTotalPrice(calculateTotalPrice(cartDetails));
        cart.setTotalQuantity(calculateTotalQuantity(cartDetails));
        cart.setCartDetails(cartDetails);
        cartRepository.save(cart);
        logger.debug("cart updated successfully");
        return new SuccessResponseDto(200, "Quantity updated successfully");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cart getActiveCartOfCurrentUser() {
        logger.debug("Entered getCartByCartId method in cartServiceImpl");
        User user = userService.getCurrentUser();
        Optional<Cart> cart = cartRepository.findByUserIdAndIsActive(user.getId(), true);
        if (cart.isEmpty()) {
            return null;
        }
        return cart.get();
    }
}
