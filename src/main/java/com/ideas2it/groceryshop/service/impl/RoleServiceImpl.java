/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service.impl;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.groceryshop.dto.RoleRequestDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.dto.RoleUpdateRequestDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.mapper.RoleMapper;
import com.ideas2it.groceryshop.model.Role;
import com.ideas2it.groceryshop.repository.RoleRepository;
import com.ideas2it.groceryshop.service.RoleService;

/**
 * <p>
 *     Provides implementation services to save, update and delete role.
 *     Data transfer object(Dto) are converted into model object using mapper
 *     for storing in database and vice versa.
 * </p>
 *
 * @version 1.0
 * @author Rohit A P
 * @since 07-11-2022
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final Logger logger = LogManager.getLogger(RoleServiceImpl.class);

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public SuccessResponseDto addRole(RoleRequestDto roleRequestDto) throws ExistedException {
        logger.debug("Entered addRole method");
        Role role = RoleMapper.roleDtoToRole(roleRequestDto.getName());
        Boolean isAvailable = roleRepository.existsByNameAndIsActive( role.getName(),
                true);
        if(isAvailable) {
            logger.debug("Role already exist");
            throw new ExistedException("Role already exist");
        }
        roleRepository.save(role);
        logger.debug("Role created successfully");
        return new SuccessResponseDto(201,"Role created successfully");
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public SuccessResponseDto updateRole(RoleUpdateRequestDto roleUpdateRequestDto)
            throws NotFoundException {
        logger.debug("Entered updateRole method");
        Optional<Role> role = roleRepository.findByIsActiveAndName(true,
                RoleMapper.roleDtoToRole(roleUpdateRequestDto.getNameToUpdate()).getName());
        if(role.isEmpty()) {
            logger.debug("Role not found");
            throw new NotFoundException("Role not found");
        }
        roleRepository.updateRoleName(RoleMapper.roleDtoToRole
                        (roleUpdateRequestDto.getName()).getName(),
                RoleMapper.roleDtoToRole(roleUpdateRequestDto.
                        getNameToUpdate()).getName());
        logger.debug("Role updated successfully");
        return new SuccessResponseDto(200,"Role updated successfully");
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public SuccessResponseDto deleteRole(RoleRequestDto roleRequestDto) throws NotFoundException {
        logger.debug("Entered deleteRole method");
        Optional<Role> role = roleRepository.findByIsActiveAndName
                (true, roleRequestDto.getName());
        if(role.isEmpty()) {
            logger.debug("Role not found");
            throw new NotFoundException("Role not found");
        }
        role.get().setIsActive(false);
        roleRepository.save(role.get());
        logger.debug("Role deleted successfully");
        return new SuccessResponseDto(200,"Role deleted successfully");
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Optional<Role> findRoleByName(String name) {
        logger.debug("Entered findRoleByName");
        Optional<Role> role = roleRepository.findByIsActiveAndName(true, name);
        logger.debug("Got role object");
        return role;
    }
}