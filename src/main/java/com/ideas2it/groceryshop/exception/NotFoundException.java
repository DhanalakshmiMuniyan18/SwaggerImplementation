/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.exception;

/**
 * <p>
 *     Implemented to throw an exception when requested data not found.
 * </p>
 *
 * @author  RUBAN
 * @version  1.0
 * @since  05-11-22
 *
 */
public class NotFoundException extends Exception {
    public NotFoundException(String exception) {
        super(exception);
    }

}
