/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.audit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * <p>
 *      It holds auditing information like created by, modified by, created at and modified at
 *      to store and retrieve for auditing purpose
 * </p>
 *
 * @author Mohammed Ammar
 * @version 1.0
 * @since 15-11-2022
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Audit {

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @CreatedBy
    @Column(name = "created_by")
    private Integer createdBy;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private Date modifiedAt;

    @LastModifiedBy
    @Column(name = "modified_by")
    private Integer modifiedBy;
}