/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.dto.ProductResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.ProductService;


/**
 * <p>
 *      Provides APIs to  create, view, update and delete operations
 *      for the product.
 * </p>
 *
 * @author Ruban
 * @since  03-11-2022
 * @version  1.0
 *
 */
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;
    private final Logger logger;

    public ProductController(ProductService productService) {
        this.productService = productService;
        this.logger = LogManager.getLogger(ProductController.class);
    }

    /**
     * <p>
     *     Create and add product by using product request dto.
     * </p>
     *
     * @param productRequestDto  - Contains name, price, subCategory id, unit, perHead.
     * @return                   - Success message and status code.
     * @throws ExistedException  - If product already exists.
     * @throws NotFoundException - If category id not found.
     */
    @PostMapping
    public SuccessResponseDto addProduct(@Valid @RequestBody ProductRequestDto productRequestDto)
                                          throws ExistedException, NotFoundException {
        logger.debug("Entered into addProduct method in product controller");
        return productService.addProduct(productRequestDto);
    }

    /**
     * <p>
     *     Get products by using keyword.
     * </p>
     *
     * @param name               - Contains keywords.
     * @return                   - list of matched products.
     * @throws NotFoundException - If products not found.
     */
    @GetMapping("/search/{name}")
    public List<ProductResponseDto> getProductBySearch(@Valid @PathVariable("name") String name)
                                                       throws NotFoundException {
        logger.debug("Entered into searchProduct method in product controller");
        return productService.getProductsBySearch(name);
    }

    /**
     * <p>
     *     Get all products by category id.
     * </p>
     *
     * @param categoryId         - To find relevant category.
     * @return                   - List of products available in category.
     * @throws NotFoundException - If no products found.
     */
    @GetMapping("/category/{categoryId}")
    public List<ProductResponseDto> getProductsByCategory(@PathVariable("categoryId") Integer categoryId)
                                                           throws NotFoundException {
        logger.debug("Entered into getProductByCategory method in product controller");
        return productService.getProductsByCategoryId(categoryId);
    }

    /**
     * <p>
     *     Get all products of specific sub category by sub category id.
     * </p>
     *
     * @param subCategoryId      - To find particular sub category.
     * @return                   - List of products available in sub category.
     * @throws NotFoundException - If no products found.
     */
    @GetMapping("/category/subCategory/{subCategoryId}")
    public List<ProductResponseDto> getProductsBySubCategory(@PathVariable("subCategoryId")
                                                                 Integer subCategoryId)
                                                              throws NotFoundException {
        logger.debug("Entered into getProductBySubCategory method in product controller");
        return productService.getProductsBySubCategoryId(subCategoryId);
    }

    /**
     * <p>
     *     Get all available products.
     * </p>
     *
     * @return                   - list of product.
     * @throws NotFoundException - If no products found.
     */
    @GetMapping
    public List<ProductResponseDto> getProducts() throws NotFoundException {
        logger.debug("Entered into getProducts method in product controller");
        return productService.getProducts();
    }

    /**
     * <p>
     *     Get products in specific location by using location id.
     * </p>
     *
     * @param locationId         - To fetch product from that particular location.
     * @return                   - List of Product.
     * @throws NotFoundException - If no product found.
     */
    @GetMapping("/location/{locationId}")
    public List<ProductResponseDto> getProductsByLocation(@PathVariable("locationId") Integer locationId)
                                                          throws NotFoundException {
        logger.debug("Entered into getProductByLocationId method in product controller");
        return productService.getProductsByLocation(locationId);
    }

    /**
     * <p>
     *     Get product by product id.
     * </p>
     *
     * @param productId          - To fetch particular product.
     * @return product           - Contains product details.
     * @throws NotFoundException - If no product found.
     */
    @GetMapping("/productId/{productId}")
    public ProductResponseDto getProductById(@PathVariable("productId") Integer productId)
                                             throws NotFoundException {
        logger.debug("Entered into getProductById method in product controller");
        return productService.getProductById(productId);
    }

    /**
     * <p>
     *     Removes product by product id.
     * </p>
     *
     * @param productId          - To delete product.
     * @return                   - Success message and status code.
     * @throws NotFoundException - If product not found.
     */
    @DeleteMapping("/{productId}")
    public SuccessResponseDto deleteProductById(@PathVariable("productId") Integer productId)
            throws NotFoundException {
        logger.debug("Entered into deleteProduct method in product controller");
        return productService.deleteProductById(productId);
    }

    /**
     * <p>
     *     Updates specific product details
     * </p>
     *
     * @param productId          - To update product.
     * @param productRequestDto  - Contains values to be updated.
     * @return                   - Success message and status code.
     * @throws NotFoundException - If product not found.
     * @throws ExistedException  - If product field is already exists.
     */
    @PutMapping("/{productId}")
    public SuccessResponseDto updateProductById(@PathVariable("productId") Integer productId,
                                                @RequestBody ProductRequestDto productRequestDto)
                                                throws NotFoundException, ExistedException {
        logger.debug("Entered into updateProduct method in product controller");
        return productService.updateProductById(productId, productRequestDto);
    }
}
