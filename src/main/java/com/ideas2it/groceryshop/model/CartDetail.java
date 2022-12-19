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

import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ideas2it.groceryshop.audit.Audit;

/**
 * <p>
 *     It holds the CartDetails related information(like product, price
 *     and quantity etc.,) and also it is used to store and retrieve the cart details
 *     to and from database through JpaRepository
 * </p>
 *
 * @author Mohammed Ammar
 * @version 1.0
 * @since 02-11-2022
 */
@Entity
@Table(name = "cart_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_active = 1")
public class CartDetail extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false, precision = 2)
    private Float price;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT")
    private Boolean isActive = true;

    @ManyToOne
    private Cart cart;
}
