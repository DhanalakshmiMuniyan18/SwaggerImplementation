/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

/**
 * <p>
 *     It holds the user related information(like username, mobile number details etc.,) and
 *     also it is used to store and retrieve the user information to and from
 *     database through JpaRepository
 * </p>
 *
 * @version 1.0
 * @author Rohit A P
 * @since 31-10-2022
 */
@Entity
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name", length = 20 , nullable = false, unique = true)
    private String userName;

    @Column(name = "first_name", length = 20 , nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 20 , nullable = false)
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "mobile_number", length = 10, nullable = false, unique=true)
    private Long mobileNumber;

    @Column(name = "email", length = 50, nullable = false, unique=true)
    private String email;

    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT")
    private Boolean isActive = Boolean.TRUE;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id", columnDefinition = "role_id")
    private Role role;

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

    @PostPersist
    public void setCreator() {
        createdBy = id;
    }
    @PostUpdate
    public void setModifier() {
        modifiedBy = id;
    }
}