/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.mapper;

import com.ideas2it.groceryshop.model.Role;

/**
 * <p>
 *     Convert data transfer object into model and vice versa.
 * </p>
 *
 * @version 1.0
 * @author Rohit A P
 * @since 04-11-2022
 */
public class RoleMapper {

    /**
     * <p>
     *     Convert role Dto object into Role model object and return role.
     * </p>
     *
     * @param roleRequest - Contains role name.
     * @return role - Contains role details.
     */
    public static Role roleDtoToRole(String roleRequest){
        Role role = new Role();
        String roleValue = "ROLE_" + roleRequest;
        role.setName(roleValue.toUpperCase());
        return role;
    }
}