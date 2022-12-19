/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

import java.util.List;

import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.dto.UserRequestDto;
import com.ideas2it.groceryshop.dto.UserResponseDto;
import com.ideas2it.groceryshop.dto.UserUpdateDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.model.User;

/**
 * <p>
 *     provides service for create, update,
 *     delete and view of user.
 * </p>
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
public interface UserService {

    /**
     * <p>
     *     Creates user profile.
     * </p>
     *
     * @param userRequestDto    - Contains user details.
     * @return                  - Contains success message and status code.
     * @throws ExistedException - If username already exist.
     */
    SuccessResponseDto addUser(UserRequestDto userRequestDto) throws ExistedException;

    /**
     * <p>
     *     Gets user by user id.
     * </p>
     *
     * @param id                 - To get user.
     * @return                   - Contains user detail
     * @throws NotFoundException - If user does not exist or inactive
     */
    UserResponseDto getUserById(Integer id) throws NotFoundException;

    /**
     * <p>
     *     Gets all users.
     * </p>
     *
     * @return userResponseDtoList - List of user.
     * @throws NotFoundException   - If no user found.
     */
    List<UserResponseDto> getAllUser() throws NotFoundException;

    /**
     * <p>
     *     Gets users by role name.
     * </p>
     *
     * @param name                 - To search users by role name.
     * @return userResponseDtoList - List of user.
     * @throws NotFoundException   - If no users not found.
     */
    List<UserResponseDto> getUserByRole(String name) throws NotFoundException;

    /**
     * <p>
     *     Deletes user by id.
     * </p>
     *
     * @param id                  - To delete user.
     * @return SuccessResponseDto - Contains success message and code.
     * @throws NotFoundException  - If user does not exist.
     */
    SuccessResponseDto deleteUserById(Integer id) throws NotFoundException;

    /**
     * <p>
     *     Gets userName by mobile number.
     *     Using Regex given string is validated as mobile number or username.
     *     If given string is mobileNumber using mobileNumber userName is
     *     retrieved and returned, else userName is returned.
     * </p>
     *
     * @param userNameOrMobileNumber - Contains username or mobileNumber.
     * @return userName              - username.
     */
    String getUserNameByMobileNumber(String userNameOrMobileNumber);

    /**
     * <p>
     *     Updates user by username.
     * </p>
     *
     * @param userUpdateDto       - Contains details to be updated.
     * @return SuccessResponseDto - Contains success message and status code.
     * @throws NotFoundException  - If user does not exist.
     */
    SuccessResponseDto updateUserByUserName(UserUpdateDto userUpdateDto)
                                            throws NotFoundException;

    /**
     * <p>
     *     Gets currently logged-in user profile.
     * </p>
     *
     * @return userResponseDto - Contains user details.
     */
    UserResponseDto getCurrentUserProfile();

    /**
     * <p>
     *      Gets currently logged-in user object from security context holder.
     * </p>
     *
     * @return user - Contains logged-in user details.
     */
    User getCurrentUser();
}