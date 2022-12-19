/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service.impl;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;
import com.ideas2it.groceryshop.dto.SubCategoryResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.mapper.CategoryMapper;
import com.ideas2it.groceryshop.model.Category;
import com.ideas2it.groceryshop.model.Product;
import com.ideas2it.groceryshop.repository.CategoryRepository;
import com.ideas2it.groceryshop.repository.ProductRepository;
import com.ideas2it.groceryshop.service.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;


/**
 * <p>
 *     Provides implementation to perform create, update, delete
 *     and view category operations.
 * </p>
 *
 * @author RUBAN
 * @version  1.0
 * @since 03-11-22
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final Logger logger;
    private final ProductRepository productRepository;

    public CategoryServiceImpl(ProductRepository productRepository,
                               CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.logger = LogManager.getLogger(CategoryServiceImpl.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto addCategory(CategoryRequestDto categoryRequestDto)
                                          throws ExistedException, NotFoundException {
        logger.debug("Entered into addCategory method in category service");
        if(categoryRepository.existsByName(categoryRequestDto.getName())) {
            throw new ExistedException("Category already added");
        }
        Category categories = CategoryMapper.toCategory(categoryRequestDto);
        if(categoryRequestDto.getParentId() != 0) {
            Optional<Category> category = categoryRepository.findById(categoryRequestDto.
                                                             getParentId());
            if(category.isPresent()) {
                categories.setCategory(category.get());
            } else  {
                throw new NotFoundException("Category not found");
            }
        }
        categoryRepository.save(categories);
        logger.debug("addCategory method successfully executed");
        if (categoryRequestDto.getParentId() == 0) {
            return new SuccessResponseDto(201, "Category added successfully");
        }
        return new SuccessResponseDto(201, "Sub category added successfully");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryResponseDto> getCategory() throws NotFoundException {
        logger.debug("Entered into getCategory method in category service");
        List<Category> categoriesList = categoryRepository.
                                        findCategoriesByParentIdAndIsActive( true);
        if (categoriesList.isEmpty()) {
            throw new NotFoundException("Category not added");
        }
        List<CategoryResponseDto> categoriesResponse = new ArrayList<>();
        for(Category category : categoriesList) {
            categoriesResponse.add(CategoryMapper.toCategoryDto(category));
        }
        logger.debug("getCategory method successfully executed");
        return categoriesResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SubCategoryResponseDto> getAllSubCategory() throws NotFoundException {
        logger.debug("Entered into getAllSubCategory method in category service");
        List<Category> categories = categoryRepository.
                                    findSubCategoriesByParentIdAndIsActive(true);
        if (categories.isEmpty()) {
            throw new NotFoundException("Sub category not added");
        }
        List<SubCategoryResponseDto> categoriesList = new ArrayList<>();
        for(Category category : categories) {
            categoriesList.add(CategoryMapper.toSubCategoryDto(category));
        }
        logger.debug("getAllSubCategory method executed successfully");
        return categoriesList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto deleteCategory(Integer categoryId) throws NotFoundException {
        logger.debug("Entered into deleteCategory method in category service");
        Category categories = categoryRepository.findCategoryByIdAndIsActive(categoryId, true);
        if (categories == null) {
            throw new NotFoundException("Category id not found");
        }
        categories.setActive(false);
        List<Category> subCategories = categoryRepository.
                                       findSubCategoriesByParentIdAndIsActive(categoryId, true);
        for (Category category : subCategories) {
            category.setActive(false);
            categoryRepository.save(category);
        }
        List<Product> products = productRepository.
                findProductsByCategoryIdAndIsActive(categoryId, true);
        for(Product product: products) {
            product.setActive(false);
            productRepository.save(product);
        }
        categoryRepository.save(categories);
        logger.debug("deleteCategory method successfully executed");
        return new SuccessResponseDto(200, "category deleted Successfully");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto deleteSubCategory(Integer parentId, Integer categoryId)
                                                throws NotFoundException {
        logger.debug("Entered into deleteSubCategory method in category service");
        Category category = categoryRepository.
                findSubCategoryByParentIdAndCategoryIdAndIsActive(parentId, categoryId, true);
        if (category == null) {
            throw new NotFoundException("Sub category id not found");
        }
        category.setActive(false);
        categoryRepository.save(category);
        List<Product> products = productRepository.
                findProductsBySubCategoryIdAndIsActive(categoryId, true);
        for(Product product: products) {
            product.setActive(false);
            productRepository.save(product);
        }
        logger.debug("deleteSubCategory method successfully executed");
        return new SuccessResponseDto(200, "Subcategory deleted successfully");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto updateCategory(Integer categoryId, CategoryRequestDto categoryRequestDto)
                                             throws NotFoundException, ExistedException {
        logger.debug("Entered into updateCategory method in category service");
        Category category = categoryRepository.findCategoryByIdAndIsActive(categoryId, true);
        if(category == null) {
            throw new NotFoundException("Category id not found");
        }
        if (categoryRepository.existsByName(categoryRequestDto.getName())) {
            throw new ExistedException("Category name already exist");
        }
        category.setName(categoryRequestDto.getName());
        categoryRepository.save(category);
        logger.debug("updateCategory method successfully executed");
        return new SuccessResponseDto(200, "Category details updated successfully");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SuccessResponseDto updateSubCategory(Integer parentId, Integer categoryId,
                                                CategoryRequestDto categoryRequestDto)
                                                throws NotFoundException, ExistedException {
        logger.debug("Entered into updateSubCategory method in category service");
        Category category = categoryRepository.
                findSubCategoryByParentIdAndCategoryIdAndIsActive(parentId, categoryId, true);
        if(category == null) {
            throw new NotFoundException("Subcategory id not found");
        }
        if (categoryRepository.existsByName(categoryRequestDto.getName())) {
            throw new ExistedException("Subcategory name already exist");
        }
        category.setName(categoryRequestDto.getName());
        categoryRepository.save(category);
        logger.debug("updateSubCategory method successfully executed");
        return new SuccessResponseDto(200, "Subcategory updated successfully");
    }
}
