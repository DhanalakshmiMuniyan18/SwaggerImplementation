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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ideas2it.groceryshop.audit.Audit;

/**
 * <p>
 *     It holds the category related information(like name, parentId) and
 *     also it is used to store and retrieve the category information to and from
 *     database through JpaRepository.
 * </p>
 *
 * @author Ruban
 * @version 1.0
 * @since 03-11-22
 **/
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class Category extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "name", length = 20, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category category;

    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT")
    private boolean isActive = true;
}
