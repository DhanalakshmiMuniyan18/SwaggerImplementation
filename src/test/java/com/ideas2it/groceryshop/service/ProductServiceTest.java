/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.ProductRequestDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.mapper.ProductMapper;
import com.ideas2it.groceryshop.model.Category;
import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.repository.CategoryRepository;
import com.ideas2it.groceryshop.repository.ProductRepository;
import com.ideas2it.groceryshop.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ProductServiceTest.class})
public class ProductServiceTest {
    @Mock
    ProductRepository productRepo;

    @Mock
    CategoryRepository categoryRepo;

    @InjectMocks
    private ProductService productService = new ProductServiceImpl();

    @Test
    @Order(2)
    public void test_getProducts() throws NotFoundException {
        Category category1 = new Category(1,"fruits & vegetables", null, true);
        Category category = new Category(1,"vegetables", category1, true);
        Product product = new Product(1,"Tomato", 100, true, category, "kg",
                1, "pic");
        List<Product> products = new ArrayList<>();
        products.add(product);
        SuccessResponseDto successResponseDto = new SuccessResponseDto(201,
                "product added4");
        when(productRepo.findAllAndIsActive(true)).thenReturn(products);
        assertEquals(201,successResponseDto.getStatusCode());
    }

    @Test
    @Order(1)
    public void test_addProduct() throws NotFoundException, ExistedException {
        ProductRequestDto productRequestDto = new ProductRequestDto(1, "Grapes", 100,
                "kg",1,1, "pic");
        Product product = ProductMapper.toProduct(productRequestDto);
        Category category = new Category();
        when(categoryRepo.findById(productRequestDto.getSubCategoryId())).thenReturn
                (Optional.of(category));
        when(categoryRepo.existsById(productRequestDto.getSubCategoryId())).thenReturn(true);
        when(productRepo.save(product)).thenReturn(product);
        assertEquals(201,productService.addProduct(productRequestDto).getStatusCode());
    }

    @Test
    @Order(4)
    public void test_updateProduct() throws ExistedException, NotFoundException {
        ProductRequestDto productRequestDto = new ProductRequestDto(1, "PineApple",
                150, "kg", 1, 1, "pic");
        Product product = new Product(1, "apple", 100, true, null,
                "kg", 1, "pic");
        product.setName(productRequestDto.getName());
        when(productRepo.findByIdAndIsActive(1, true)).thenReturn(product);
        assertEquals("Product details updated successfully", productService.updateProductById
                (1, productRequestDto).getMessage());
    }

    @Test
    @Order(5)
    public void test_deleteProduct() throws NotFoundException {
        Product product = new Product(1, "apple", 100, true, null, "kg",
                1, "pic");
        product.setActive(false);
        when(productRepo.findByIdAndIsActive(2, true)).thenReturn(product);
        assertEquals(201, productService.deleteProductById(2).getStatusCode());
    }
}

