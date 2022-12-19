/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

import java.util.List;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;
import com.ideas2it.groceryshop.dto.SubCategoryResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;

/**
 * <p>
 *     Provides create, view, update and delete operation service for
 *     category.
 * </p>
 *
 * @author RUBAN
 * @version  1.0
 * @since 03-11-22
 */
public interface CategoryService {

    /**
     * <p>
     *     Create category by using category request dto.
     * </p>
     *
     * @param categoryRequestDto - Contains category details.
     * @return                   - Success message and status code.
     * @throws ExistedException  - If category is already added.
     * @throws NotFoundException - If category not found
     */
    SuccessResponseDto addCategory(CategoryRequestDto categoryRequestDto)
            throws ExistedException, NotFoundException;

    /**
     * <p>
     *     Get available categories.
     * </p>
     *
     * @return                   - List of category.
     * @throws NotFoundException - If category not found.
     */
    List<CategoryResponseDto> getCategory() throws NotFoundException;

    /**
     * <p>
     *     Get all available sub categories.
     * </p>
     *
     * @return                   - List of sub category.
     * @throws NotFoundException - If sub category not found.
     */
    List<SubCategoryResponseDto> getAllSubCategory() throws NotFoundException;

    /**
     * <p>
     *     Deletes category by using category id.
     * </p>
     *
     * @param categoryId         - To fetch relevant category.
     * @return                   - Success message and status code.
     * @throws NotFoundException - If category not found.
     */
    SuccessResponseDto deleteCategory(Integer categoryId) throws NotFoundException;

    /**
     * <p>
     *     Deletes sub category by using sub category id.
     * </p>
     *
     * @param parentId           - To find relevant sub category.
     * @param categoryId         - To delete sub category.
     * @return                   - Success message and status code.
     * @throws NotFoundException - If sub category not found.
     */
    SuccessResponseDto deleteSubCategory(Integer parentId, Integer categoryId) throws NotFoundException;

    /**
     * <p>
     *     Updates category fields by using category request dto and category id.
     * </p>
     *
     * @param categoryId         - To find which object to update
     * @param categoryRequestDto - Contains name to get update.
     * @return                   - Success message and status code.
     * @throws ExistedException  - If category fields already exists.
     * @throws NotFoundException - If category not found.
     */
    SuccessResponseDto updateCategory(Integer categoryId, CategoryRequestDto categoryRequestDto)
            throws ExistedException, NotFoundException;

    /**
     * <p>
     *     Updates sub category fields by using sub category request dto and sub category id.
     * </p>
     *
     * @param categoryId         - To find relevant sub category.
     * @param parentId           - To fetch sub category.
     * @param categoryRequestDto - Contains category name, parentId to be updated.
     * @return                   - Success message and status code.
     * @throws ExistedException  - If sub category fields already exists.
     * @throws NotFoundException - If sub category not found.
     */
    SuccessResponseDto updateSubCategory(Integer parentId, Integer categoryId, CategoryRequestDto
            categoryRequestDto) throws NotFoundException, ExistedException;
}
