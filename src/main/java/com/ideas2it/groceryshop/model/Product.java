/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ideas2it.groceryshop.audit.Audit;

/**
 * <p>
 *     It holds the product related information(like name, price, unit, perHead) and
 *     also it is used to store and retrieve the product information to and from
 *     database through JpaRepository.
 * </p>
 *
 * @author Ruban
 * @version 1.0
 * @since 03-11-22
 *
 **/
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="product")
public class Product extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name ="price", nullable = false)
    private float price;

    @Column(name = "is_active", columnDefinition = "TINYINT")
    private boolean isActive = true;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="sub_category_id")
    private Category subCategory;

    @Column(name ="unit", length = 10)
    private String unit;

    @Column(name = "per_Head")
    private int perHead;

    @Column(name = "image")
    private String image;
}
