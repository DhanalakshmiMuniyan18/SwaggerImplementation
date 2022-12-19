/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.dto.CategoryRequestDto;
import com.ideas2it.groceryshop.dto.CategoryResponseDto;
import com.ideas2it.groceryshop.dto.SubCategoryResponseDto;
import com.ideas2it.groceryshop.model.Category;

/**
 * <p>
 *      Converts category dto to category entity and vice versa.
 * </p>
 *
 * @author RUBAN
 * @version 1.0
 * @since 03-11-22
 */
public class CategoryMapper {

    /**
     * <p>
     *     Converts category request Dto to category entity.
     * </p>
     *
     * @param categoryRequestDto - Contains category name.
     * @return category - Contains category details.
     */
    public static Category toCategory(CategoryRequestDto categoryRequestDto) {
        Category category = new Category();
        category.setName(categoryRequestDto.getName());
        return category;
    }

    /**
     * <p>
     *     Converts category entity to category response dto.
     * </p>
     *
     * @param category - Contains category details.
     * @return CategoryResponseDto - Contains category id and name.
     */
    public static CategoryResponseDto toCategoryDto(Category category) {
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setId(category.getId());
        categoryResponseDto.setName(category.getName());
        return categoryResponseDto;
    }

    /**
     * <p>
     *     Converts category model to sub category response dto.
     * </p>
     *
     * @param category - Contains category details.
     * @return SubCategoryResponseDto - Contains category id, category name and sub category name.
     */
    public static SubCategoryResponseDto toSubCategoryDto(Category category) {
        SubCategoryResponseDto subCategoryResponseDto = new SubCategoryResponseDto();
        subCategoryResponseDto.setId(category.getId());
        subCategoryResponseDto.setName(category.getName());
        subCategoryResponseDto.setCategoryName(category.getCategory().getName());
        return subCategoryResponseDto;
    }
}
