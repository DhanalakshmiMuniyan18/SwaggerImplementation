/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.dto.UserRequestDto;
import com.ideas2it.groceryshop.dto.UserResponseDto;
import com.ideas2it.groceryshop.dto.UserUpdateDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.service.UserService;

/**
 * <p>
 *     Provides APIs to create, delete, update and view users.
 * </p>
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;
    private final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * <p>
     *     Creates user profile by getting user details.
     * </p>
     *
     * @param userRequestDto      - Contains user detail.
     * @return SuccessResponseDto - Contains success message and status code.
     * @throws ExistedException   - If username already exist.
     */
    @PostMapping
    public SuccessResponseDto createUser(@Valid @RequestBody UserRequestDto userRequestDto)
                                         throws ExistedException {
        logger.debug("Entered createUser method");
        return userService.addUser(userRequestDto);
    }

    /**
     * <p>
     *     Gets user by id.
     * </p>
     *
     * @param id                 - To get user.
     * @return userResponseDto   - Contains user details.
     * @throws NotFoundException - If user does not exist.
     */
    @GetMapping("/{userId}")
    public UserResponseDto getUserById(@PathVariable("userId") Integer id)
                                       throws NotFoundException {
        logger.debug("Entered getUserById method");
        return userService.getUserById(id);
    }

    /**
     * <p>
     *     Gets all users.
     * </p>
     *
     * @return userResponseDtoList - Contains list of user.
     * @throws NotFoundException   - If users does not found.
     */
    @GetMapping
    public List<UserResponseDto> viewAllUser() throws NotFoundException {
        logger.debug("Entered viewAllUser method");
        List<UserResponseDto> userResponseDtoList = userService.getAllUser();
        logger.debug("Got list of user");
        return userResponseDtoList;
    }

    /**
     * <p>
     *     Gets user by role name.
     * </p>
     *
     * @param name                 - Role name to get users.
     * @return userResponseDtoList - Contains list of user.
     * @throws NotFoundException   - If users not found.
     */
    @GetMapping("/role/{roleName}")
    public List<UserResponseDto> viewUsersByRole(@PathVariable("roleName") String name)
                                                 throws NotFoundException {
        logger.debug("Entered viewUsersByRole method");
        List<UserResponseDto> userResponseDtoList = userService.getUserByRole(name);
        logger.debug("Got list of user");
        return userResponseDtoList;
    }

    /**
     * <p>
     *     Gets currently logged-in user profile.
     * </p>
     *
     * @return UserResponseDto - contains user profile.
     */
    @GetMapping("/userProfile")
    public UserResponseDto viewProfile() {
        logger.debug("Entered viewProfile method");
        return userService.getCurrentUserProfile();
    }

    /**
     * <p>
     *     Deletes user by id.
     * </p>
     *
     * @param id - To delete user.
     * @return SuccessResponseDto - Contains success message and status code.
     * @throws NotFoundException  - If user not found exception.
     */
    @DeleteMapping("/{userId}")
    public SuccessResponseDto deleteUserById(@PathVariable("userId") Integer id)
                                             throws NotFoundException {
        logger.debug("Entered deleteUserById method");
        return userService.deleteUserById(id);
    }

    /**
     * <p>
     *     Updates user by username.
     *     Following are the fields that can be updated,
     *     firstName, lastName, password and email.
     * </p>
     *
     * @return SuccessResponseDto - Contains success message and status code.
     * @throws NotFoundException  - If user does not exist.
     */
    @PutMapping
    public SuccessResponseDto updateUserByUserName
                              (@Valid @RequestBody UserUpdateDto userUpdateDto)
                               throws NotFoundException {
        logger.debug("Entered updateUserByUserName");
        return userService.updateUserByUserName(userUpdateDto);
    }
}