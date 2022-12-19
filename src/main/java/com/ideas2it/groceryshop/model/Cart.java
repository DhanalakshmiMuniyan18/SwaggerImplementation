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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

import com.ideas2it.groceryshop.audit.Audit;

/**
 * <p>
 *     It holds the cart related information(like totalPrice, product details etc.,) and
 *     also it is used to store and retrieve the cart information to and from
 *     database through JpaRepository
 * </p>
 *
 * @author Mohammed Ammar
 * @version 1.0
 * @since 02-11-2022
 */
@Entity
@Table(name = "cart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "is_active = 1")
public class Cart extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "total_price", nullable = false, precision = 2)
    private Float totalPrice;

    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private List<CartDetail> cartDetails;

    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT")
    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
