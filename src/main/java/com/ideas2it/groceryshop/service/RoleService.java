/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.service;

import java.util.Optional;

import com.ideas2it.groceryshop.dto.RoleRequestDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.dto.RoleUpdateRequestDto;
import com.ideas2it.groceryshop.exception.ExistedException;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.model.Role;

/**
 * <p>
 *     Provides Service to save, update and delete role.
 * </p>
 *
 * @version 1.0
 * @author Rohit A P
 * @since 07-11-2022
 */
public interface RoleService {

    /**
     * <p>
     *     Creates role by getting role name.
     * </p>
     *
     * @param roleRequestDto    - Contains role name.
     * @return                  - Contains success message and status code.
     * @throws ExistedException - If role already exist.
     */
    SuccessResponseDto addRole(RoleRequestDto roleRequestDto) throws ExistedException;

    /**
     * <p>
     *     Updates role name by existing role name.
     * </p>
     *
     * @param roleUpdateRequestDto - Contains existing role name and name to be updated.
     * @return                     - Contains success message and status code.
     * @throws NotFoundException   - If role not found.
     */
    SuccessResponseDto updateRole(RoleUpdateRequestDto roleUpdateRequestDto)
                                  throws NotFoundException;

    /**
     * <p>
     *     Deletes role by role name.
     * </p>
     *
     * @param roleRequestDto      - Contains role name.
     * @return                    - Contains success message and status code.
     * @throws NotFoundException  - If role not found.
     */
    SuccessResponseDto deleteRole(RoleRequestDto roleRequestDto) throws NotFoundException;

    /**
     * <p>
     *     Gets role by name.
     * </p>
     *
     * @param name  - To get role.
     * @return Role - Contains role details.
     */
    Optional<Role> findRoleByName(String name);
}