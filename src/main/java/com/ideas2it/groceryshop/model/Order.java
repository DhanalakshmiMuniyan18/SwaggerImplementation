/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;

import com.ideas2it.groceryshop.audit.Audit;

/**
 * <p>
 *     It holds all order related information like(orderedDate, totalPrice,
 *     totalQuantity, orderDetails, isActive, users) and it is also used to
 *     store and retrieve order information from and to database.
 * </p>
 *
 * @author   Dhanalakshmi.M
 * @version  1.0
 * @since    18-11-2022
 */
@Entity
@Table(name = "order_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ordered_date")
    @CreationTimestamp
    private Date orderedDate;

    @Column(name = "total_price", nullable = false)
    private Float totalPrice;

    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;

    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT")
    private Boolean isActive = Boolean.TRUE;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private List<OrderDetail> orderDetails;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user ;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private OrderDelivery orderDelivery;
}