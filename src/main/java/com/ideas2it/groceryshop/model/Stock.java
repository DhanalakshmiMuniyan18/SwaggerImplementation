/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.model;

import com.ideas2it.groceryshop.audit.Audit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * <p>
 *     It holds the stock related information(like storeLocation, product,
 *     availableStock etc.,) and also it is used to store and retrieve the
 *     Stock information to and from database through JpaRepository
 * </p>
 *
 * @author Mohammed Ammar
 * @version 1.0
 * @since 07-11-2022
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stock")
public class Stock extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "available_stock", nullable = false)
    private Integer availableStock;

    @Column(name = "unit", length = 10, nullable = false)
    private String unit;

    @ManyToOne()
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT")
    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private StoreLocation storeLocation;
}
