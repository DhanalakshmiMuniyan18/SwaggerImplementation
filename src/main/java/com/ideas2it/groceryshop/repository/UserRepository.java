/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ideas2it.groceryshop.model.User;

/**
 * <p>
 *     Provides service to store and retrieve data
 *     from User entity.
 * </p>
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * <p>
     *     Retrieves user by id and isActive.
     * </p>
     *
     * @param isActive - To check user is active.
     * @param id       - To find user.
     * @return         - Contains user details.
     */
    Optional<User> findByIsActiveAndId(Boolean isActive, Integer id);

    /**
     * <p>
     *      Retrieves all active users.
     * </p>
     *
     * @param isActive - To check active or inactive user.
     * @return         - list of active users.
     */
    List<User> findByIsActive(Boolean isActive);

    /**
     * </p>
     *      Retrieves user by Role name.
     * </p>
     *
     * @param isActive - To check active or inactive user.
     * @param name     - To get by role name.
     * @return         - list of user.
     */
    List<User> findByIsActiveAndRoleName(Boolean isActive, String name);

    /**
     * <p>
     *     Deletes user by user id.
     * </p>
     *
     * @param id - To make user disable.
     */
    @Modifying
    @Transactional
    @Query("update User set isActive = false where id = ?1")
    void disableUserById(Integer id);

    /**
     * <p>
     *     Checks if user exist.
     * </p>
     *
     * @param userName - To find user.
     * @return         - If user is found true or-else false.
     */
    boolean existsByUserName(String userName);

    /**
     * <p>
     *     Retrieves active user by id.
     * </p>
     *
     * @param username - To get user.
     * @param isActive - To check active or inactive user.
     * @return         - Contains user details.
     */
    Optional<User> findByUserNameAndIsActive(String username, boolean isActive);

    /**
     * <p>
     *      Retrieves user by mobile number.
     * </p>
     *
     * @param mobileNumber - To get username.
     * @param isActive     - To check active or inactive user.
     * @return             - Contains user details.
     */
    Optional<User> findUserByMobileNumberAndIsActive(Long mobileNumber , Boolean isActive);
}