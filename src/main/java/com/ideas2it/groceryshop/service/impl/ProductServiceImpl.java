/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.dto.ProductResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.mapper.ProductMapper;
import com.ideas2it.groceryshop.model.Category;
import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.repository.CategoryRepository;
import com.ideas2it.groceryshop.repository.ProductRepository;
import com.ideas2it.groceryshop.repository.StockRepository;
import com.ideas2it.groceryshop.repository.StoreRepository;
import com.ideas2it.groceryshop.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *     Provides implementation to perform create, update, delete
 *     and view product operations.
 * </p>
 *
 * @author RUBAN
 * @version  1.0
 * @since 03-11-22
 */
@Service
@NoArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private StockRepository stockRepository;
    private StoreRepository storeRepository;
    private Logger logger;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, StockRepository stockRepository,
                              StoreRepository storeRepository, CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
        this.stockRepository = stockRepository;
        this.logger = LogManager.getLogger(ProductServiceImpl.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto addProduct(ProductRequestDto productRequestDto)
                                         throws ExistedException, NotFoundException {
        logger.debug("Entered into addProduct method in product service");
        if(productRepository.existsByName(productRequestDto.getName())) {
            throw new ExistedException("Product already added");
        }
        if(!categoryRepository.existsById(productRequestDto.getSubCategoryId())) {
            throw new NotFoundException("Subcategory id not found");
        }
        Product product = ProductMapper.toProduct(productRequestDto);
        Optional<Category> category = categoryRepository.findById(productRequestDto.getSubCategoryId());
        category.ifPresent(product::setSubCategory);
        productRepository.save(product);
        logger.debug("The addProduct method successfully executed");
        return new SuccessResponseDto(201, "Product added successfully");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductResponseDto> getProducts() throws NotFoundException {
        logger.debug("Entered into getProducts method in product service");
        List<Product> productList = productRepository.findAllAndIsActive(true);
        if (productList.isEmpty()) {
            throw new NotFoundException("Products not found");
        }
        List<ProductResponseDto> productResponse = new ArrayList<>();
        for (Product product : productList) {
            ProductResponseDto productResponseDto = ProductMapper.toProductDto(product);
            productResponse.add(productResponseDto);
        }
        logger.debug("The getProducts method successfully executed");
        return productResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductResponseDto getProductById(Integer productId) throws NotFoundException {
        logger.debug("Entered into getProductsById method in product service");
        Product product = productRepository.findByIdAndIsActive(productId, true);
        if (product == null) {
            throw new NotFoundException("Product not found");
        }
        logger.debug("The getProductsById method successfully executed");
        return ProductMapper.toProductDto(product);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductResponseDto> getProductsByCategoryId( Integer categoryId)
                                                            throws NotFoundException {
        logger.debug("Entered into getProductsByCategoryId method in product service");
        List<Product> products = productRepository.findProductsByCategoryIdAndIsActive( categoryId,
                true);
        if(products.isEmpty()) {
            throw new NotFoundException("Products not found, id invalid");
        }
        List<ProductResponseDto> productResponses = new ArrayList<>();
        for(Product product : products){
            ProductResponseDto productResponseDto = ProductMapper.toProductDto(product);
            productResponses.add(productResponseDto);
        }
        logger.debug("The getProductsByCategoryId method successfully executed");
        return productResponses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductResponseDto> getProductsBySearch(String name) throws NotFoundException {
        List<Product> products = productRepository.findProductBySearch(name);
        if (products.isEmpty()) {
            throw new NotFoundException("Products not found");
        }
        List<ProductResponseDto> productResponse = new ArrayList<>();
        for (Product product : products) {
            ProductResponseDto productResponseDto = ProductMapper.toProductDto(product);
            productResponse.add(productResponseDto);
        }
        return productResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductResponseDto> getProductsBySubCategoryId(Integer subCategoryId)
                                                               throws NotFoundException {
        logger.debug("Entered into getProductsBySubCategoryId method in product service");
        List<Product> products = productRepository.findProductsBySubCategoryIdAndIsActive(subCategoryId,
                true);
        if(products.isEmpty()) {
            throw new NotFoundException("Products not found, id invalid");
        }
        List<ProductResponseDto> productResponses = new ArrayList<>();
        for(Product product : products){
            ProductResponseDto productResponseDto = ProductMapper.toProductDto(product);
            productResponses.add(productResponseDto);
        }
        logger.debug("The getProductsBySubCategoryId method successfully executed");
        return productResponses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto deleteProductById(Integer id) throws NotFoundException {
        logger.debug("Entered into deleteProductsById method in product service");
        Product product = productRepository.findByIdAndIsActive(id, true);
        if (product == null) {
            throw new NotFoundException("product not found");
        }
        product.setActive(false);
        productRepository.save(product);
        logger.debug("The deleteProductById method successfully executed");
        return new SuccessResponseDto(200, "Product deleted successfully");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto updateProductById(Integer id, ProductRequestDto productRequestDto)
                                                throws NotFoundException, ExistedException {
        logger.debug("Entered into updateProductsById method in product service");
        Product product = productRepository.findByIdAndIsActive(id, true);
        if (product == null) {
            throw new NotFoundException("Product id not found");
        }
        if(productRepository.existsByNameAndPriceAndUnitAndPerHead(productRequestDto.getName(),
                                     productRequestDto.getPrice(), productRequestDto.getUnit(),
                                     productRequestDto.getPerHead())) {
            throw new ExistedException("Product already exist");
        }
        product.setName(productRequestDto.getName());
        product.setPrice(productRequestDto.getPrice());
        product.setUnit(productRequestDto.getUnit());
        productRepository.save(product);
        logger.debug("The updateProductById method successfully executed");
        return new SuccessResponseDto(200, "Product details updated successfully");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductResponseDto> getProductsByLocation(Integer locationId) throws NotFoundException {
        logger.debug("Entered into getProductsLocation method in product service");
        List<Product> products = productRepository.findAllAndIsActive(true);
        if (products.isEmpty()) {
            throw new NotFoundException("No products found");
        }
        Boolean location = storeRepository.existsByIdAndIsActive(locationId, true);
        if(!location) {
            throw new NotFoundException("Location id invalid");
        }
        List<ProductResponseDto> productResponses = new ArrayList<>();
        for (Product product : products) {
            ProductResponseDto productResponseDto = ProductMapper.toProductDto(product);
            Boolean isStockAvailable = stockRepository.
                                       existsByStoreLocationIdAndProductIdAndAvailableStockGreaterThan
                                       (locationId, product.getId(), 0);
            productResponseDto.setIsStockAvailable(isStockAvailable);
            productResponses.add(productResponseDto);
        }
        logger.debug("The getProductsByLocation method successfully executed");
        return productResponses;
    }

    /**
     *{@inheritDoc}
     */
    public Product getProductByProductId(Integer id) {
        return productRepository.findByIdAndIsActive(id, true);
    }
}

