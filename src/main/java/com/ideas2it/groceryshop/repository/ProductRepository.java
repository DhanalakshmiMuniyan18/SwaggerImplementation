/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.repository;

import com.ideas2it.groceryshop.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 *     Provides create, update, view and delete method for product.
 * </p>
 *
 * @author  RUBAN
 * @version  1.0
 * @since 05-11-22
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    /**
     * <p>
     *     To fetch specific product.
     * </p>
     *
     * @param productId - To find relevant product.
     * @param status    - To fetch active or inactive product.
     * @return          - Product details.
     */
    @Query(value = "select * from product where id = ?1 and is_active = ?2", nativeQuery = true)
    Product findByIdAndIsActive(Integer productId, Boolean status);

    /**
     * <p>
     *     To fetch all active products.
     * </p>
     *
     * @param status - To fetch active or inactive products.
     * @return       - list of product.
     */
    @Query(value = "select * from product where is_active = ?1", nativeQuery = true)
    List<Product> findAllAndIsActive(Boolean status);

    /**
     * <p>
     *     To fetch all products of specific category.
     * </p>
     *
     * @param categoryId - To get all products in category.
     * @param status     - To get active or inactive products.
     * @return           - list of product.
     */
    @Query(value ="select * from product p join category sc on sc.id = p.sub_category_id join " +
            "category c on c.id = sc.parent_id where c.id = ?1 and p.is_active = true",
            nativeQuery = true)
    List<Product> findProductsByCategoryIdAndIsActive(Integer categoryId, Boolean status);

    /**
     * <p>
     *     To fetch all products of specific sub category.
     * </p>
     *
     * @param subCategoryId - To get relevant products.
     * @param status        - To fetch active or inactive products.
     * @return              - list of product in sub category.
     */
    @Query(value = "select * from product where sub_category_id = ?1 and is_active = ?2",
            nativeQuery = true)
    List<Product> findProductsBySubCategoryIdAndIsActive(Integer subCategoryId, Boolean status);

    /**
     * <p>
     *     To check product details already exists or not.
     * </p>
     *
     * @param name  - To check name.
     * @param price - To check price.
     * @param unit  - To check unit.
     * @return      - If exists true otherwise false.
     */
    Boolean existsByNameAndPriceAndUnitAndPerHead(String name, Float price, String unit, Integer perHead);

    /**
     * <p>
     *     To check product name already exists or not.
     * </p>
     *
     * @param name - To check name already exists.
     * @return     - If exists true or-else false.
     */
    Boolean existsByName(String name);

    /**
     * <p>
     *     To fetch product by user keywords.
     * </p>
     *
     * @param name - To check whether it is exists or not.
     * @return     - list of product matched.
     */
    @Query(value = "select * from product where name LIKE %:name%" , nativeQuery = true)
    List<Product> findProductBySearch(String name);
}