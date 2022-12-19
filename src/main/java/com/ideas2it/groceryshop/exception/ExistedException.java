/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.exception;

/**
 * <p>
 *     Implemented to throw an exception when data entered already exists.
 * </p>
 *
 * @author  RUBAN
 * @version  1.0
 * @since  05-11-22
 *
 */
public class ExistedException extends Exception {
    public ExistedException(String exception) {
        super(exception);
    }
}
