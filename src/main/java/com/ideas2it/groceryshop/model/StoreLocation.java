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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

import com.ideas2it.groceryshop.audit.Audit;

/**
 * <p>
 *     It holds the Store Location related information(like area, pinCode details etc.,)
 *     and also it is used to store and retrieve the store information to and from
 *     database through JpaRepository
 * </p>
 *
 * @author Mohammed Ammar
 * @version 1.0
 * @since 02-11-2022
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "store_location")
public class StoreLocation extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pin_code", nullable = false)
    private Integer pinCode;

    @Column(name = "area", length = 20,
            nullable = false, unique = true)
    private String area;

    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT")
    private Boolean isActive = true;

    @ManyToMany
    @JoinTable(name = "stock", joinColumns = @JoinColumn(name = "location_id"),
               inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;
}
