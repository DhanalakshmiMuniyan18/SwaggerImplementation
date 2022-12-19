/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ideas2it.groceryshop.configuration.CustomUserDetails;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.dto.UserRequestDto;
import com.ideas2it.groceryshop.dto.UserResponseDto;
import com.ideas2it.groceryshop.dto.UserUpdateDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.mapper.RoleMapper;
import com.ideas2it.groceryshop.mapper.UserMapper;
import com.ideas2it.groceryshop.model.Role;
import com.ideas2it.groceryshop.model.User;
import com.ideas2it.groceryshop.repository.UserRepository;
import com.ideas2it.groceryshop.service.UserService;
import com.ideas2it.groceryshop.service.RoleService;

/**
 * <p>
 *     Provides implementation services for business logic, save, update,
 *     delete and retrive user data.
 *     Data transfer object(Dto) are converted into model object using mapper
 *     for storing in database and vice versa.
 * </p>
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public SuccessResponseDto addUser(UserRequestDto userRequestDto) throws ExistedException {
        logger.debug("Entered addUser method");
        User user = UserMapper.userRequestDtoToUser(userRequestDto);
        if (userRepository.existsByUserName(userRequestDto.getUserName())) {
            logger.debug("Username already exist");
            throw new ExistedException("Username already exist");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(userRequestDto.getPassword()));
        Optional<Role> role = roleService.findRoleByName(user.getRole().getName());
        if (role.isPresent()) {
            user.setRole(role.get());
        }
        userRepository.save(user);
        logger.debug("User created successfully");
        return new SuccessResponseDto(201,
                "User created successfully");
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public UserResponseDto getUserById(Integer id) throws NotFoundException {
        logger.debug("Entered getUserById method");
        Optional<User> user = userRepository.findByIsActiveAndId(true, id);
        if(user.isEmpty()) {
            logger.debug("Uses not found");
            throw new NotFoundException("User not found");
        }
        UserResponseDto userResponseDto =
                UserMapper.userToUserResponseDto(user.get());
        logger.debug("Got users");
        return userResponseDto;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<UserResponseDto> getAllUser() throws NotFoundException {
        logger.debug("Entered getAllUser method");
        List<UserResponseDto> userResponseDtoList
                = UserMapper.userToUserResponseDtoList(userRepository.findByIsActive(true));
        if(userResponseDtoList.isEmpty()) {
            logger.debug("Users not found");
            throw new NotFoundException("Users not found");
        }
        logger.debug("Got List of user");
        return userResponseDtoList;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<UserResponseDto> getUserByRole(String name) throws NotFoundException {
        logger.debug("Entered getUserByRole method");
        List<UserResponseDto> userResponseDtoList
                = UserMapper.userToUserResponseDtoList
                (userRepository.findByIsActiveAndRoleName(true,
                        RoleMapper.roleDtoToRole(name).getName()));
        if(userResponseDtoList.isEmpty()) {
            logger.debug("Users not found");
            throw new NotFoundException("Users not found");
        }
        logger.debug("Got List of user");
        return userResponseDtoList;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public SuccessResponseDto deleteUserById(Integer id) throws NotFoundException {
        logger.debug("Entered deleteUserById method");
        Optional<User> user = userRepository.findByIsActiveAndId(true, id);
        if(user.isEmpty()) {
            logger.debug("User not found");
            throw new NotFoundException("User not found");
        }
        userRepository.disableUserById(id);
        logger.debug("User Deleted successfully");
        return new SuccessResponseDto(200,"User Deleted successfully");
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        logger.debug("Entered loadUserByUsername method");
        Optional<User> user = userRepository.findByUserNameAndIsActive(username, true);
        if (user.isEmpty()) {
            logger.debug("Username Not Found");
            throw new UsernameNotFoundException("Username Not Found");
        }
        logger.debug("Got user");
        return new CustomUserDetails(user.get());
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public String getUserNameByMobileNumber(String userNameOrMobileNumber){
        logger.debug("Entered getUserByMobileNumber method");
        Long number = null;
        String userName = null;
        Boolean isTrue = Pattern.matches("^[6-9][0-9]{9}", userNameOrMobileNumber);
        if(isTrue == true) {
            logger.debug("mobile number");
            number = Long.parseLong(userNameOrMobileNumber);
            Optional<User> user = userRepository.findUserByMobileNumberAndIsActive(number,
                    true);
            userName = user.get().getUserName();
        } else {
            logger.debug("username");
            userName = userNameOrMobileNumber;
        }
        logger.debug("Got user name");
        return userName;
    }

    /**
     *{@inheritDoc}
     */
    public SuccessResponseDto updateUserByUserName(UserUpdateDto userUpdateDto)
                                                               throws NotFoundException {
        logger.debug("Entered updateUserByUserName method");
        if(getCurrentUser().getUserName().equals(userUpdateDto.getUserName())) {
            logger.debug("User Cannot be updated");
            throw new NotFoundException("User Cannot be updated");
        }
        User updatedUser = UserMapper.userUpdateDtoToUser(userUpdateDto, getCurrentUser());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        updatedUser.setPassword(encoder.encode(userUpdateDto.getPassword()));
        userRepository.save(updatedUser);
        logger.debug("User Updated successfully");
        return new SuccessResponseDto(200,"User Updated successfully");
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public UserResponseDto getCurrentUserProfile() {
        logger.debug("Entered getCurrentUserProfile method");
        UserResponseDto userResponseDto = UserMapper.
                userToUserResponseDto(getCurrentUser());
        logger.debug("Got userResponse object");
        return userResponseDto;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public User getCurrentUser() {
        logger.debug("Entered getCurrentUser");
        Authentication authentication = SecurityContextHolder.
                getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        logger.debug("Got user object");
        return user.getUser();
    }
}