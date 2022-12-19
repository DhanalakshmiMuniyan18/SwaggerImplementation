/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.ideas2it.groceryshop.model.Category;

/**
 * <p>
 *     Provides create, update, view and delete method for category.
 * </p>
 *
 * @author  RUBAN
 * @version  1.0
 * @since 05-11-22
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /**
     * <p>
     *     To fetch all active categories.
     * </p>
     *
     * @param status - To fetch active or inactive categories
     * @return       - list of category
     */
    @Query(value ="select * from category where parent_id is null and is_active = ?1", nativeQuery = true)
    List<Category> findCategoriesByParentIdAndIsActive( Boolean status);

    /**
     * <p>
     *     To fetch all active sub categories.
     * </p>
     *
     * @param status - To get active or inactive sub categories.
     * @return       - list of sub category.
     */
    @Query(value = "select * from category where parent_id is not null and is_active = ?1",
            nativeQuery = true)
    List<Category> findSubCategoriesByParentIdAndIsActive(Boolean status);

    /**
     * <p>
     *     To fetch relevant active category by id.
     * </p>
     *
     * @param categoryId - To fetch relevant category.
     * @param status     - To fetch active or inactive category
     * @return           - Category details.
     */
    @Query(value = "select * from category where id = ?1 and is_active = ?2", nativeQuery = true)
    Category findCategoryByIdAndIsActive(Integer categoryId, Boolean status);

    /**
     * <p>
     *     To fetch active sub category by parentId and categoryId.
     * </p>
     *
     * @param parentId   - To ensure it is sub category.
     * @param categoryId - To get sub category.
     * @param status     - To get active or inactive sub category.
     * @return           - Sub category details.
     */
    @Query(value = "select * from category where parent_id = ?1 and id = ?2 and is_active = ?3",
            nativeQuery = true)
    Category findSubCategoryByParentIdAndCategoryIdAndIsActive(Integer parentId, Integer categoryId,
                                                               Boolean status);
    /**
     * <p>
     *     To fetch active sub categories by parent id.
     * </p>
     *
     * @param subCategoryId - To fetch relevant sub categories.
     * @param status        - To fetch active or inactive sub categories.
     * @return              - list of sub category.
     */
    @Query(value ="select * from category where parent_id = ?1 and is_active = ?2", nativeQuery = true)
    List<Category> findSubCategoriesByParentIdAndIsActive(Integer subCategoryId, Boolean status);

    /**
     * <p>
     *     To check category name already exist or not.
     * </p>
     *
     * @param name - To check name exist or not
     * @return     - If name exist return true or-else false
     */
    Boolean existsByName(String name);
}



