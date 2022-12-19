/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.mapper;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.groceryshop.dto.UserRequestDto;
import com.ideas2it.groceryshop.dto.UserResponseDto;
import com.ideas2it.groceryshop.dto.UserUpdateDto;
import com.ideas2it.groceryshop.model.User;

/**
 * <p>
 *     Convert data transfer object into model and vice versa.
 * </p>
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
public class UserMapper {

    /**
     * <p>
     *     Convert UserRequestDto to User object and return user
     * </p>
     *
     * @param userRequestDto - Contains user details
     * @return user - Contains User details
     */
    public static User userRequestDtoToUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setUserName(userRequestDto.getUserName());
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setMobileNumber(Long.parseLong(userRequestDto.getMobileNumber()));
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        user.setRole(RoleMapper.roleDtoToRole(userRequestDto.getRole()));
        return user;
    }

    /**
     * <p>
     *     Convert User to UserResponseDto Object and return UserResponseDto
     * </p>
     *
     * @param user - Contain user details
     * @return UserResponseDto - Contains user details
     */
    public static UserResponseDto userToUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setUserName(user.getUserName());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setMobileNumber(user.getMobileNumber());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setCreatedAt(user.getCreatedAt());
        userResponseDto.setModifiedAt(user.getModifiedAt());
        userResponseDto.setCreatedBy(user.getCreatedBy());
        userResponseDto.setModifiedBy(user.getModifiedBy());
        userResponseDto.setRole(user.getRole().getName());
        userResponseDto.setIsActive(user.getIsActive());
        return userResponseDto;
    }

    /**
     * <p>
     *     Convert UserUpdateDto to user object and return user object
     * </p>
     *
     * @param userUpdateDto - Contains details to update
     * @param user - Contains old details
     * @return user - Contains updated details
     */
    public static User userUpdateDtoToUser(UserUpdateDto userUpdateDto, User user) {
        user.setFirstName(userUpdateDto.getFirstName());
        user.setLastName(userUpdateDto.getLastName());
        user.setPassword(userUpdateDto.getPassword());
        user.setEmail(userUpdateDto.getEmail());
        return user;
    }

    /**
     * <p>
     *     Convert list of User to UserResponseDto object list
     *     and return UserResponseDto
     * </p>
     *
     * @param userList - Contains list of user object
     * @return userResponses - Contains list of user object
     */
    public static List<UserResponseDto> userToUserResponseDtoList(List<User> userList) {
        List<UserResponseDto> userResponses = new ArrayList<>();
        for(User user : userList) {
            userResponses.add(userToUserResponseDto(user));
        }
        return userResponses;
    }
}